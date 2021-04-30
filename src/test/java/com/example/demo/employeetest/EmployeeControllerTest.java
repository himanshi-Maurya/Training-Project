package com.example.demo.employeetest;
import com.example.demo.employee.Employee;
import com.example.demo.employee.EmployeeController;
import com.example.demo.employee.EmployeeRepository;
import com.example.demo.employee.EmployeeService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
class EmployeeControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    String contentEmployee = "{\"himanshi\":\"john\",\"lastName\":\"doe\",\"age\":21,\"gender\":\"male\",\"salary\":33000,\"email\":\"john@gmail.com\"}";

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1l, "Himanshi", 21, "technical"));

        Mockito.when(employeeService.getAllEmployee()).thenReturn(employees);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employee/").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"emp_id\":1,\"emp_name\":\"Himanshi\",\"age\":21,\"department\":\"technical\"}]";


        assertEquals(expected,result.getResponse().getContentAsString());
    }

    @Test
    void getEmployeeById() throws Exception {
        Employee employee = new Employee(1l, "Himanshi", 21, "technical");

        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong())).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employee/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String example = "{\"emp_id\":1,\"emp_name\":\"Himanshi\",\"age\":21,\"department\":\"technical\"}";
        assertEquals(example,result.getResponse().getContentAsString());
    }

    @Test
    void testCreateEmployee() throws Exception {

        Employee employee = new Employee(1L, "khkuh",21, "john@gmail.com");

        Mockito.when(employeeService.createEmployee(new Employee())).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/employee/")
                .accept(MediaType.APPLICATION_JSON)
                .content(contentEmployee)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/api/employee/",
                response.getHeader(HttpHeaders.LOCATION));
    }

       @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employee/0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    void updateEmployee() throws Exception {
        Employee employee = new Employee(1l, "john", 21,  "john@gmail.com");
        Mockito.when(employeeService.updateEmployee(Mockito.any(Employee.class))).thenReturn(employee);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:8080/api/employee/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(contentEmployee);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String expected = "{emp_id:1,emp_name:john,age:21,department:john@gmail.com}";
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}