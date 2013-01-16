package com.gordondickens.orm.querydsl.service;

import com.gordondickens.orm.querydsl.domain.Employee;
import com.gordondickens.orm.querydsl.domain.QEmployee;
import com.gordondickens.orm.querydsl.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory
            .getLogger(EmployeeServiceImpl.class);

    @Autowired
    EmployeeRepository employeeRepository;

    private static final QEmployee $ = QEmployee.employee;

    @Override
    @Transactional(readOnly = true)
    public long countAllEmployees() {
        return employeeRepository.count();
    }

    @Override
    public void deleteEmployee(final Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployee(final Long id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findEmployeeLastNameILike(final String search) {

        List<Employee> employees = new ArrayList<Employee>();
        Iterable<Employee> employeeRepositoryAll
                = employeeRepository.findAll($.lastName.containsIgnoreCase(search));
        for (Employee emp : employeeRepositoryAll) {
            employees.add(emp);
        }

        return new ArrayList<Employee>();
    }


}
