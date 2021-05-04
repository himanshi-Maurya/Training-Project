package com.example.demo.converter;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.model.Department;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class DepartmentConverter {
    public DepartmentDto entityToDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepId(department.getDepId());
        departmentDto.setDepartmentName(department.getDepartmentName());
        // departmentDto.setIsDeleted(department.getIsDeleted());
        //departmentDto.setEmployeeDto(department.getEmployees());
        return departmentDto;
    }

    public List<DepartmentDto> entityToDto(List<Department> department) {
        return department.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }

    public Department dtoToEntity(DepartmentDto departmentDto) {
        Department department = new Department();
        department.setDepId(departmentDto.getDepId());
        department.setDepartmentName(departmentDto.getDepartmentName());
        //  department.setIsDeleted(departmentDto.getIsDeleted());

        return department;
    }

    public List<Department> dtoToEntity(List<DepartmentDto> departmentDto) {
        return departmentDto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }

}