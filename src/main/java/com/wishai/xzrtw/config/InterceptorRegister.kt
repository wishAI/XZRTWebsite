package com.wishai.xzrtw.config

import com.wishai.xzrtw.inteceptor.UserHandlerInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class InterceptorRegister : WebMvcConfigurerAdapter() {
    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry!!.addInterceptor(UserHandlerInterceptor())
                .addPathPatterns("/manager/**")
    }
}
