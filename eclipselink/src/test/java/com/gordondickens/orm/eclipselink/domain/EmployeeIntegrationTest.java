package com.gordondickens.orm.eclipselink.domain;

import com.gordondickens.orm.eclipselink.service.EmployeeService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * For Configuration Properties:
 * <ul>
 *     <li>@see {@link org.eclipse.persistence.config.PersistenceUnitProperties}</li>
 * </ul>
 */
@ContextConfiguration("classpath:/META-INF/spring/eclipselink-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class EmployeeIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeIntegrationTest.class);

    @Autowired
    EmployeeService employeeService;

    @Test
    public void verifyEmployeeCreate() {
        Employee employee = new Employee();
        employee.setFirstName("Cletus");
        employee.setLastName("Fetus");
        employee.setUsername("cfetus");

        employeeService.saveEmployee(employee);
        assertNotNull("Employee MUST exist", employee);
//        assertNotNull("Employee MUST have PK", employee.getId());
        logger.debug("Employee {} Saved", employee.getId());
        logger.debug("***** \n\t {} \n ******", employee.toString());

        Employee employee1 = employeeService.findEmployee(employee.getId());
        assertSame("Employee Must be Found by ID", employee1.getId(), employee.getId());
    }

    @Ignore
    @Test(expected = org.springframework.transaction.TransactionSystemException.class)
    public void testUniqueConstraint() {
        Employee employee = new Employee();
        employee.setFirstName("Cletus");
        employee.setLastName("Fetus");
        employee.setUsername("cfetus");

        employeeService.saveEmployee(employee);
        assertThat("Employee MUST exist", employee, notNullValue());

        employee = new Employee();
        employee.setFirstName("Bobby");
        employee.setLastName("Bobber");
        employee.setUsername("cfetus");

        employeeService.saveEmployee(employee);
        assertThat("Employee MUST exist", employee, notNullValue());
    }

}

