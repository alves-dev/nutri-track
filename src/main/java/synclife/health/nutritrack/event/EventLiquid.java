package synclife.health.nutritrack.event;

public class EventLiquid extends EventBase {
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
                "EventLiquidFood{" +
                "liquid='" + liquid + '\'' +
                ", amount=" + amount +
                '}';
    }
}