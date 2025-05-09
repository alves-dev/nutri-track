package synclife.health.nutritrack.event.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import synclife.health.nutritrack.event.EventFlow;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RegisterForReflection
public class EventHealthNutritionMealsV1 extends EventBaseV3 {

    @JsonProperty("data")
    private Data data;

    @Override
    public EventFlow getEventFlow() {
        return super.getType().getEventFlow();
    }

    public record Data(
            @JsonProperty("person_id") String personId,
            @JsonProperty("meal") String meal,
            @JsonProperty("datetime") ZonedDateTime datetime,
            @JsonProperty("food_list") List<String> foodList,
            @JsonProperty("weight") Map<String, Integer> weight
    ) {
    }

    public Data getData() {
        return data;
    }
}
