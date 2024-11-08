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

import java.io.IOException;
import java.io.UncheckedIOException;

@ApplicationScoped
public class RabbitMqListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqListener.class);

    @Inject
    RabbitMQClient rabbitMQClient;

    @Inject
    private MyConsumer consumer;

    @Inject
    @ConfigProperty(name = "life.health.nutri-track.queue")
    private String nutriTrackQueue;

    public void onApplicationStart(@Observes StartupEvent event) {
        setup();
    }

    private void setup() {
        try {
            Channel channel = rabbitMQClient.connect().createChannel();
            channel.basicConsume(nutriTrackQueue, true, consumer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}