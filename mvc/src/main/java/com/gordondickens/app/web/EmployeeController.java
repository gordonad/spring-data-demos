package com.gordondickens.app.web;

import com.gordondickens.app.domain.Employee;
import com.gordondickens.app.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Date: 2012-12-06
 * <p/>
 * RESTful Controller
 * &#64;ResponseBody - Automatically Marshalls response data based on either:
 * HTTP Accept Header type
 * Path Extension such as ".json"
 * <p/>
 * See: {@Link RestWebServiceConfig} for registered mime type mappings, will also use JAF (java activation framework)
 * JAF available in javax.activation or javax.mail in a file mime.types mapping short strings to mime.types.
 * <p/>
 *
 * @author Gordon Dickens
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;


    @RequestMapping("/{id}")
    @ResponseBody
    public Employee findOne(@PathVariable("id") long id) {
        return employeeService.findEmployee(id);
    }

    @RequestMapping("/")
    @ResponseBody
    public List<Employee> findAll() {
        List<Employee> entities = employeeService.findAllEmployees();

        return entities;
    }


}
