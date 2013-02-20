package com.gordondickens.app.infrastructure.config;

import com.gordondickens.app.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Gordon Dickens
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppTestConfig.class})
public class ApplicationConfigTest {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigTest.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    EmployeeService employeeService;

    @Test
    public void verifyAllWiresUpCorrectly() {
        assertThat(applicationContext, notNullValue());
        assertThat(employeeService, notNullValue());
    }
}
