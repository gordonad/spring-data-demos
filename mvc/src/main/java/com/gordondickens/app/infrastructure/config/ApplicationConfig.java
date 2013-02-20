package com.gordondickens.app.infrastructure.config;

import com.gordondickens.app.infrastructure.config.service.EmployeeServiceConfig;
import com.gordondickens.app.service.internal.BeansInContextLogger;
import com.gordondickens.app.service.internal.MvcMappingsLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Define any beans here that will not be discovered by scanning
 *
 * @author Gordon Dickens
 */
@Configuration
@EnableTransactionManagement
@ComponentScan("com.gordondickens.app.infrastructure.config.db")
@EnableJpaRepositories(basePackages = {"com.gordondickens.app.repository"})
@Import({EmployeeServiceConfig.class})
public class ApplicationConfig {

    @Bean
    public BeansInContextLogger beansInContext() {
        return new BeansInContextLogger();
    }

    @Bean
    public MvcMappingsLogger mvcMappingsLogger() {
        return new MvcMappingsLogger();
    }

}
