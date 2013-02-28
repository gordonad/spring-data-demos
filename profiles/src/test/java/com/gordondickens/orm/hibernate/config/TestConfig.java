package com.gordondickens.orm.hibernate.config;

import com.gordondickens.orm.hibernate.domain.Employee;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Gordon Dickens
 */
@Configuration
@ComponentScan(basePackages = "com.gordondickens.orm.hibernate",
        excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.gordondickens.orm.hibernate.repository")
public class TestConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Employee employee() {
        return new Employee();
    }

}
