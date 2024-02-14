package rf.db.access.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
public class ConnectionConfig {

    @Bean
    ConnectionFactoryInitializer initializer(@NonNull ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        return initializer;
    }
}
