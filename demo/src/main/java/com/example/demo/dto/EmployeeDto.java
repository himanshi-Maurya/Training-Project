package com.example.demo.dto;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EmployeeDto {
    private Long empId;
    private String empName;
    private Integer age;
    private String gender;
    private Integer salary;
    private String emailId;
    private List<DepartmentDto> departments = new ArrayList<>();



    public EmployeeDto(Long empId, Employee employee) {
        this.empId = empId;
        this.empName = employee.getEmpName();
        this.age = employee.getAge();
        this.gender = employee.getGender();
        this.salary = employee.getSalary();
        this.emailId = employee.getEmailId();
        this.departments = mapListToDto(employee.getDepartments());
    }

    public EmployeeDto(Long empId, String empName, Integer age, String gender, Integer salary, String emailId) {
        this.empId = empId;
        this.empName = empName;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.emailId = emailId;
    }

    public DepartmentDto mapToDto(Department department){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepId(department.getId());
        departmentDto.setDepartmentName(department.getDepartmentName());
        return  departmentDto;
    }

    public List<DepartmentDto> mapListToDto(List<Department> departments){
        return departments.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
    }
}
