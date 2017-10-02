package com.wishai.xzrtw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(XzrtProperties.class)
public class XzrtPropertiesFactory {

    private static XzrtProperties xzrtProperties;

    @Autowired
    private XzrtPropertiesFactory(XzrtProperties xzrtProperties) {
        XzrtPropertiesFactory.xzrtProperties = xzrtProperties;
    }

    public static XzrtProperties getXzrtProperties() {
        return xzrtProperties;
    }

}
