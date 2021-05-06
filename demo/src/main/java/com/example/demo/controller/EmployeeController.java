package com.example.demo.controller;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.converter.EmployeeConverter;
import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Api(value = "employeeData", description = "Operations pertaining to employee")
@RequestMapping(value = "/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeConverter converter;

    @ApiOperation(value = "View a list of available employees", response = Iterable.class)
    @GetMapping("")
    public ResponseEntity<Page<EmployeeDto>> getAllEmployees(@RequestParam(defaultValue = "1") int pageNo,
                                                  @RequestParam(defaultValue = "3") int pageSize,
                                                  @RequestParam(defaultValue = "id") String sortBy) throws InterruptedException {

        Page<EmployeeDto> paginatedEmployeeList = employeeService.findPaginated(pageNo, pageSize, sortBy);
     return ResponseEntity.ok().body(paginatedEmployeeList);

    }

    @RequestMapping(value = "/userName", method = RequestMethod.GET)
    public ResponseEntity<String> printUser(ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return ResponseEntity.ok().body(name);

    }


    @ApiOperation(value = "Add an Employee")
    @PostMapping("/")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto dto) {
        EmployeeDto dto1 = employeeService.createEmployee(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto1.getEmpId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value = "Search an employee with an ID", response = Employee.class)
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws
            ResourceNotFoundException {
        EmployeeDto employee = employeeService.getEmployeeById(employeeId);
            return ResponseEntity.ok().body(employee);
    }

    @ApiOperation(value = "Update an employee ")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                      @RequestBody EmployeeDto dto)  {
        dto.setEmpId(employeeId);
        EmployeeDto emp = employeeService.updateEmployee(dto,employeeId);
            return ResponseEntity.accepted().body(emp);

    }

    @ApiOperation(value = "Delete an employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployees(@PathVariable(value = "id") Long employeeId) throws InterruptedException {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().body("Success");
    }

    @ApiOperation(value = "All the Departments of a particular Employee")
    @GetMapping("/{id}/departments")
    public ResponseEntity<List<DepartmentDto>> getDepartmentsOfParticularEmployee(@PathVariable(value = "id") Long emp_id) throws InterruptedException {
        EmployeeDto employee = employeeService.getEmployeeById(emp_id);
        return ResponseEntity.ok().body(employee.getDepartments());
    }

    @GetMapping("/name/{name}/{page}")
    public ResponseEntity<Page<Employee>> getEmployeeByName(@PathVariable String name, @PathVariable("page") Integer page) {
        Pageable pageable = PageRequest.of(page, 1);
        Page<Employee> employees = employeeService.getEmployeeByName(name, pageable);
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/salary")
    public ResponseEntity<List<EmployeeDto>> getFirstFiveEmployees(){
        List<EmployeeDto> dtos = employeeService.getFirstFiveEmployees();
        return ResponseEntity.ok().body(dtos);
    }

}
