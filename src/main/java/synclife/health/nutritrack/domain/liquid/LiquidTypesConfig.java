package synclife.health.nutritrack.domain.liquid;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class LiquidTypesConfig {

    @ConfigProperty(name = "nutri-track.liquids.healthy")
    String liquidsHealthy;

    @ConfigProperty(name = "nutri-track.liquids.unhealthy")
    String liquidsUnhealthy;

    private final List<LiquidType> liquids = new ArrayList<>();

    public List<LiquidType> getLiquids() {
        if (!liquids.isEmpty()) return liquids;

        String[] healthyNames = liquidsHealthy.split(",");
        String[] unhealthyNames = liquidsUnhealthy.split(",");

        Arrays.stream(healthyNames).forEach(l -> liquids.add(new LiquidType(l, true)));
        Arrays.stream(unhealthyNames).forEach(l -> liquids.add(new LiquidType(l, false)));

        return liquids;
    }
}