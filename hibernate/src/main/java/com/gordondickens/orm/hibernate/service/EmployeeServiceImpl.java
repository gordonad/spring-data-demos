package com.gordondickens.orm.hibernate.service;

import com.gordondickens.orm.hibernate.domain.Employee;
import com.gordondickens.orm.hibernate.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory
            .getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public long countAllEmployees() {
        return employeeRepository.count();
    }

    @Override
    public void deleteEmployee(final Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public Employee findEmployee(final Long id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findEmployeeEntries(final int firstResult, final int maxResults) {
        return employeeRepository.findAll(
                new org.springframework.data.domain.PageRequest(firstResult
                        / maxResults, maxResults)).getContent();
    }

    @Override
    public void saveEmployee(final Employee employee) {
        Employee e2 = employeeRepository.save(employee);
        logger.debug("Employee received & saved '{}'", employee.toString());
        logger.debug("Employee returned from repository '{}'", e2.toString());
    }

    @Override
    public Employee updateEmployee(final Employee employee) {
        return employeeRepository.save(employee);
    }
}
