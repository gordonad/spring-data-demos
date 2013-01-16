package com.gordondickens.orm.openjpa.repository;

import com.gordondickens.orm.openjpa.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
        extends JpaSpecificationExecutor<Employee>,
        JpaRepository<Employee, Long> {

}
