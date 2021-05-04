package com.example.demo.controller;

import com.example.demo.converter.DepartmentConverter;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Api(value = "departmentData", description = "Operations pertaining to department")
@RequestMapping(path = "api/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentConverter converter;

    @ApiOperation(value = "View a list of available departments", response = Iterable.class)
    @GetMapping("/")
    public List<DepartmentDto> getAllDepartments(Model model) {
        return findPaginated(1, model);
    }

    @ApiOperation(value = "Add a Department")
    @PostMapping("/")
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto dto) {
        Department department = converter.dtoToEntity(dto);
        departmentService.addDepartment(department);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(converter.entityToDto(department).getDepId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Search a department with an ID", response = Department.class)
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(value = "id") Long dep_id) {
        Department department = departmentService.getDepartmentById(dep_id);
        return ResponseEntity.ok().body(converter.entityToDto(department));
    }

    @GetMapping("page/{pageNo}")
    public List<DepartmentDto> findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 3;
        Page<Department> page = departmentService.findPaginated(pageNo, pageSize);
        List<Department> departments = page.getContent();
        List<DepartmentDto> dto = converter.entityToDto(departments);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("departments", dto);
        return dto;
    }
}