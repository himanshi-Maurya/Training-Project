package com.example.demo.service;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Department;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {
    public Department getDepartmentById(Long id) throws InterruptedException;

    public List<Department> getAllDepartments();

    public Department addDepartment(Department department);

    Page<DepartmentDto> findPaginated(int pageNo, int pageSize, String sortBy);

}
