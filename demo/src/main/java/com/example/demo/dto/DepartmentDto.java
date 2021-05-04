package com.example.demo.dto;


import com.example.demo.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"employees"}, ignoreUnknown = true)
public class DepartmentDto {
    private Long depId;
    private String departmentName;
    private List<Employee> employees = new ArrayList<>();
}
