package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public Department getDepartmentById(Long id) {
        Optional<Department> dep = departmentRepository.findById(id);
        return dep.get();
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> result = departmentRepository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Department>();
        }
    }

    @Override
    public Department addDepartment(Department department) {
        departmentRepository.save(department);
        return department;
    }

    @Override
    public Page<Department> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.departmentRepository.findAll(pageable);
    }
}
