package cf.spacetaste.backend.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="server.info")
@ConstructorBinding
@RequiredArgsConstructor
@ToString
@Getter
public class ServerProperties {
    private final String baseUrl;
}
