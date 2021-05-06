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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Api(value = "departmentData", description = "Operations pertaining to department")
@RequestMapping(path = "api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentConverter converter;

    @ApiOperation(value = "View a list of available departments", response = Iterable.class)
    @GetMapping("")
    public Page<DepartmentDto> getAllEmployees(@RequestParam(defaultValue = "1") int pageNo,
                                             @RequestParam(defaultValue = "3") int pageSize,
                                             @RequestParam(defaultValue = "id") String sortBy) {

        Page<DepartmentDto> paginatedEmployeeList = departmentService.findPaginated(pageNo, pageSize, sortBy);
        return paginatedEmployeeList;

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
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(value = "id") Long dep_id) throws InterruptedException {
        Department department = departmentService.getDepartmentById(dep_id);
        return ResponseEntity.ok().body(converter.entityToDto(department));
    }

}