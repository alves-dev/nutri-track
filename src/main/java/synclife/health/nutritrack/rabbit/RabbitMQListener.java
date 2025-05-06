package synclife.health.nutritrack.rabbit;

import com.rabbitmq.client.Channel;
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
class RabbitMQListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

    @Inject
    @ConfigProperty(name = "sync-life.health.nutri-track.queue")
    private String nutriTrackQueue;

    @Inject
    private RabbitMQConnection rabbitMQConnection;

    @Inject
    private MyConsumer consumer;

    private void onApplicationStart(@Observes StartupEvent event) {
        setup();
    }

    private void setup() {
        try {
            Channel channel = rabbitMQConnection.getConnection().createChannel();
            channel.basicConsume(nutriTrackQueue, true, "consumer-tag-old", consumer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}