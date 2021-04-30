package com.example.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
//@RequestMapping(path="api/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;


    @GetMapping("/api/employee")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployee();
    }

//    @PostMapping("/api/employee")
//    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
//        employeeService.createEmployee(employee);
//        URI location
//                = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(
//                        employee.getEmp_id())
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }


    @PostMapping("/api/employee")
   public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getEmp_id()).toUri();
        return ResponseEntity.created(location).build();
    }

//    @PostMapping("/api/employee")
//    public Employee createEmployee(@Validated @RequestBody Employee employee ){
//        return employeeService.createEmployee(employee);
//    }




    @GetMapping("/api/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
 Employee employee = employeeService.getEmployeeById(employeeId);
 return ResponseEntity.ok().body(employee);
    }

    @PutMapping("/api/employee/{id}")
    public Employee updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @RequestBody Employee employee) {
       employee.setEmp_id(employeeId);
       return employeeService.updateEmployee(employee);
    }
    @DeleteMapping("/api/employee/{id}")
  public String deleteEmployee(@PathVariable(value = "id") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return "Success";
}
}
