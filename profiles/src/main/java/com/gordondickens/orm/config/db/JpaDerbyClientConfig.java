package com.gordondickens.orm.config.db;

import com.gordondickens.orm.config.support.DatabaseConfigProfile;
import com.gordondickens.orm.config.support.Hbm2ddlType;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.DerbyTenSevenDialect;
import org.hibernate.dialect.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.Environment.*;
import static org.hibernate.ejb.AvailableSettings.NAMING_STRATEGY;

/**
 * Derby Client Configuration - assumes Derby DB is already running
 * <p/>
 * &#64;Profile - Component is eligible for registration when one or more specified profiles are active
 * <p/>
 *
 * @author Gordon Dickens
 * @see Profile - Component is eligible for registration when one or more specified profiles are active
 */

@Profile(DatabaseConfigProfile.DERBY_CLIENT)

@Configuration
@PropertySource("classpath:/META-INF/spring/derby.properties")
public class JpaDerbyClientConfig extends JpaCommonConfig {

    @Override
    @Bean(destroyMethod = "close")
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

    /**
     * Source {@link org.hibernate.cfg.Environment}
     *
     * @return Jpa Properties
     */
    @Override
    protected Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(HBM2DDL_AUTO, Hbm2ddlType.UPDATE.toValue());
        //To get the value from properties file:
        // properties.setProperty(HBM2DDL_AUTO, getHbm2ddl());
        properties.setProperty(GENERATE_STATISTICS, TRUE.toString());
        properties.setProperty(SHOW_SQL, TRUE.toString());
        properties.setProperty(FORMAT_SQL, TRUE.toString());
        properties.setProperty(USE_SQL_COMMENTS, TRUE.toString());
        properties.setProperty(CONNECTION_CHAR_SET, getHibernateCharSet());
        properties.setProperty(NAMING_STRATEGY, ImprovedNamingStrategy.class.getName());
        //JBOSS Settings
        // properties.setProperty(VALIDATOR_APPLY_TO_DDL, FALSE.toString());
        // properties.setProperty(VALIDATOR_AUTOREGISTER_LISTENERS, FALSE.toString());
        return properties;
    }


    @Override
    protected Class<? extends Dialect> getDatabaseDialect() {
        return DerbyTenSevenDialect.class;
    }


    @Bean
    public DatabasePopulator databasePopulator(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(true);
        populator.setIgnoreFailedDrops(true);
        // populator.addScript(new ClassPathResource("/derby-sql/dml/myschema-ddl.sql"));
        // populator.addScript(new ClassPathResource("/derby-sql/dml/mydata-dml.sql"));
        try {
            populator.populate(dataSource.getConnection());
        } catch (SQLException ignored) {
        }
        return populator;
    }
}

