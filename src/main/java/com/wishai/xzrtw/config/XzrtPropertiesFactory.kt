package com.wishai.xzrtw.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component("XzrtPropertiesFactory")
@EnableConfigurationProperties(XzrtProperties::class)
class XzrtPropertiesFactory
@Autowired
constructor(xzrtProperties: XzrtProperties) {

    init {
        XzrtPropertiesFactory.xzrtProperties = xzrtProperties
    }

    companion object {
        var xzrtProperties: XzrtProperties? = null
            private set
    }

}
