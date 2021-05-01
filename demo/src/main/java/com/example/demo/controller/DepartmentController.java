package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping(path="api/employee")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @GetMapping("/api/department")
    public List<Department> getAllDepartment(){
        return departmentRepository.findAll();
    }

    @PostMapping("/api/department")
    public Department createDepartment(@Validated @RequestBody Department department ){
        return departmentRepository.save(department);
    }

    @GetMapping("/api/department/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        Department department = departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department not found for this id ::"+id));
        return ResponseEntity.ok().body(department);
    }

    @PutMapping("/api/department/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable(value = "id") long id,
                                                   @RequestBody Department departmentDetail) throws ResourceNotFoundException{
        Department department = departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Department not found for this id ::"+id));
        department.setDepartment_name(departmentDetail.getDepartment_name());
        return ResponseEntity.ok().body(department);
    }
    @DeleteMapping("/api/department/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") long id)throws ResourceNotFoundException{
        departmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id ::"+id));

        departmentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
