package tech.exam.dauo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ConfigurationProperties("enable")
public class ConfigProperties {

  public ConfigProperties(List<String> ip) {
    this.ip = ip;
  }

  private List<String> ip;

    public List<String> getIp() {
        return this.ip;
    }
}
