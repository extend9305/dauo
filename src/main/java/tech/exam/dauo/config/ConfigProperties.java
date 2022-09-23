package tech.exam.dauo.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "client")
@ConstructorBinding
@Data
public class ConfigProperties {
  private String[] ip;
}
