package com.gordondickens.app.repository;

import com.gordondickens.app.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
        extends JpaSpecificationExecutor<Employee>,
        JpaRepository<Employee, Long> {
}
