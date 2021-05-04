package com.example.demo.converter;


import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeConverter {

    @Autowired
    private DepartmentConverter departmentConverter;

    public EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmpId(employee.getEmpId());
        employeeDto.setEmpName(employee.getEmpName());
        employeeDto.setAge(employee.getAge());
        employeeDto.setGender(employee.getGender());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setEmailId(employee.getEmailId());
        employeeDto.setDepartments(departmentConverter.entityToDto(employee.getDepartments()));
        return employeeDto;
    }

    public List<EmployeeDto> entityToDto(List<Employee> employees) {
        return employees.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public Employee dtoToEntity(EmployeeDto dto) {
        Employee employee = new Employee();
        employee.setEmpId(dto.getEmpId());
        employee.setEmpName(dto.getEmpName());
        employee.setAge(dto.getAge());
        employee.setGender(dto.getGender());
        employee.setSalary(dto.getSalary());
        employee.setEmailId(dto.getEmailId());
        employee.setDepartments(departmentConverter.dtoToEntity(dto.getDepartments()));
        return employee;
    }

    public List<Employee> dtoToEntity(List<EmployeeDto> employees) {
        return employees.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }
}
