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
class RabbitMQListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

    @Inject
    RabbitMQClient rabbitMQClient;

    @Inject
    private MyConsumer consumer;

    @Inject
    @ConfigProperty(name = "sync-life.health.nutri-track.queue")
    private String nutriTrackQueue;

    @Inject
    @ConfigProperty(name = "quarkus.application.name")
    private String applicationName;

    private void onApplicationStart(@Observes StartupEvent event) {
        setup();
    }

    private void setup() {
        try {
            Channel channel = rabbitMQClient.connect(applicationName + "_listener").createChannel();
            channel.basicConsume(nutriTrackQueue, true, consumer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}