package com.gordondickens.orm.config.db;

import com.gordondickens.orm.config.support.DatabaseConfigProfile;
import com.gordondickens.orm.config.support.Hbm2ddlType;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.ejb.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.Environment.*;

/**
 * Oracle DB Spring Configuration - Requires DB Driver in Classpath
 * <p/>
 * &#64;Profile - Component is eligible for registration when one or more specified profiles are active
 * <p/>
 *
 * @author Gordon Dickens
 */
@Configuration
@Profile(DatabaseConfigProfile.ORACLE)
@PropertySource("classpath:/META-INF/spring/oracle.properties")
public class JpaOracleConfig extends JpaCommonConfig {


    @Autowired
    private Environment environment;

    /*
     * NOTE:
     * To use a PROPERTIES FILE, set the filename above in @PropertySource
     * Access the properties with the injected Environment
     * environment.getProperty("database.url");
     * Use the second parameter to set a default if no value is found in properties files
     * environment.getProperty("database.hibernateCharSet", "UTF-8");
     *
     */


    @Override
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(getDriverClassName());
        dataSource.setUrl(getUrl());
        dataSource.setUsername(getUser());
        dataSource.setPassword(getPassword());
        dataSource.setValidationQuery(getDatabaseValidationQuery());
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(1800000);
        dataSource.setNumTestsPerEvictionRun(3);
        dataSource.setMinEvictableIdleTimeMillis(1800000);
        return dataSource;
    }

    @Override
    protected Class<? extends Dialect> getDatabaseDialect() {
        return Oracle10gDialect.class;
    }

    /**
     * @return Jpa Properties
     * @see org.hibernate.cfg.Environment
     * @see org.hibernate.ejb.HibernatePersistence
     */
    @Override
    protected Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(HBM2DDL_AUTO, Hbm2ddlType.NONE.toValue());
        //To get the value from properties file:
        // properties.setProperty(HBM2DDL_AUTO, getHbm2ddl());
        properties.setProperty(GENERATE_STATISTICS, TRUE.toString());
        properties.setProperty(SHOW_SQL, TRUE.toString());
        properties.setProperty(FORMAT_SQL, TRUE.toString());
        properties.setProperty(USE_SQL_COMMENTS, TRUE.toString());
        properties.setProperty(CONNECTION_CHAR_SET, getHibernateCharSet());
        properties.setProperty(AvailableSettings.NAMING_STRATEGY, ImprovedNamingStrategy.class.getName());
        //JBOSS Settings
        // properties.setProperty(VALIDATOR_APPLY_TO_DDL, FALSE.toString());
        // properties.setProperty(VALIDATOR_AUTOREGISTER_LISTENERS, FALSE.toString());

        return properties;
    }
}
