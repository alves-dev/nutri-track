package synclife.health.nutritrack.event.v3;

import com.fasterxml.jackson.annotation.JsonValue;
import synclife.health.nutritrack.event.EventFlow;
import synclife.health.nutritrack.event.EventType;

import java.util.Arrays;

public enum EventTypeV3 implements EventType {
    ORCHESTRATOR_SUBSCRIPTIONS_REQUESTED_V1("orchestrator.subscriptions.requested.v1", EventFlow.PRODUCED,
            EventOrchestratorSubscriptionRequestedV1.class), // https://github.com/alves-dev/SyncLife/blob/main/events/model/orchestrator/subscriptions.requested.v1.json
    HEALTH_NUTRITION_MEALS_V1("health.nutrition.meals.v1", EventFlow.CONSUMED,
            EventHealthNutritionMealsV1.class); // https://github.com/alves-dev/SyncLife/blob/main/events/model/health/nutrition.meals.v1.json

    private final String text;
    private final EventFlow flow;
    private final Class<? extends EventBaseV3> eventClass;

    EventTypeV3(String text, EventFlow flow, Class<? extends EventBaseV3> eventClass) {
        this.text = text;
        this.flow = flow;
        this.eventClass = eventClass;
    }

    @JsonValue
    public String toJson() {
        return text;
    }

    public static Class<? extends EventBaseV3> getEventClass(String eventText) {
        return Arrays.stream(EventTypeV3.values()).filter(
                e -> e.toJson().equals(eventText)
        ).findFirst().get().getEventClass();
    }

    private Class<? extends EventBaseV3> getEventClass() {
        return eventClass;
    }

    @Override
    public EventFlow getEventFlow() {
        return flow;
    }
}