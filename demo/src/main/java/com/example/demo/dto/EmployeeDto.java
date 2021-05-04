package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EmployeeDto {
    private Long empId;
    private String empName;
    private Integer age;
    private String gender;
    private int salary;
    private String emailId;
    private List<DepartmentDto> departments = new ArrayList<>();

    public EmployeeDto(Long empId,
                       String empName,
                       Integer age,
                       String gender, int salary, String emailId) {
        this.empId = empId;
        this.empName = empName;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.emailId = emailId;
    }
}
