package synclife.health.nutritrack.rabbit;

import com.rabbitmq.client.Channel;
import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;

@ApplicationScoped
class RabbitMQListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

    private final RabbitMQConnection rabbitMQConnection;
    private final ConsumerV1 consumer;
    private final ConsumerV3 consumerV3;
    private final String nutriTrackQueue;
    private final String nutriTrackQueueV3;

    private Channel channelV1;
    private Channel channelV3;

    RabbitMQListener(RabbitMQConnection rabbitMQConnection, ConsumerV1 consumer, ConsumerV3 consumerV3,
                     @ConfigProperty(name = "sync-life.health.nutri-track.queue") String nutriTrackQueue,
                     @ConfigProperty(name = "sync-life.health.nutri-track.queue.v3") String nutriTrackQueueV3) {
        this.rabbitMQConnection = rabbitMQConnection;
        this.consumer = consumer;
        this.consumerV3 = consumerV3;
        this.nutriTrackQueue = nutriTrackQueue;
        this.nutriTrackQueueV3 = nutriTrackQueueV3;
    }

    private void onApplicationStart(@Observes StartupEvent event) {
        consume();
        consumeV3();
    }

    private void consume() {
        try {
            this.channelV1 = rabbitMQConnection.getConnection().createChannel();
            channelV1.basicConsume(nutriTrackQueue, true, "consumer-tag-old", consumer);
            log.info("Consumer for {} created!", nutriTrackQueue);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void consumeV3() {
        try {
            this.channelV3 = rabbitMQConnection.getConnection().createChannel();
            channelV3.basicConsume(nutriTrackQueueV3, true, "consumer-tag-v3", consumerV3);
            log.info("Consumer for {} created!", nutriTrackQueueV3);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @PreDestroy
    private void shutdown() {
        try {
            if (channelV1 != null && channelV1.isOpen()) channelV1.close();
            if (channelV3 != null && channelV3.isOpen()) channelV3.close();
        } catch (Exception e) {
            log.warn("Erro ao fechar canais RabbitMQ", e);
        }
    }
}