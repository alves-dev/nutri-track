package synclife.health.nutritrack.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class EventMetadata {
    private String origin;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public String getOrigin() {
        return origin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "origin=" + origin +
                ", created_at=" + createdAt +
                '}';
    }
}