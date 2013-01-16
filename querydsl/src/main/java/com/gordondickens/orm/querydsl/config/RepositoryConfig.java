package com.gordondickens.orm.querydsl.config;

import com.gordondickens.orm.querydsl.domain.Employee;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Date: 6/18/12 Time: 4:34 PM
 * <p/>
 * <ul>
 * <li>{@link Configuration} - defines this class as a Spring Configuration class</li>
 * <li>{@link ComponentScan} - replaces &lt;context:component-scan/&gt;</li>
 * <li>{@link PropertySource} - replaces &lt;context:property-placeholder/&gt;</li>
 * <li>{@link EnableTransactionManagement} - replaces &lt;tx:annotation-driven/&gt;</li>
 * <li>{@link EnableJpaRepositories} - replaces Spring Data Jpa &lt;jpa:repositories/&gt;</li>
 * <li>{@link Bean} - replaces  &lt;bean/&gt;</li>
 * <li>{@link Scope} - replaces  &lt;bean scope=""/&gt;</li>
 * </ul>
 *
 * @author Gordon Dickens
 */
@Configuration
@ComponentScan(basePackages = {"com.gordondickens.orm.querydsl.domain",
        "com.gordondickens.orm.querydsl.service"})
//@ComponentScan(basePackages = "com.gordondickens.orm.querydsl",
//        excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@PropertySource("classpath:META-INF/spring/querydsl-db.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.gordondickens.orm.querydsl.repository")
public class RepositoryConfig {
    private static final Logger logger = LoggerFactory
            .getLogger(RepositoryConfig.class);

    @Value("#{ environment['database.driverClassName']?:'' }")
    private String dbDriverClass;
    @Value("#{ environment['database.url']?:'' }")
    private String dbUrl;
    @Value("#{ environment['database.username']?:'' }")
    private String dbUserName;
    @Value("#{ environment['database.password']?:'' }")
    private final String dbPassword = "";
    @Value("#{ environment['database.vendor']?:'' }")
    private String dbVendor;

    @Autowired
    BeanFactory beanFactory;

    @Autowired
    private AbstractEnvironment environment;

//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public Employee employee() {
//        logger.debug("*** Creating Employee");
//        return new Employee();
//    }

    @Bean
    public DataSource dataSource() {
        logger.trace("URL '{}'", dbUrl);
        logger.trace("Driver '{}'", dbDriverClass);
        logger.trace("UserName '{}'", dbUserName);
        logger.trace("dbPassword '{}'", dbPassword);
        BasicDataSource bean = new BasicDataSource();
        bean.setPassword(dbPassword);
        bean.setUrl(dbUrl);
        bean.setUsername(dbUserName);
        bean.setDriverClassName(dbDriverClass);
        bean.setTestOnBorrow(true);
        bean.setTestOnReturn(true);
        bean.setTestWhileIdle(true);
        bean.setTimeBetweenEvictionRunsMillis(1800000);
        bean.setMinEvictableIdleTimeMillis(1800000);
        bean.setNumTestsPerEvictionRun(3);
        return bean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        logger.debug("Scanning Package '{}' for entities", Employee.class
                .getPackage().getName());
        bean.setPackagesToScan(Employee.class.getPackage().getName());

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.valueOf(dbVendor));
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);

        bean.setJpaVendorAdapter(jpaVendorAdapter);

        // No persistence.xml - thanks to packagesToScan
        return bean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        JpaTransactionManager bean = new JpaTransactionManager();
        bean.setEntityManagerFactory(entityManagerFactory().getObject());
        bean.setDataSource(dataSource());
        bean.afterPropertiesSet();

        return bean;
    }

    @Bean
    public EntityManager entityManager() throws Exception {
        if (entityManagerFactory() == null)
            logger.error("CEMF IS NULL");

        EntityManager bean = null;

        EntityManagerFactory entityManagerFactory = entityManagerFactory()
                .getObject();
        if (entityManagerFactory == null) {
            logger.error("EMF IS NULL");
            return null;
        } else {
            bean = entityManagerFactory.createEntityManager();
            if (bean == null) {
                logger.error("EM IS NULL");
                return null;
            }
        }

        return bean;
    }
}
