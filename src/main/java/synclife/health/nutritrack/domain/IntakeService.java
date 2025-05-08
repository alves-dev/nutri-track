package synclife.health.nutritrack.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import synclife.health.nutritrack.domain.liquid.LiquidIntake;
import synclife.health.nutritrack.domain.liquid.LiquidType;
import synclife.health.nutritrack.domain.liquid.LiquidTypesConfig;
import synclife.health.nutritrack.domain.solid.NutrientWeight;
import synclife.health.nutritrack.domain.solid.SolidIntake;
import synclife.health.nutritrack.event.v1.EventLiquid;
import synclife.health.nutritrack.event.v1.EventLiquidAcceptableV1;
import synclife.health.nutritrack.event.v1.EventLiquidSummaryV1;
import synclife.health.nutritrack.event.v1.EventSolid;
import synclife.health.nutritrack.event.v3.EventHealthNutritionMealsV1;
import synclife.health.nutritrack.rabbit.RabbitMQProducer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static jakarta.enterprise.event.TransactionPhase.AFTER_SUCCESS;

@ApplicationScoped
public class IntakeService {

    private static final String ORCHESTRATOR_ROUTING_KEY = "orchestrator.event-sync";

    private final List<LiquidType> liquids;
    private final RabbitMQProducer rabbitMQProducer;

    public IntakeService(LiquidTypesConfig config, RabbitMQProducer rabbitMQProducer) {
        this.liquids = config.getLiquids();
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public void processEventLiquid(@Observes(during = AFTER_SUCCESS) final EventLiquid event) {
        LiquidIntake liquidIntake = new LiquidIntake(getLiquid(event.getLiquid()), event.getAmount(), event.getPersonId(), event.getDatetime());
        saveEntity(liquidIntake);
        publishLiquidSummaryEvent(event.getPersonId(), event.getDatetime());
    }

    public void processEventSolid(@Observes(during = AFTER_SUCCESS) final EventSolid event) {
        SolidIntake solidIntake = new SolidIntake(event.getMeal(), event.getFood(), event.getWeight(), event.getPersonId(), event.getDatetime());
        saveEntity(solidIntake);
    }

    public void processEventHealthNutritionMealsV1(@Observes(during = AFTER_SUCCESS) final EventHealthNutritionMealsV1 event) {
        LocalDateTime localDateTime = event.getData().datetime().withZoneSameInstant(ZoneId.of("America/Sao_Paulo")).toLocalDateTime();
        List<SolidIntake> solidIntakeList = event.getData().foodList().stream().map(food ->
                new SolidIntake(event.getData().meal(), food, event.getData().personId(), localDateTime)
        ).toList();
        saveListEntity(solidIntakeList);

        List<NutrientWeight> nutrientWeightList = new ArrayList<>();
        event.getData().weight().forEach((k, v) ->
                nutrientWeightList.add(new NutrientWeight(event.getData().meal(), k, v, event.getData().personId(), localDateTime))
        );
        saveListEntity(nutrientWeightList);
    }

    public void publishLiquidAcceptableEvent() {
        List<String> liquidsString = this.liquids.stream().map(LiquidType::liquid).toList();
        EventLiquidAcceptableV1 event = new EventLiquidAcceptableV1(liquidsString);
        rabbitMQProducer.publishEvent(ORCHESTRATOR_ROUTING_KEY, event);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected <T extends BaseEntity> void saveEntity(T entity) {
        entity.persist();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected <T extends BaseEntity> void saveListEntity(List<T> entities) {
        PanacheEntityBase.persist(entities);
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
}
