package com.example.demo.service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    public Employee getEmployeeById(Long id);

    public List<Employee> getAllEmployee();

    public Employee createEmployee(Employee employee);

    public String deleteEmployee(Long id);

    Employee updateEmployee(Employee employee);

    Page<Employee> getEmployeeByName(String name, Pageable page);

    List<Employee> getFirstFiveEmployees();

    Page<EmployeeDto> findPaginated(int pageNo, int pageSize, String sortBy);
}
