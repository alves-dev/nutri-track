package synclife.health.nutritrack.event.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import synclife.health.nutritrack.event.EventSync;

import java.time.LocalDateTime;

// https://github.com/alves-dev/SyncLife/blob/main/events.md#todos-os-eventos-v%C3%A3o-ter-os-seguintes-campos
public abstract class EventBaseV1 implements EventSync {
    private EventTypeV1 type;

    @JsonProperty("person_id")
    private String personId;

    private LocalDateTime datetime;

    @JsonProperty("meta_data")
    private EventMetadata metadata;

    EventBaseV1(EventTypeV1 type, String personId) {
        this.type = type;
        this.personId = personId;
        this.datetime = LocalDateTime.now();
        this.metadata = new EventMetadata();
    }

    protected EventBaseV1() {
    }

    public EventTypeV1 getType() {
        return type;
    }

    public String getPersonId() {
        return personId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return "EventBaseV1{" +
                "type=" + type +
                ", personId=" + personId +
                ", datetime=" + datetime +
                ", metadata=" + metadata.toString() +
                '}';
    }
}