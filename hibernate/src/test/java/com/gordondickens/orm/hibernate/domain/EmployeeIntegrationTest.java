package com.gordondickens.orm.hibernate.domain;

import com.gordondickens.orm.hibernate.config.RepositoryConfig;
import com.gordondickens.orm.hibernate.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

@ContextConfiguration(classes = RepositoryConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EmployeeIntegrationTest {
    private static final Logger logger = LoggerFactory
            .getLogger(EmployeeIntegrationTest.class);

    @Autowired
    EmployeeService employeeService;

    @Test
    public void testMarkerMethod() {
        Employee employee = new Employee();
        employee.setFirstName("Cletus");
        employee.setLastName("Fetus");

        employeeService.saveEmployee(employee);
        logger.debug("Employee Saved '{}'", employee.toString());
        assertThat("Employee MUST exist", employee, notNullValue());
        assertThat("Employee MUST have PK", employee.getId(), notNullValue());
        logger.debug("Employee {} Saved", employee.getId());

        Employee employee1 = employeeService.findEmployee(employee.getId());
        assertThat("Employee Must be Found by ID", employee1.getId(),
                samePropertyValuesAs(employee.getId()));
    }
}
