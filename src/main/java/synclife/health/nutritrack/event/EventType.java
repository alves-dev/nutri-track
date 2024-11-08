package synclife.health.nutritrack.event;

public enum EventType {
    HEALTH_NUTRI_TRACK_LIQUID_V1(EventLiquid.class);

    private final Class<? extends EventBase> eventClass;

    EventType(Class<? extends EventBase> eventClass) {
        this.eventClass = eventClass;
    }

    public Class<? extends EventBase> getEventClass() {
        return eventClass;
    }
}