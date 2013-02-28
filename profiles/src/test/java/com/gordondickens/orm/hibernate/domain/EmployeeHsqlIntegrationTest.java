package com.gordondickens.orm.hibernate.domain;

import com.gordondickens.orm.config.db.JpaH2EmbeddedConfig;
import com.gordondickens.orm.config.db.JpaHsqlEmbeddedConfig;
import com.gordondickens.orm.config.support.DatabaseConfigProfile;
import com.gordondickens.orm.hibernate.config.TestConfig;
import com.gordondickens.orm.hibernate.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

@ActiveProfiles(DatabaseConfigProfile.HSQL_EMBEDDED)

@Transactional
@ContextConfiguration(classes = {JpaHsqlEmbeddedConfig.class, TestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeHsqlIntegrationTest {
    private static final Logger logger = LoggerFactory
            .getLogger(EmployeeHsqlIntegrationTest.class);

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
