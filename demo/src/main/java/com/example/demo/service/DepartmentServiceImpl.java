package com.example.demo.service;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Cacheable(value="departmentsCache",key="#id")
    public Department getDepartmentById(Long id) throws InterruptedException {
        Thread.sleep(4000);
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
    public Page<DepartmentDto> findPaginated(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).descending());
        return this.departmentRepository.getAllDepartments(pageable);
    }
}
