package com.gordondickens.app.infrastructure.config;

import com.gordondickens.app.infrastructure.config.db.JpaHsqlEmbeddedConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Date: 2012-12-12
 * <p/>
 *
 * @author Gordon Dickens
 */
@Configuration
@EnableTransactionManagement
@Import({ApplicationConfig.class, JpaHsqlEmbeddedConfig.class})
public class AppTestConfig {
}
