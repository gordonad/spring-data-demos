package com.gordondickens.orm.eclipselink.repository;

import com.gordondickens.orm.eclipselink.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
        extends JpaSpecificationExecutor<Employee>,
        JpaRepository<Employee, Long> {
}
