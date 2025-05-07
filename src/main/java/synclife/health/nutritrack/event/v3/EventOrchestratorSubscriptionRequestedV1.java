package synclife.health.nutritrack.event.v3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class EventOrchestratorSubscriptionRequestedV1 extends EventBaseV3 {

    private static final String URL = "https://raw.githubusercontent.com/alves-dev/SyncLife/main/events/schema/orchestrator/subscriptions.requested.v1.json";
    private static final String SERVICE_ID = "nutri-track";

    @JsonProperty("data")
    private final Data data;

    public EventOrchestratorSubscriptionRequestedV1(String queueName) throws URISyntaxException, MalformedURLException {
        super(EventTypeV3.ORCHESTRATOR_SUBSCRIPTIONS_REQUESTED_V1,
                new URI(URL).toURL(),
                EventOrchestratorSubscriptionRequestedV1.getExtensions());
        this.data = new Data(SERVICE_ID, queueName, new Subscriptions(List.of(EventTypeV3.HEALTH_NUTRITION_MEALS_V1.toJson())));
    }

    public record Data(
            @JsonProperty("service_id") String serviceId,
            @JsonProperty("queue_name") String queueName,
            @JsonProperty("subscriptions") Subscriptions subscriptions
    ) {
    }

    record Subscriptions(
            @JsonProperty("event_types") List<String> eventTypes
    ) {
    }

    private static Map<String, Object> getExtensions() {
        return Map.of("causation_id", "event-startup",
                "origin", SERVICE_ID);
    }
}
