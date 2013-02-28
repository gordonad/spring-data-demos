package com.gordondickens.orm.config.db;

import com.gordondickens.orm.config.support.Hbm2ddlType;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.ejb.AvailableSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.Environment.*;

/**
 * Oracle DB Spring Configuration - Requires DB Driver in Classpath Demonstrates how to get Oracle Datasource from JNDI
 * Context
 * <p/>
 * &#64;Profile - Component is eligible for registration when one or more specified profiles are active
 * <p/>
 *
 * @author Gordon Dickens
 */
//@Configuration
//@Profile(DatabaseConfigProfile.ORACLE)
//@ImportResource("classpath:/META-INF/spring/oracle.properties")
public class JpaOracleJndiConfig extends JpaCommonConfig {
    private static final Logger logger = LoggerFactory.getLogger(JpaOracleJndiConfig.class);

    @Autowired
    Environment environment;

    @Value("#{ environment['jndi.datasource'] }")
    private String JAVA_JNDI_DATASOURCE = "java:jdbc/myappdatasource";

    @Override
    public DataSource dataSource() {
        DataSource dataSource = null;
        logger.debug("\n\n************ Starting JNDI Data Source Lookup ************\n\n");
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName(JAVA_JNDI_DATASOURCE);
        jndiObjectFactoryBean.setLookupOnStartup(true);
        jndiObjectFactoryBean.setCache(true);
        Object o = jndiObjectFactoryBean.getObject();
        logger.error("\n\n\nJNDI Object Factory got this... '{}'\n\n\n", o);
        if (o != null) {
            dataSource = (DataSource) o;
        } else {
            logger.error("\n\n\nData Source not found for '{}'\n\n\n", JAVA_JNDI_DATASOURCE);
        }

        logger.debug("\n\n************ Returning JNDI Data Source '{}' ************\n\n", dataSource);

        return dataSource;
    }


    @Override
    protected Class<? extends Dialect> getDatabaseDialect() {
        return Oracle10gDialect.class;
    }

    /**
     * Source {@link org.hibernate.cfg.Environment}
     *
     * @return Jpa Properties
     */
    @Override
    protected Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(HBM2DDL_AUTO, Hbm2ddlType.NONE.toValue());
        // properties.setProperty("hibernate.hbm2ddl.auto", getHbm2ddl());
        properties.setProperty(GENERATE_STATISTICS, TRUE.toString());
        properties.setProperty(SHOW_SQL, TRUE.toString());
        properties.setProperty(FORMAT_SQL, TRUE.toString());
        properties.setProperty(USE_SQL_COMMENTS, TRUE.toString());
        properties.setProperty(CONNECTION_CHAR_SET, getHibernateCharSet());
        properties.setProperty(AvailableSettings.NAMING_STRATEGY, ImprovedNamingStrategy.class.getName());
        //JBOSS Settings
        properties.setProperty(VALIDATOR_APPLY_TO_DDL, FALSE.toString());
        properties.setProperty(VALIDATOR_AUTOREGISTER_LISTENERS, FALSE.toString());

        return properties;
    }

}
