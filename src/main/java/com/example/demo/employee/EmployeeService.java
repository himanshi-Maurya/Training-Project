package com.example.demo.employee;

import java.util.List;

public interface EmployeeService {
    public Employee getEmployeeById(Long id);

    public List<Employee> getAllEmployee();

    public Employee createEmployee(Employee employee);

    public String  deleteEmployee(Long id);

    Employee updateEmployee(Employee employee);
}
