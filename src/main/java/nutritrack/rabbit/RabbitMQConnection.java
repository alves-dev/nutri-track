package nutritrack.rabbit;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
class RabbitMQConnection {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQConnection.class);

    private final Connection connection;

    RabbitMQConnection(@ConfigProperty(name = "quarkus.application.name") String applicationName,
                       @ConfigProperty(name = "quarkus.rabbitmqclient.username") String username,
                       @ConfigProperty(name = "quarkus.rabbitmqclient.password") String password,
                       @ConfigProperty(name = "quarkus.rabbitmqclient.hostname") String hostname,
                       @ConfigProperty(name = "quarkus.rabbitmqclient.port") int port) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(hostname);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setRequestedHeartbeat(30);
        try {
            this.connection = factory.newConnection(applicationName);
        } catch (TimeoutException | IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Connection created: {}", connection);
    }

    public Connection getConnection() {
        return connection;
    }
}