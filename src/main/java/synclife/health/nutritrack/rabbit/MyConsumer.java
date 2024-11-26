package synclife.health.nutritrack.rabbit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import synclife.health.nutritrack.event.EventBase;
import synclife.health.nutritrack.event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
class MyConsumer implements Consumer {

    private static final Logger log = LoggerFactory.getLogger(MyConsumer.class);

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private Event<EventBase> eventPub;

    @Override
    public void handleConsumeOk(String consumerTag) {

    }

    @Override
    public void handleCancelOk(String consumerTag) {

    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

    }

    @Override
    public void handleRecoverOk(String consumerTag) {

    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String messageString = new String(body, StandardCharsets.UTF_8);

        log.debug("Received: {}", messageString);

        try {
            JsonNode node = objectMapper.readTree(messageString);
            String typeValue = node.get("type").asText();

            Class<? extends EventBase> eventClass = EventType.getEventClass(typeValue);
            EventBase event = objectMapper.readValue(messageString, eventClass);

            eventPub.fire(event);
        } catch (Exception e) {
            log.warn("[RabbitMqListener] Error parsing: {}", messageString);
            log.warn(e.toString());
        }
    }
}
