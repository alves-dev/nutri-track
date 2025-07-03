package nutritrack.domain.solid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import nutritrack.domain.IntakeEntity;

import java.time.LocalDateTime;

@Entity(name = "nutrient_weight")
public class NutrientWeight extends IntakeEntity {

    @Column(name = "meal", length = 20, nullable = false)
    private String meal;

    @Column(name = "nutrient_type", length = 20, nullable = false)
    private String nutrientType;

    @Column(name = "weight", nullable = true)
    private int weight;

    public NutrientWeight(String meal, String nutrientType, int weight, String personId, LocalDateTime datetime) {
        super(personId, datetime);
        this.meal = meal;
        this.nutrientType = nutrientType;
        this.weight = weight;
    }

    protected NutrientWeight() {
        super();
    }
}
