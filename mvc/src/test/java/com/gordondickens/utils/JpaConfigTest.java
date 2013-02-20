package com.gordondickens.utils;

import com.gordondickens.app.infrastructure.config.AppTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gordon
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppTestConfig.class})
public class JpaConfigTest {
    private static final Logger logger = LoggerFactory.getLogger(JpaConfigTest.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BeanFactory beanFactory;

    @Test
    public void doIt() {

        logger.debug("Test Done");
    }
}
