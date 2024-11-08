package synclife.health.nutritrack.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

// https://github.com/alves-dev/life/blob/main/events/README.md#todos-os-eventos-vao-ter-os-seguintes-campos
public class EventBase {
    private EventType type;

    @JsonProperty("person_id")
    private String personId;

    private LocalDateTime datetime;

    @JsonProperty("meta_data")
    private EventMetadata metadata;

    public EventType getType() {
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
        return "EventBase{" +
                "type=" + type +
                ", personId=" + personId +
                ", datetime=" + datetime +
                ", metadata=" + metadata.toString() +
                '}';
    }
}