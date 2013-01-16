package com.gordondickens.orm.hibernate.config;

import com.gordondickens.orm.hibernate.domain.Employee;
import com.gordondickens.orm.hibernate.repository.EmployeeRepository;
import com.gordondickens.orm.hibernate.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * User: gordon Date: 6/18/12 Time: 4:40 PM
 */
@ContextConfiguration(classes = RepositoryConfig.class)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryConfigTest {
    private static final Logger logger = LoggerFactory
            .getLogger(RepositoryConfig.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ApplicationContext appContext;

    @Test
    public void listAppContextBeans() {
        String[] beans = appContext.getBeanDefinitionNames();
        for (String bean : beans) {
            logger.trace("Bean '{}'", bean);
        }
    }


    @Test
    public void testEmployeeRepository() {
        assertThat("Employee Repository MUST exist", employeeRepository, notNullValue());
        Employee employee = appContext.getBean(Employee.class);
        employee.setFirstName("gordon");
        employee.setLastName("Dickens");

        employeeService.saveEmployee(employee);
        logger.debug(employee.toString());

        List<Employee> employees = employeeRepository.findAll();
        logger.debug("{} Employees Found", employees.size());
        for (Employee emp : employees) {
            logger.debug("FOUND '{}'", emp.toString());
        }
    }
}
