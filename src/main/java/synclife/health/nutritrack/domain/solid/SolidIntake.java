package synclife.health.nutritrack.domain.solid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import synclife.health.nutritrack.domain.IntakeEntity;

import java.time.LocalDateTime;

@Entity(name = "solid_intake")
public class SolidIntake extends IntakeEntity {

    @Column(name = "meal", length = 20, nullable = false)
    private String meal;

    @Column(name = "food", length = 20, nullable = false)
    private String food;

    @Column(name = "weight", nullable = true)
    private int weight;

    public SolidIntake(String meal, String food, String personId, LocalDateTime datetime) {
        super(personId, datetime);
        this.meal = meal;
        this.food = food;
        this.weight = 0;
    }

    protected SolidIntake() {
        super();
    }
}
