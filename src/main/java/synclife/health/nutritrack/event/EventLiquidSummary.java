package synclife.health.nutritrack.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventLiquidSummary extends EventBase {
    @JsonProperty("total_liquid")
    private TotalLiquid totalLiquid;

    public EventLiquidSummary(String personId, int totalHealth, int totalUnhealthy) {
        super(EventType.HEALTH_NUTRI_TRACK_LIQUID_SUMMARY_V1, personId);
        this.totalLiquid = new TotalLiquid(totalHealth, totalUnhealthy);
    }

    record TotalLiquid(int healthy, int unhealthy) {
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
                "EventLiquidSummary{" +
                ", totalLiquid=" + totalLiquid +
                '}';
    }
}