package synclife.health.nutritrack.rabbit;

import com.rabbitmq.client.Channel;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
class RabbitMQListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

    private final RabbitMQConnection rabbitMQConnection;
    private final MyConsumer consumer;
    private final String nutriTrackQueue;

    RabbitMQListener(RabbitMQConnection rabbitMQConnection, MyConsumer consumer,
                     @ConfigProperty(name = "sync-life.health.nutri-track.queue") String nutriTrackQueue) {
        this.rabbitMQConnection = rabbitMQConnection;
        this.consumer = consumer;
        this.nutriTrackQueue = nutriTrackQueue;
    }

    private void onApplicationStart(@Observes StartupEvent event) throws TimeoutException {
        setup();
    }

    private void setup() throws TimeoutException {
        try (Channel channel = rabbitMQConnection.getConnection().createChannel()) {
            channel.basicConsume(nutriTrackQueue, true, "consumer-tag-old", consumer);
            log.info("Consumer for {} created!", nutriTrackQueue);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}