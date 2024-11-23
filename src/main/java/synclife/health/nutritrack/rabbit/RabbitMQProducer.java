package synclife.health.nutritrack.rabbit;

import com.rabbitmq.client.Channel;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class RabbitMQProducer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQProducer.class);

    @Inject
    RabbitMQClient rabbitMQClient;

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

    public void publishEvent(String routingKey, String message) {
        try {
            channel.basicPublish(exchange, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            log.info("Published: {}", message);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
