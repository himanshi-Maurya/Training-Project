package com.example.demo.dto;


import com.example.demo.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"employees"}, ignoreUnknown = true)
public class DepartmentDto {
    private Long depId;
    private String departmentName;
    private List<Employee> employees = new ArrayList<>();

    public DepartmentDto(Long depId, String departmentName) {
        this.depId = depId;
        this.departmentName = departmentName;
    }
}
