package com.gordondickens.orm.openjpa.domain;

import com.gordondickens.orm.openjpa.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;


@ContextConfiguration("classpath:/META-INF/spring/openjpa-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class EmployeeIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeIntegrationTest.class);

    @Autowired
    EmployeeService employeeService;

    @Test
    public void verifyCreate() {
        Employee employee = new Employee();
        employee.setFirstName("Cletus");
        employee.setLastName("Fetus");

        employeeService.saveEmployee(employee);
        assertThat("Employee MUST exist", employee, notNullValue());
        assertThat("Employee MUST have PK", employee.getId(), notNullValue());
        logger.debug("Employee {} Saved", employee.getId());

        Employee employee1 = employeeService.findEmployee(employee.getId());
        assertThat("Employee Must be Found by ID", employee1.getId(),
                samePropertyValuesAs(employee.getId()));
    }
}
