package com.hp.restaurant.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * set up static resource mapping
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("set up static resource mapping...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/"); // classpath就是resources
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
