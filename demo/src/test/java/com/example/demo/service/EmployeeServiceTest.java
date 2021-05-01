package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;


    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void getAllEmployee() {
        List<Employee> list = new ArrayList<Employee>();
        Employee empOne = new Employee(1L, "name1", 20, "tech-1", 1234, "name1@name1.com");
        Employee empTwo = new Employee(2L, "name2", 21, "tech-2", 1234, "name2@name2.com");
        Employee empThree = new Employee(3L, "name3", 22, "tech-3", 1234, "name3@name3.com");

        list.add(empOne);
        list.add(empTwo);
        list.add(empThree);

        Mockito.when(employeeRepository.findAll()).thenReturn(list);

        List<Employee> employeeList = employeeService.getAllEmployee();
        assertEquals(3, employeeList.size());

    }

    @Test
    public void getEmployeeById() {
        Employee emp = new Employee(1L, "name", 20, "Tech", 1234, "name@name.com");

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));

        Employee employee = employeeService.getEmployeeById(1L);

        assertEquals("name", employee.getEmp_name());
        assertEquals(20, employee.getAge());
        assertEquals("tech", employee.getDepartment());
        assertEquals(1234, employee.getSalary());
        assertEquals("name@name.com", employee.getEmail_id());
    }

    @Test
    public void createEmployee() {
        Employee emp = new Employee(1L, "name", 20, "Tech", 1234, "name@name.com");
        Mockito.when(employeeRepository.save(emp)).thenReturn(emp);
        Employee employee = employeeService.createEmployee(emp);
        assertEquals("name", employee.getEmp_name());
        assertEquals(20, employee.getAge());
        assertEquals("tech", employee.getDepartment());
        assertEquals(1234, employee.getSalary());
        assertEquals("name@name.com", employee.getEmail_id());
    }

    @Test
    public void deleteEmployee() {
        employeeService.deleteEmployee(1L);
        assertEquals("Success", employeeService.deleteEmployee(1L));
    }

    @Test
    public void updateEmployee() {
        Employee emp1 = new Employee(1L, "name1", 20, "tech1", 1234, "name1@name1.com");
        Employee emp2 = new Employee(2L, "name2", 21, "tech2", 1234, "name2@name2.com");
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp1));
        Mockito.when(employeeRepository.save(emp2)).thenReturn(emp2);

        Employee employee = employeeService.updateEmployee(emp2);
        assertEquals(emp2, employee);
    }
}
