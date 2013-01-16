package com.gordondickens.orm.querydsl.repository;

import com.gordondickens.orm.querydsl.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Gordon Dickens
 */
@Repository
@Transactional
public interface EmployeeRepository
        extends JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee>,
        QueryDslPredicateExecutor<Employee> {

}
