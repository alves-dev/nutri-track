package synclife.health.nutritrack.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import synclife.health.nutritrack.domain.liquid.LiquidIntake;
import synclife.health.nutritrack.domain.liquid.LiquidType;
import synclife.health.nutritrack.domain.liquid.LiquidTypesConfig;
import synclife.health.nutritrack.event.EventBase;
import synclife.health.nutritrack.event.EventLiquid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static jakarta.enterprise.event.TransactionPhase.AFTER_SUCCESS;

@ApplicationScoped
public class IntakeService {

    private final Logger log = LoggerFactory.getLogger(IntakeService.class);

    private final List<LiquidType> liquids;

    @Inject
    public IntakeService(LiquidTypesConfig config) {
        this.liquids = config.getLiquids();
    }

    public void processEventBase(@Observes EventBase event) {
        log.info(event.toString());
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void processEventFood(@Observes(during = AFTER_SUCCESS) final EventLiquid event) {
        LiquidIntake liquidIntake = new LiquidIntake(getLiquid(event.getLiquid()), event.getAmount(), event.getPersonId(), event.getDatetime());
        liquidIntake.persist();
    }

    private LiquidType getLiquid(String liquid) {
        return liquids.stream()
                .filter(e -> e.liquid().equals(liquid))
                .findFirst()
                .orElse(new LiquidType(liquid, null));
    }
}
