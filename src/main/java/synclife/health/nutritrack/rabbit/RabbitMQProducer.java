package synclife.health.nutritrack.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import synclife.health.nutritrack.event.EventBase;

@ApplicationScoped
public class RabbitMQProducer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Inject
    RabbitMQClient rabbitMQClient;

    @Inject
    private ObjectMapper objectMapper;

    private Channel channel;

    @Inject
    @ConfigProperty(name = "sync-life.health.nutri-track.exchange")
    private String exchange;

    private void onApplicationStart(@Observes StartupEvent event) {
        try {
            channel = rabbitMQClient.connect().createChannel();
            log.debug("Channel created!");
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public <T extends EventBase> void publishEvent(String routingKey, T event) {
        try {
            byte[] body = objectMapper.writeValueAsBytes(event);
            channel.basicPublish(exchange, routingKey, null, body);
            log.info("Published: {}", event);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
