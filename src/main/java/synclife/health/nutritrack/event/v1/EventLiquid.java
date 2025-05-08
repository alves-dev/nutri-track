package synclife.health.nutritrack.event.v1;

import synclife.health.nutritrack.event.EventFlow;

public class EventLiquid extends EventBaseV1 {
    private String liquid;
    private int amount;

    public String getLiquid() {
        return liquid;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
                "EventLiquid{" +
                "liquid=" + liquid +
                ", amount=" + amount +
                '}';
    }

    @Override
    public EventFlow getEventFlow() {
        return super.getType().getEventFlow();
    }
}