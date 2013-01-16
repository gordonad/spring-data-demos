package com.gordondickens.orm.openjpa.service;

import com.gordondickens.orm.openjpa.domain.Employee;
import com.gordondickens.orm.openjpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

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
        return employeeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

    @Override
    public void saveEmployee(final Employee employee) {
        employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Employee updateEmployee(final Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }
}
