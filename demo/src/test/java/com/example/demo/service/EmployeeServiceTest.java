package com.example.demo.service;

import com.example.demo.converter.EmployeeConverter;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    EmployeeConverter converter;

    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void getAllEmployee() throws InterruptedException {
        List<EmployeeDto> list = new ArrayList<EmployeeDto>();
        EmployeeDto empOne = new EmployeeDto(1L, "name1", 20, "tech-1", 1234, "name1@name1.com");
        EmployeeDto empTwo = new EmployeeDto(2L, "name2", 21, "tech-2", 1234, "name2@name2.com");
        EmployeeDto empThree = new EmployeeDto(3L, "name3", 22, "tech-3", 1234, "name3@name3.com");
        list.add(empOne);
        list.add(empTwo);
        list.add(empThree);
        // Page<EmployeeDto> employeeDtoPage = employeeService.findPaginated(1,1,"empId");
        Page<EmployeeDto> page = new PageImpl<EmployeeDto>(list);
        System.out.println(page.getContent());
        Pageable pageable = PageRequest.of(page.getNumber(), page.getTotalPages(), page.getSort());
        Mockito.when(employeeRepository.getAllEmployees(pageable)).thenReturn(page);
         String expected = "[EmployeeDto(empId=1, empName=name1, age=20, gender=tech-1, salary=1234, emailId=name1@name1.com, departments=[]), EmployeeDto(empId=2, empName=name2, age=21, gender=tech-2, salary=1234, emailId=name2@name2.com, departments=[]), EmployeeDto(empId=3, empName=name3, age=22, gender=tech-3, salary=1234, emailId=name3@name3.com, departments=[])]";
          employeeService.findPaginated(1, 1, "empId");
        assertEquals(expected, page.getContent().toString());
    }

    @Test
    public void getEmployeeById() throws InterruptedException {
        EmployeeDto emp = new EmployeeDto(1L, "name", 12, "female", 1234, "name@name.com");
        Employee employee = converter.dtoToEntity(emp);
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        EmployeeDto dto = employeeService.getEmployeeById(1L);
        assertEquals("name", dto.getEmpName());
        assertEquals(12, dto.getAge());
        assertEquals("female", employee.getGender());
        assertEquals(1234, employee.getSalary());
        assertEquals("name@name.com", employee.getEmailId());
    }

    @Test
    public void createEmployee() {
        EmployeeDto emp = new EmployeeDto(1L, "name", 12, "female", 1234, "name@name.com");
        Employee employee = converter.dtoToEntity(emp);
        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(employee);
        EmployeeDto dto = employeeService.createEmployee(emp);
        assertEquals("name", dto.getEmpName());
        assertEquals(12, dto.getAge());
        assertEquals("female", dto.getGender());
        assertEquals(1234, dto.getSalary());
        assertEquals("name@name.com", dto.getEmailId());
    }

    @Test
    public void deleteEmployee() {
        EmployeeDto emp = new EmployeeDto(1L, "name", 12, "female", 1234, "name@name.com");
        Employee employee = converter.dtoToEntity(emp);
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));
        assertEquals("Success", employeeService.deleteEmployee(1L));
    }

    @Test
    public void updateEmployee() {
        EmployeeDto empDto1 = new EmployeeDto(1L, "name1", 12, "female", 1234, "name1@name1.com");
        EmployeeDto empDto2 = new EmployeeDto(2L, "name2", 12, "female", 1234, "name2@name2.com");
        Employee emp1 = converter.dtoToEntity(empDto1);
        Employee emp2 = converter.dtoToEntity(empDto2);
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(emp1));
        Mockito.when(employeeRepository.save(emp1)).thenReturn(emp2);
        EmployeeDto employee = employeeService.updateEmployee(empDto2, empDto2.getEmpId());
        assertEquals(empDto2, employee);
    }
}
