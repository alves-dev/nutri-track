package synclife.health.nutritrack.event;

public class EventSolid extends EventBase {
    private String meal;
    private String food;
    private int weight;

    public String getMeal() {
        return meal;
    }

    public String getFood() {
        return food;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return super.toString() + " | " +
                "EventSolid{" +
                "meal=" + meal +
                ", food=" + food +
                ", weight=" + weight +
                '}';
    }
}