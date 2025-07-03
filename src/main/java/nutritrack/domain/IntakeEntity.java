package nutritrack.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class IntakeEntity extends BaseEntity {

    @Column(name = "person_id", nullable = false, length = 30)
    private String personId;

    @Column(name = "datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime datetime;

    protected IntakeEntity() {
    }

    protected IntakeEntity(String personId, LocalDateTime datetime) {
        this.personId = personId;
        this.datetime = datetime;
    }
}
