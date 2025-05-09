package synclife.health.nutritrack.event.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import synclife.health.nutritrack.event.EventFlow;

import java.util.List;

@RegisterForReflection
public class EventLiquidAcceptableV1 extends EventBaseV1 {

    @JsonProperty("accepted_liquids")
    private List<String> acceptedLiquids;

    public EventLiquidAcceptableV1(List<String> acceptedLiquids) {
        super(EventTypeV1.HEALTH_NUTRI_TRACK_LIQUID_ACCEPTABLE_V1, "system");
        this.acceptedLiquids = acceptedLiquids;
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
                "EventLiquidAcceptableV1{" +
                ", acceptedLiquids=" + acceptedLiquids +
                '}';
    }

    @JsonIgnore
    @Override
    public EventFlow getEventFlow() {
        return super.getType().getEventFlow();
    }
}