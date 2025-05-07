package synclife.health.nutritrack.rabbit;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import synclife.health.nutritrack.event.v3.EventOrchestratorSubscriptionRequestedV1;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@ApplicationScoped
public class PublishStart {

    private final RabbitMQProducer rabbitMQProducer;
    private final String nutriTrackQueueV3;

    public PublishStart(RabbitMQProducer rabbitMQProducer,
                        @ConfigProperty(name = "sync-life.health.nutri-track.queue.v3") String nutriTrackQueueV3) {
        this.rabbitMQProducer = rabbitMQProducer;
        this.nutriTrackQueueV3 = nutriTrackQueueV3;
    }

    private void onApplicationStart(@Observes StartupEvent event) throws MalformedURLException, URISyntaxException {
        EventOrchestratorSubscriptionRequestedV1 subscriptionRequestedV1 = new
                EventOrchestratorSubscriptionRequestedV1(nutriTrackQueueV3);
        rabbitMQProducer.publishEventV3(subscriptionRequestedV1);
    }

}
