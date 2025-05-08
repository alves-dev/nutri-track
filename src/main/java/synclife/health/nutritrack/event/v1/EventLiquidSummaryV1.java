package synclife.health.nutritrack.event.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import synclife.health.nutritrack.event.EventFlow;

public class EventLiquidSummaryV1 extends EventBaseV1 {
    @JsonProperty("total_liquid")
    private TotalLiquid totalLiquid;

    public EventLiquidSummaryV1(String personId, int totalHealth, int totalUnhealthy) {
        super(EventTypeV1.HEALTH_NUTRI_TRACK_LIQUID_SUMMARY_V1, personId);
        this.totalLiquid = new TotalLiquid(totalHealth, totalUnhealthy);
    }

    @Override
    public EventFlow getEventFlow() {
        return super.getType().getEventFlow();
    }

    record TotalLiquid(int healthy, int unhealthy) {
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
                "EventLiquidSummaryV1{" +
                ", totalLiquid=" + totalLiquid +
                '}';
    }
}