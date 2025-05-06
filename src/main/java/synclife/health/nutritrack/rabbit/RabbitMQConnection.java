package synclife.health.nutritrack.rabbit;

import com.rabbitmq.client.Connection;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
class RabbitMQConnection {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConnection.class);

    private final Connection connection;

    RabbitMQConnection(@ConfigProperty(name = "quarkus.application.name") String applicationName, RabbitMQClient rabbitMQClient) {
        this.connection = rabbitMQClient.connect(applicationName);
        log.info("Connection created: {}", connection);
    }

    public Connection getConnection() {
        return connection;
    }
}