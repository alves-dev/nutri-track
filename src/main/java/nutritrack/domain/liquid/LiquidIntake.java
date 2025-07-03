package nutritrack.domain.liquid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import nutritrack.domain.IntakeEntity;

import java.time.LocalDateTime;

@Entity(name = "liquid_intake")
public class LiquidIntake extends IntakeEntity {

    @Column(name = "liquid", length = 50, nullable = false)
    private String liquid;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "healthy")
    private boolean healthy;

    public int getAmount() {
        return amount;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public LiquidIntake(LiquidType liquid, int amount, String personId, LocalDateTime datetime) {
        super(personId, datetime);
        this.liquid = liquid.liquid();
        this.amount = amount;
        this.healthy = liquid.healthy();
    }

    protected LiquidIntake() {
        super();
    }
}
