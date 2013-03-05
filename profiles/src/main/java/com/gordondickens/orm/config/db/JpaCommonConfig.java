package com.gordondickens.orm.config.db;

import org.hibernate.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Common Settings for JPA
 *  * <ul>
 * <li>{@link Configuration} - defines this class as a Spring Configuration class</li>
 * <li>{@link org.springframework.context.annotation.ComponentScan} - replaces &lt;context:component-scan/&gt;</li>
 * <li>{@link PropertySource} - replaces &lt;context:property-placeholder/&gt;</li>
 * <li>{@link org.springframework.transaction.annotation.EnableTransactionManagement} - replaces &lt;tx:annotation-driven/&gt;</li>
 * <li>{@link org.springframework.data.jpa.repository.config.EnableJpaRepositories} - replaces Spring Data Jpa &lt;jpa:repositories/&gt;</li>
 * <li>{@link Bean} - replaces  &lt;bean/&gt;</li>
 * <li>{@link org.springframework.context.annotation.Scope} - replaces  &lt;bean scope=""/&gt;</li>
 * </ul>
 */
@Configuration
@PropertySource("classpath:/META-INF/spring/app-config.properties")
public abstract class JpaCommonConfig {
    private static final Logger logger = LoggerFactory.getLogger(JpaCommonConfig.class);
    public static final String UNDEFINED = "**UNDEFINED**";
    public static final String CONNECTION_CHAR_SET = "hibernate.connection.charSet";
    public static final String VALIDATOR_APPLY_TO_DDL = "hibernate.validator.apply_to_ddl";
    public static final String VALIDATOR_AUTOREGISTER_LISTENERS = "hibernate.validator.autoregister_listeners";

    @Autowired
    Environment environment;

    @Value("#{ environment['entity.package'] }")
    private String entityPackage = "com.gordondickens.orm.hibernate.domain";

    /*
     * ********************************
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
//        factory.setPersistenceXmlLocation("classpath:" + this.persistenceXmlFile);
        logger.debug("\n\n****** Scanning '{}' Packages for Entities ******\n\n", entityPackage);
        factory.setPackagesToScan(entityPackage);
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
     * ********************************
     */
    protected abstract Class<? extends Dialect> getDatabaseDialect();

    /**
     * Override if using a different file for app-persistence.xml
     * <p/>
     */
//    protected void setPersistenceXmlFile(final String filepathFromClasspathRoot) {
//        if (StringUtils.trimToEmpty(filepathFromClasspathRoot) != null) {
//            this.persistenceXmlFile = filepathFromClasspathRoot;
//        }
//    }

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
        return environment.getProperty("database.hbm2ddl.auto", "none");
    }

    public String getHibernateCharSet() {
        return environment.getProperty("database.hibernateCharSet", "UTF-8");
    }

    public String getDatabaseValidationQuery() {
        return environment.getProperty("database.validation.query", UNDEFINED);
    }
}
