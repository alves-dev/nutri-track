package synclife.health.nutritrack.event;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum EventType {
    HEALTH_NUTRI_TRACK_LIQUID_V1("HEALTH.NUTRI_TRACK.LIQUID.V1", EventLiquid.class), // https://github.com/alves-dev/SyncLife/blob/main/events.md#event-healthnutri_trackliquidv1
    HEALTH_NUTRI_TRACK_LIQUID_SUMMARY_V1("HEALTH.NUTRI_TRACK.LIQUID_SUMMARY.V1", EventLiquid.class); // https://github.com/alves-dev/SyncLife/blob/main/events.md#event-healthnutri_trackliquid_summaryv1

    private final String eventText;
    private final Class<? extends EventBase> eventClass;

    EventType(String eventText, Class<? extends EventBase> eventClass) {
        this.eventText = eventText;
        this.eventClass = eventClass;
    }

    @JsonValue
    public String toJson() {
        return eventText;
    }

    public static Class<? extends EventBase> getEventClass(String eventText) {
        return Arrays.stream(EventType.values()).filter(
                e -> e.toJson().equals(eventText)
        ).findFirst().get().getEventClass();
    }

    private Class<? extends EventBase> getEventClass() {
        return eventClass;
    }
}