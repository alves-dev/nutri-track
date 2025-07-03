package nutritrack.rabbit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import nutritrack.event.v3.EventBaseV3;
import nutritrack.event.v3.EventTypeV3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
class ConsumerV3 implements Consumer {

    private static final Logger log = LoggerFactory.getLogger(ConsumerV3.class);

    private final ObjectMapper objectMapper;
    private final Event<EventBaseV3> eventPub;

    private Runnable restartCallback;

    ConsumerV3(ObjectMapper objectMapper, Event<EventBaseV3> eventPub) {
        this.objectMapper = objectMapper;
        this.eventPub = eventPub;
    }

    public void setRestartCallback(Runnable callback) {
        this.restartCallback = callback;
    }

    /**
     * Chamado quando o consumer foi registrado com sucesso.
     * Pode ser útil para confirmar que o Rabbit aceitou o consumidor.
     */
    @Override
    public void handleConsumeOk(String consumerTag) {
        log.info("Consumer registrado com sucesso: {}", consumerTag);
    }

    /**
     * Chamado quando o cancelamento do consumer foi confirmado.
     * Mas isso aqui é passivo — apenas confirma que o cancelamento foi OK.
     */
    @Override
    public void handleCancelOk(String consumerTag) {
        log.info("Cancelamento confirmado para o consumer: {}", consumerTag);
    }

    /**
     * Chamado quando o consumer é cancelado pelo Rabbit (ex: fila deletada, erro, etc).
     */
    @Override
    public void handleCancel(String consumerTag) throws IOException {
        log.warn("⚠️ Consumer canceled by the broker: {}", consumerTag);

        if (restartCallback != null) {
            try {
                log.info("Sleeping for 4 seconds");
                Thread.sleep(4000);
                restartCallback.run();
            } catch (Exception e) {
                log.error("Error when trying to restart the consumer v3", e);
            }
        }
    }

    /**
     * Chamado quando o canal/consumer sofre um shutdown (ex: erro de rede, broker reinicia).
     * Muito útil para detectar falhas silenciosas.
     */
    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        log.error("Shutdown no consumer: {} | Motivo: {}", consumerTag, sig.getMessage(), sig);
        log.info("Iniciador do shutdown: {}", sig.isInitiatedByApplication());
    }

    /**
     * Chamado quando o consumidor foi recuperado automaticamente (auto-recovery).
     * Só é chamado se tiver ativado auto-recovery na connection factory.
     */
    @Override
    public void handleRecoverOk(String consumerTag) {
        log.info("Consumer recuperado automaticamente: {}", consumerTag);
    }

    /**
     * Recebe e processa a mensagem da fila.
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String messageString = new String(body, StandardCharsets.UTF_8);

        log.info("Received: {}", messageString);

        try {
            JsonNode node = objectMapper.readTree(messageString);
            String typeValue = node.get("type").asText();

            Class<? extends EventBaseV3> eventClass = EventTypeV3.getEventClass(typeValue);
            EventBaseV3 event = objectMapper.readValue(messageString, eventClass);

            eventPub.fire(event);
        } catch (Exception e) {
            log.error("[RabbitMqListener] Error parsing: {}", messageString);
            log.error("Reason: ", e);
        }
    }
}
