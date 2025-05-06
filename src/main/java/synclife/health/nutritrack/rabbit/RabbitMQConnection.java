package synclife.health.nutritrack.rabbit;

import com.rabbitmq.client.Connection;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
class RabbitMQConnection {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConnection.class);

    @Inject
    @ConfigProperty(name = "quarkus.application.name")
    private String applicationName;

    @Inject
    private RabbitMQClient rabbitMQClient;

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) setup();
        return connection;
    }

    private void onApplicationStart(@Observes StartupEvent event) {
        setup();
    }

    private void setup() {
        connection = rabbitMQClient.connect(applicationName);
    }
}