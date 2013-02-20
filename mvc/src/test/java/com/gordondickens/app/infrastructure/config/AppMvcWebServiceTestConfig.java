package com.gordondickens.app.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Date: 2012-12-12
 * <p/>
 *
 * @author Gordon Dickens
 */
@Configuration
@Import({AppTestConfig.class, MvcWebServiceTestConfig.class})
public class AppMvcWebServiceTestConfig {
}
