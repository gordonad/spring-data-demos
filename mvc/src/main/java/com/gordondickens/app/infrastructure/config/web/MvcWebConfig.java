package com.gordondickens.app.infrastructure.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.gordondickens.app.web", useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION)})
public class MvcWebConfig extends WebMvcConfigurerAdapter {

    // ONLY FOR serving static resources
    //    @Override
    //    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    //        configurer.enable("myapp");
    //    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/welcome");
        registry.addViewController("/uncaughtException");
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/views");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }
}
