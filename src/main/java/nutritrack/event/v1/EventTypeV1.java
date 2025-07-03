package nutritrack.event.v1;

import com.fasterxml.jackson.annotation.JsonValue;
import io.quarkus.runtime.annotations.RegisterForReflection;
import nutritrack.event.EventFlow;
import nutritrack.event.EventType;

import java.util.Arrays;

@RegisterForReflection
public enum EventTypeV1 implements EventType {
    HEALTH_NUTRI_TRACK_LIQUID_V1("HEALTH.NUTRI_TRACK.LIQUID.V1", EventFlow.CONSUMED,
            EventLiquid.class), // https://github.com/alves-dev/SyncLife/blob/main/events.md#event-healthnutri_trackliquidv1
    HEALTH_NUTRI_TRACK_LIQUID_SUMMARY_V1("HEALTH.NUTRI_TRACK.LIQUID_SUMMARY.V1", EventFlow.PRODUCED,
            EventLiquidSummaryV1.class), // https://github.com/alves-dev/SyncLife/blob/main/events.md#event-healthnutri_trackliquid_summaryv1
    HEALTH_NUTRI_TRACK_LIQUID_ACCEPTABLE_V1("HEALTH.NUTRI_TRACK.LIQUID_ACCEPTABLE.V1", EventFlow.PRODUCED,
            EventLiquidAcceptableV1.class); // https://github.com/alves-dev/SyncLife/blob/main/events.md#event-healthnutri_trackliquid_acceptablev1

    private final String text;
    private final EventFlow flow;
    private final Class<? extends EventBaseV1> eventClass;

    EventTypeV1(String text, EventFlow flow, Class<? extends EventBaseV1> eventClass) {
        this.text = text;
        this.flow = flow;
        this.eventClass = eventClass;
    }

    @JsonValue
    public String toJson() {
        return text;
    }

    public static Class<? extends EventBaseV1> getEventClass(String eventText) {
        return Arrays.stream(EventTypeV1.values()).filter(
                e -> e.toJson().equals(eventText)
        ).findFirst().get().getEventClass();
    }

    private Class<? extends EventBaseV1> getEventClass() {
        return eventClass;
    }

    @Override
    public EventFlow getEventFlow() {
        return flow;
    }
}