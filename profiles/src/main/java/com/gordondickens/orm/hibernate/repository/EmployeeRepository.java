package com.gordondickens.orm.hibernate.repository;

import com.gordondickens.orm.hibernate.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
        extends JpaSpecificationExecutor<Employee>,
        JpaRepository<Employee, Long> {
}
