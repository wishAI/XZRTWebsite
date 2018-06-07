package com.wishai.xzrtw.config

import com.wishai.xzrtw.component.CustomMultipartResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import java.util.*

@Configuration
@EnableConfigurationProperties(XzrtProperties::class)
class XzrtConfig @Autowired
constructor(private val config: XzrtProperties) {

    @Bean
    fun multipartResolver(): CustomMultipartResolver {
        val multipartResolver = CustomMultipartResolver()
        multipartResolver.setMaxUploadSize(500000000)
        return multipartResolver
    }

    @Bean
    fun corsFilter(): CorsFilter {
        // initialize config
        val config = CorsConfiguration()
        config.allowCredentials = true

        // add allowed origins and sources
        val origins = this.config.forwardRootUrl!!.split("^")
        config.allowedOrigins = origins
        config.addAllowedHeader("*")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("POST")

        // apply config to sources
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}
