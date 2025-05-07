package synclife.health.nutritrack.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import synclife.health.nutritrack.event.EventBase;
import synclife.health.nutritrack.event.v3.EventBaseV3;

@ApplicationScoped
public class RabbitMQProducer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);

    private final RabbitMQConnection rabbitMQConnection;
    private final ObjectMapper objectMapper;
    private final String exchange;
    private final String exchangeV3;

    private Channel channel;

    public RabbitMQProducer(RabbitMQConnection rabbitMQConnection, ObjectMapper objectMapper,
                            @ConfigProperty(name = "sync-life.health.nutri-track.exchange") String exchange,
                            @ConfigProperty(name = "sync-life.health.nutri-track.exchange.v3") String exchangeV3) {
        this.rabbitMQConnection = rabbitMQConnection;
        this.objectMapper = objectMapper;
        this.exchange = exchange;
        this.exchangeV3 = exchangeV3;
    }

    private void onApplicationStart(@Observes StartupEvent event) {
        setChannel();
    }

    public <T extends EventBase> void publishEvent(String routingKey, T event) {
        this.publishEvent(event, exchange, routingKey);
    }

    public <T extends EventBaseV3> void publishEventV3(T event) {
        this.publishEvent(event, exchangeV3, event.getType().toJson());
    }

    private void publishEvent(Object event, String exchange, String routingKey){
        if (channel == null) setChannel();
        try {
            byte[] body = objectMapper.writeValueAsBytes(event);
            channel.basicPublish(exchange, routingKey, null, body);
            log.info("Published: {}", event);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    private void setChannel() {
        try {
            channel = rabbitMQConnection.getConnection().createChannel();
            log.debug("Channel created!");
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
