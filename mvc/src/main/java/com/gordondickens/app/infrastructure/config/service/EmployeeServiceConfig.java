package com.gordondickens.app.infrastructure.config.service;

import com.gordondickens.app.service.EmployeeService;
import com.gordondickens.app.service.EmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Define any beans here that will not be discovered by scanning
 *
 * @author Gordon Dickens
 */
@Configuration
public class EmployeeServiceConfig {

    @Bean
    public EmployeeService catalogCategoryService() {
        return new EmployeeServiceImpl();
    }
}
