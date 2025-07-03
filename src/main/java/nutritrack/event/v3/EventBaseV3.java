package nutritrack.event.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import nutritrack.event.EventSync;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

// https://github.com/alves-dev/sync-life/blob/main/events/events_v3.md
@RegisterForReflection
public abstract class EventBaseV3 implements EventSync {

    private static final String URI = "/services/nutri-track";

    @JsonProperty("specversion")
    private String specVersion;

    @JsonProperty("type")
    private EventTypeV3 type;

    @JsonProperty("source")
    private URI source;

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("time")
    private ZonedDateTime time;

    @JsonProperty("datacontenttype")
    private String dataContentType;

    @JsonProperty("dataschema")
    private URL datasChema;

    @JsonProperty("extensions")
    private Map<String, Object> extensions;

    EventBaseV3(EventTypeV3 type, URL datasChema, Map<String, Object> extensions) throws URISyntaxException {
        this.specVersion = "1.0";
        this.type = type;
        this.source = new URI(URI);
        this.id = UUID.randomUUID();
        this.time = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
        this.dataContentType = "application/json";
        this.datasChema = datasChema;
        this.extensions = extensions;
    }

    protected EventBaseV3() {
    }

    public EventTypeV3 getType() {
        return type;
    }

    @Override
    public String toString() {
        return "EventBaseV3{" +
                "specVersion='" + specVersion + '\'' +
                ", type=" + type +
                ", source=" + source +
                ", id=" + id +
                ", time=" + time +
                ", dataContentType='" + dataContentType + '\'' +
                ", datasChema=" + datasChema +
                ", extensions=" + extensions +
                '}';
    }
}