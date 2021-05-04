package com.example.demo.controller;

import com.example.demo.converter.EmployeeConverter;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@Api(value = "employeeData", description = "Operations pertaining to employee")
@RequestMapping(value = "/api/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeConverter converter;

    @ApiOperation(value = "View a list of available employees", response = Iterable.class)
    @GetMapping("/")
    public List<EmployeeDto> getAllEmployees(Model model) {
        return findPaginated(1, model);
    }

    @ApiOperation(value = "Add an Employee")
    @PostMapping("/")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto dto) {
        Employee employee = converter.dtoToEntity(dto);
        employeeService.createEmployee(employee);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(converter.entityToDto(employee).getEmpId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Search an employee with an ID", response = Employee.class)
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok().body(converter.entityToDto(employee));
    }

    @ApiOperation(value = "Update an employee ")
    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable(value = "id") Long employeeId,
                                      @RequestBody EmployeeDto dto) {
        Employee employee = converter.dtoToEntity(dto);
        employee.setEmpId(employeeId);
        employee.setLastModifiedDate(new Date());
        Employee emp = employeeService.updateEmployee(employee);
        return converter.entityToDto(emp);
    }

    @ApiOperation(value = "Delete an employee")
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return "Success";
    }

    @ApiOperation(value = "All the Departments of a particular Employee")
    @GetMapping("/{id}/departments")
    public List<DepartmentDto> getDepartmentsOfParticularEmployee(@PathVariable(value = "id") Long emp_id) {
        Employee employee = employeeService.getEmployeeById(emp_id);
        return converter.entityToDto(employee).getDepartments();
    }

    @GetMapping("/name/{name}/{page}")
    public Page<Employee> getEmployeeByName(@PathVariable String name, @PathVariable("page") Integer page) {
        Pageable pageable = PageRequest.of(page, 1);
        Page<Employee> employees = employeeService.getEmployeeByName(name, pageable);
        return employees;
    }

    @GetMapping("page/{pageNo}")
    public List<EmployeeDto> findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 3;
        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize);
        List<Employee> employees = page.getContent();
        List<EmployeeDto> dto = converter.entityToDto(employees);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("departments", dto);
        return dto;
    }

}
