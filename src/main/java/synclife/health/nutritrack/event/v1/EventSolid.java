package synclife.health.nutritrack.event.v1;

import io.quarkus.runtime.annotations.RegisterForReflection;
import synclife.health.nutritrack.event.EventFlow;

@RegisterForReflection
public class EventSolid extends EventBaseV1 {
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

    @Override
    public EventFlow getEventFlow() {
        return super.getType().getEventFlow();
    }
}