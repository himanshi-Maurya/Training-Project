package com.example.demo.employeetest;

import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
        Employee empOne = new Employee(1L,"Himanshi",21,"Technical");
        Employee empTwo = new Employee(2L,"Deepti",20,"HR");
        Employee empThree = new Employee(3L,"krishan",21,"Finance");

        list.add(empOne);
        list.add(empTwo);
        list.add(empThree);

        Mockito.when(employeeRepository.findAll()).thenReturn(list);

        List<Employee> employeeList = employeeService.getAllEmployee();
        assertEquals(3,employeeList.size());

    }

    @Test
    public void getEmployeeById()
    {
       Employee emp = new Employee(1L,"Himanshi",21,"Technical");

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));

        Employee employee = employeeService.getEmployeeById(1L);

        assertEquals("Himanshi",employee.getEmp_name());
        assertEquals(21,employee.getAge());
        assertEquals("Technical",employee.getDepartment());
    }

    @Test
    public void createEmployee()
    {
        Employee emp = new Employee(1L,"Himanshi",21,"Technical");
        Mockito.when(employeeRepository.save(emp)).thenReturn(emp);
        Employee employee = employeeService.createEmployee(emp);
        assertEquals("Himanshi",employee.getEmp_name());
        assertEquals(21,employee.getAge());
        assertEquals("Technical",employee.getDepartment());
    }

    @Test
    public void deleteEmployee()
    {
         employeeService.deleteEmployee(1L);
        assertEquals("Success",employeeService.deleteEmployee(1L));
    }

    @Test
    public void updateEmployee(){
        Employee emp = new Employee(1L,"Himanshi",21,"Technical");
        Employee emp1 = new Employee(1L,"Deepti",21,"Technical");
     Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
     Mockito.when(employeeRepository.save(emp1)).thenReturn(emp1);

     Employee employee = employeeService.updateEmployee(emp1);
     assertEquals(emp1,employee);
    }
}
