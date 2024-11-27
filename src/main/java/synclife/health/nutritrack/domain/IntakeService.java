package synclife.health.nutritrack.domain;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import synclife.health.nutritrack.domain.liquid.LiquidIntake;
import synclife.health.nutritrack.domain.liquid.LiquidType;
import synclife.health.nutritrack.domain.liquid.LiquidTypesConfig;
import synclife.health.nutritrack.event.EventBase;
import synclife.health.nutritrack.event.EventLiquid;
import synclife.health.nutritrack.event.EventLiquidAcceptableV1;
import synclife.health.nutritrack.event.EventLiquidSummaryV1;
import synclife.health.nutritrack.rabbit.RabbitMQProducer;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.enterprise.event.TransactionPhase.AFTER_SUCCESS;

@ApplicationScoped
public class IntakeService {

    private final Logger log = LoggerFactory.getLogger(IntakeService.class);

    private final String ORCHESTRATOR_ROUTING_KEY = "orchestrator.event-sync";

    private final List<LiquidType> liquids;
    private final RabbitMQProducer rabbitMQProducer;

    @Inject
    public IntakeService(LiquidTypesConfig config, RabbitMQProducer rabbitMQProducer) {
        this.liquids = config.getLiquids();
        this.rabbitMQProducer = rabbitMQProducer;
    }

    private void onApplicationStart(@Observes StartupEvent event) {
        publishLiquidAcceptableEvent();
    }

    public void processEventBase(@Observes EventBase event) {
        log.info(event.toString());
    }

    public void processEventLiquid(@Observes(during = AFTER_SUCCESS) final EventLiquid event) {
        LiquidIntake liquidIntake = new LiquidIntake(getLiquid(event.getLiquid()), event.getAmount(), event.getPersonId(), event.getDatetime());
        saveEntity(liquidIntake);
        publishLiquidSummaryEvent(event.getPersonId(), event.getDatetime());
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected <T extends BaseEntity> void saveEntity(T entity) {
        entity.persist();
    }

    private LiquidType getLiquid(String liquid) {
        return liquids.stream()
                .filter(e -> e.liquid().equals(liquid))
                .findFirst()
                .orElseThrow();
    }

    /**
     * Só lança o evento se a data do evento de origem for a data do dia atual
     *
     * @param personId
     * @param dateEvent
     */
    private void publishLiquidSummaryEvent(String personId, LocalDateTime dateEvent) {
        LocalDateTime startDay = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime endDay = LocalDateTime.now().withHour(23).withMinute(59);

        if (startDay.getDayOfMonth() != dateEvent.getDayOfMonth()) return;

        List<LiquidIntake> todayIntakes = LiquidIntake.list("personId = ?1 and datetime >= ?2 and datetime < ?3",
                personId, startDay, endDay);
        int totalHealth = todayIntakes.stream()
                .filter(LiquidIntake::isHealthy)
                .mapToInt(LiquidIntake::getAmount)
                .sum();
        int totalUnhealthy = todayIntakes.stream()
                .filter(e -> !e.isHealthy())
                .mapToInt(LiquidIntake::getAmount)
                .sum();

        EventLiquidSummaryV1 summary = new EventLiquidSummaryV1(personId, totalHealth, totalUnhealthy);
        rabbitMQProducer.publishEvent(ORCHESTRATOR_ROUTING_KEY, summary);
    }

    private void publishLiquidAcceptableEvent() {
        List<String> liquidsString = this.liquids.stream().map(LiquidType::liquid).toList();
        EventLiquidAcceptableV1 event = new EventLiquidAcceptableV1(liquidsString);
        rabbitMQProducer.publishEvent(ORCHESTRATOR_ROUTING_KEY, event);
    }
}
