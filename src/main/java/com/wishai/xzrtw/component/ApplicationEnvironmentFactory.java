package com.wishai.xzrtw.component;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ApplicationEnvironmentFactory implements EnvironmentAware {

    private static Environment environment;

    private ApplicationEnvironmentFactory() {
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public static Environment getEnvironment() {
        return environment;
    }
}
