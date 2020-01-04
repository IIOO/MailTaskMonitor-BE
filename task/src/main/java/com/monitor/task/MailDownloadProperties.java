package com.monitor.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail.download.attachment")
@PropertySource("classpath:application.properties")
@Getter
@Setter
public class MailDownloadProperties {
    private String path;
}
