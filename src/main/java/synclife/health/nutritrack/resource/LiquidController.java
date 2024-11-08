package synclife.health.nutritrack.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import synclife.health.nutritrack.domain.liquid.LiquidTypesConfig;

import java.util.List;

@Path("/api/v1/liquid")
public class LiquidController {

    private final List<String> liquids;

    @Inject
    public LiquidController(LiquidTypesConfig config) {
        this.liquids = config.getLiquidsNames();
    }

    @GET
    @Path("/names")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> hello() {
        return liquids;
    }
}