package nutritrack.utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import nutritrack.event.EventSync;

@ApplicationScoped
public class LogEvents {

    private final Logger log = LoggerFactory.getLogger(LogEvents.class);

    public void processEventBase(@Observes EventSync event) {
        log.info("{}: {}", event.getEventFlow(), event);
    }
}
