package com.example.demo.controller;

import com.example.demo.converter.EmployeeConverter;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import com.sun.istack.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@Api(value = "employeeData", description = "Operations pertaining to employee")
@RequestMapping(value = "/api/employees")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeConverter converter;

    @ApiOperation(value = "View a list of available employees", response = Iterable.class)
    @GetMapping("")
    public Page<EmployeeDto> getAllEmployees(@RequestParam(defaultValue = "1") int pageNo,
                                                  @RequestParam(defaultValue = "3") int pageSize,
                                                  @RequestParam(defaultValue = "empId") String sortBy) {

        Page<EmployeeDto> paginatedEmployeeList = employeeService.findPaginated(pageNo, pageSize, sortBy);
     return paginatedEmployeeList;

    }

    @RequestMapping(value = "/userName", method = RequestMethod.GET)
    public String printUser(ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();


        return name;

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
    public String deleteEmployees(@PathVariable(value = "id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return "Success";
    }

    @ApiOperation(value = "All the Departments of a particular Employee")
    @GetMapping("/{id}/departments")
    public List<DepartmentDto> getDepartmentsOfParticularEmployee(@PathVariable(value = "id") Long emp_id){
        Employee employee = employeeService.getEmployeeById(emp_id);
        return converter.entityToDto(employee).getDepartments();
    }

    @GetMapping("/name/{name}/{page}")
    public Page<Employee> getEmployeeByName(@PathVariable String name, @PathVariable("page") Integer page) {
        Pageable pageable = PageRequest.of(page, 1);
        Page<Employee> employees = employeeService.getEmployeeByName(name, pageable);
        return employees;
    }

    @GetMapping("/salary")
    public List<EmployeeDto> getFirstFiveEmployees(){
        List<Employee> employees = employeeService.getFirstFiveEmployees();
        List<EmployeeDto> dtos = converter.entityToDto(employees);
        return dtos;
    }



}
