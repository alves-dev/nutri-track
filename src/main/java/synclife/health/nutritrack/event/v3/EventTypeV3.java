package synclife.health.nutritrack.event.v3;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum EventTypeV3 {
    ORCHESTRATOR_SUBSCRIPTIONS_REQUESTED_V1("orchestrator.subscriptions.requested.v1", EventOrchestratorSubscriptionRequestedV1.class), // https://github.com/alves-dev/SyncLife/blob/main/events/model/orchestrator/subscriptions.requested.v1.json
    HEALTH_NUTRITION_MEALS_V1("health.nutrition.meals.v1", EventHealthNutritionMealsV1.class); // https://github.com/alves-dev/SyncLife/blob/main/events/model/health/nutrition.meals.v1.json

    private final String eventText;
    private final Class<? extends EventBaseV3> eventClass;

    EventTypeV3(String eventText, Class<? extends EventBaseV3> eventClass) {
        this.eventText = eventText;
        this.eventClass = eventClass;
    }

    @JsonValue
    public String toJson() {
        return eventText;
    }

    public static Class<? extends EventBaseV3> getEventClass(String eventText) {
        return Arrays.stream(EventTypeV3.values()).filter(
                e -> e.toJson().equals(eventText)
        ).findFirst().get().getEventClass();
    }

    private Class<? extends EventBaseV3> getEventClass() {
        return eventClass;
    }
}