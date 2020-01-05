package com.monitor.task.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "store.connection")
@PropertySource("classpath:application.properties")
@Getter
@Setter
public class StoreConnectionProperties {
    private String host;
    private String username;
    private String password;
}
