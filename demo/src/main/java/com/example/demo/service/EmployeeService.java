package com.example.demo.service;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    public EmployeeDto getEmployeeById(Long id) ;

  //  public List<Employee> getAllEmployee();

    public EmployeeDto createEmployee(EmployeeDto employeeDto);

    public String deleteEmployee(Long id) ;

    EmployeeDto updateEmployee(EmployeeDto dto, Long id) ;

    Page<Employee> getEmployeeByName(String name, Pageable page);

    List<EmployeeDto> getFirstFiveEmployees();

    Page<EmployeeDto> findPaginated(int pageNo, int pageSize, String sortBy) throws InterruptedException;
}
