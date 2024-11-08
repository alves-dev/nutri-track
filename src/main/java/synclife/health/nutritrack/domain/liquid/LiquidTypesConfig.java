package synclife.health.nutritrack.domain.liquid;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LiquidTypesConfig {

    @ConfigProperty(name = "life.health.nutri-track.liquids")
    String liquidsStr;

    private final List<LiquidType> liquids = new ArrayList<>();

    public List<LiquidType> getLiquids() {
        if (!liquids.isEmpty()) return liquids;

        String[] parts = liquidsStr.split(",");

        for (int i = 0; i < parts.length; i += 2) {
            String liquid = parts[i];
            boolean healthy = Boolean.parseBoolean(parts[i + 1]);
            liquids.add(new LiquidType(liquid, healthy));
        }
        return liquids;
    }

    public List<String> getLiquidsNames(){
        if (liquids.isEmpty()) getLiquids();
        return liquids.stream().map(LiquidType::liquid).toList();
    }
}