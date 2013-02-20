package com.gordondickens.app.infrastructure.config.service;

import com.gordondickens.app.domain.Employee;
import com.gordondickens.app.infrastructure.config.AppTestConfig;
import com.gordondickens.app.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    public void verifyFind() {
        assertThat(employeeService, notNullValue());

        Employee employee = new Employee();
        employee.setComments("hi there");
        employee.setFirstName("gordon");
        employee.setLastName("dickens");
        employeeService.saveEmployee(employee);

        Employee bean = employeeService.findEmployee(1l);
        assertThat(bean, notNullValue());

    }


}
