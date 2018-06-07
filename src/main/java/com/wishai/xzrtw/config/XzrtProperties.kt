package com.wishai.xzrtw.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("xzrt")
class XzrtProperties {
    var forwardRootUrl: String? = null
    var noneImageId: Int? = null
    var uploadRootPath: String? = null
}
