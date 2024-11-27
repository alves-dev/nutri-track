package synclife.health.nutritrack.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EventLiquidAcceptableV1 extends EventBase {
    @JsonProperty("accepted_liquids")
    private List<String> acceptedLiquids;

    public EventLiquidAcceptableV1(List<String> acceptedLiquids) {
        super(EventType.HEALTH_NUTRI_TRACK_LIQUID_ACCEPTABLE_V1, "system");
        this.acceptedLiquids = acceptedLiquids;
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
                "EventLiquidAcceptableV1{" +
                ", acceptedLiquids=" + acceptedLiquids +
                '}';
    }
}