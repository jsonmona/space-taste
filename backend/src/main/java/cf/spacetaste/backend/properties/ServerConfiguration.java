package cf.spacetaste.backend.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {ServerProperties.class})
public class ServerConfiguration {
}
