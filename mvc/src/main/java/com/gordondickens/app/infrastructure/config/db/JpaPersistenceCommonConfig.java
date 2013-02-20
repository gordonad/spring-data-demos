/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gordondickens.app.infrastructure.config.db;

import com.gordondickens.app.domain.Employee;
import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/META-INF/spring/database.properties")
public abstract class JpaPersistenceCommonConfig {
    private static final Logger logger = LoggerFactory.getLogger(JpaPersistenceCommonConfig.class);
    public static final String UNDEFINED = "**UNDEFINED**";
    public static final String CONNECTION_CHAR_SET = "hibernate.connection.charSet";
    public static final String VALIDATOR_APPLY_TO_DDL = "hibernate.validator.apply_to_ddl";
    public static final String VALIDATOR_AUTOREGISTER_LISTENERS = "hibernate.validator.autoregister_listeners";


    @Autowired
    private AbstractEnvironment environment;


     /*
     * ********************************
     * PUBLIC  METHODS  ARE  @BEANS
     * PUBLIC  METHODS  ARE  @BEANS
     * PUBLIC  METHODS  ARE  @BEANS
     * PUBLIC  METHODS  ARE  @BEANS
     * PUBLIC  METHODS  ARE  @BEANS
     * PUBLIC  METHODS  ARE  @BEANS
     *
     * Method Name is the BEAN ID (without the parenthesis)
     * Return Type is the Bean type
     * ********************************
     */

    @Bean
    public abstract DataSource dataSource();

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        logger.debug("\n\n************ {} ************\n\n",
                getDatabaseDialect().getCanonicalName());
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabasePlatform(getDatabaseDialect().getName());
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(Employee.class.getPackage().getName());
        factory.setDataSource(dataSource());
        if (getJpaProperties() != null) {
            factory.setJpaProperties(getJpaProperties());
        }
        return factory;
    }

    @Bean
    public EntityManager entityManger() {
        return entityManagerFactory().getObject().createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    /*
     * ********************************
     * PROTECTED METHODS ARE NOT BEANS
     * PROTECTED METHODS ARE NOT BEANS
     * PROTECTED METHODS ARE NOT BEANS
     * PROTECTED METHODS ARE NOT BEANS
     * PROTECTED METHODS ARE NOT BEANS
     * PROTECTED METHODS ARE NOT BEANS
     * ********************************
     */
    protected abstract Class<? extends Dialect> getDatabaseDialect();

    /**
     * Override if configuring JPA Properties
     * <p/>
     *
     * @return Jpa Properties to be used in Entity Manager Factory
     */
    protected Properties getJpaProperties() {
        return null;
    }


    public String getDatabaseName() {
        return environment.getProperty("database.name", UNDEFINED);
    }

    public String getHost() {
        return environment.getProperty("database.host", UNDEFINED);
    }

    public String getPort() {
        return environment.getProperty("database.port", UNDEFINED);
    }

    public String getUrl() {
        return environment.getProperty("database.url", UNDEFINED);
    }

    public String getUser() {
        return environment.getProperty("database.username", UNDEFINED);
    }

    public String getPassword() {
        return environment.getProperty("database.password", UNDEFINED);
    }

    public String getDriverClassName() {
        return environment.getProperty("database.driverClassName", UNDEFINED);
    }

    public String getDialect() {
        return environment.getProperty("database.dialect", UNDEFINED);
    }

    public String getDatabaseVendor() {
        return environment.getProperty("database.vendor", UNDEFINED);
    }

    public String getHbm2ddl() {
        return environment.getProperty("hibernate.hbm2ddl.auto", Hbm2ddlType.NONE.toValue());
    }

    public String getHibernateCharSet() {
        return environment.getProperty("database.hibernateCharSet", "UTF-8");
    }

    public String getDatabaseValidationQuery() {
        return environment.getProperty("database.validation.query", UNDEFINED);
    }

}
