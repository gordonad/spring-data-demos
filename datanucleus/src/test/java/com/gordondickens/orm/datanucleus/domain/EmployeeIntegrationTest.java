package com.gordondickens.orm.datanucleus.domain;

import com.gordondickens.orm.datanucleus.service.EmployeeService;
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

/**
 * <a href="http://www.datanucleus.org/products/accessplatform_3_0/persistence_properties.html">DataNucleus Persistence Properties Reference</a>
 * <p/>
 * For DataNucleus configuration properties:
 * <ul>
 * <li>@see {@link org.datanucleus.PropertyNames}</li>
 * <li>@see {@link org.datanucleus.api.jpa.JPAEntityManager}</li>
 * <li>@see {@link org.datanucleus.api.jpa.JPAEntityManagerFactory}</li>
 * <li>@see {@link org.datanucleus.api.jpa.JPAPropertyValidator}</li>
 * <li>@see {@link org.datanucleus.enhancer.RuntimeEnhancer}</li>
 * <li>@see {@link org.datanucleus.store.rdbms.RDBMSStoreManager}</li>
 * <li>@see {@link org.datanucleus.store.rdbms.schema.RDBMSSchemaHandler}</li>
 * </ul>
 */
@ContextConfiguration("classpath:/META-INF/spring/datanucleus-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class EmployeeIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeIntegrationTest.class);

    @Autowired
    EmployeeService employeeService;

    @Test
    public void verifyEmployCreation() {
        Employee employee = new Employee();
        employee.setFirstName("Cletus");
        employee.setLastName("Fetus");

        employeeService.saveEmployee(employee);
        assertThat("Employee MUST exist", employee, notNullValue());
        assertThat("Employee MUST have PK", employee.getId(), notNullValue());
        logger.debug("Employee id='{}' Saved", employee.getId());

        Employee employee1 = employeeService.findEmployee(employee.getId());
        assertThat("Employee Must be Found by ID", employee1.getId(), samePropertyValuesAs(employee.getId()));
        logger.debug("Found Employee: '{}'", employee1.toString());
    }
}

