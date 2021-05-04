package com.example.demo.service;

import com.example.demo.model.Department;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {
    public Department getDepartmentById(Long id);

    public List<Department> getAllDepartments();

    public Department addDepartment(Department department);

    Page<Department> findPaginated(int pageNo, int pageSize);

}
