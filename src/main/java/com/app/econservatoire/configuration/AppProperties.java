package com.app.econservatoire.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "app.frontend")
@Component
@Getter
@Setter
public class AppProperties {
    private String url;
    private String resetPath;
    private String verifyPath;
}
