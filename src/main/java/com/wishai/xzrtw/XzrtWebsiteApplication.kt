package com.wishai.xzrtw

import com.wishai.xzrtw.config.XzrtProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableAutoConfiguration(exclude = arrayOf(MultipartAutoConfiguration::class))
@EnableConfigurationProperties(XzrtProperties::class)
@EnableJpaRepositories
class XzrtWebsiteApplication

fun main(args: Array<String>) {
    SpringApplication.run(XzrtWebsiteApplication::class.java, *args)
}
