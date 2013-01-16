package com.gordondickens.orm.eclipselink.service;

import com.gordondickens.orm.eclipselink.domain.Employee;

import java.util.List;

public interface EmployeeService {

	public long countAllEmployees();


	public void deleteEmployee(Employee employee);


	public Employee findEmployee(Long id);


	public List<Employee> findAllEmployees();


	public List<Employee> findEmployeeEntries(int firstResult, int maxResults);


	public void saveEmployee(Employee employee);


	public Employee updateEmployee(Employee employee);

}
