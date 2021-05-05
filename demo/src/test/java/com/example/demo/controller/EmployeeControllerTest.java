package com.example.demo.controller;

import com.example.demo.converter.EmployeeConverter;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeConverter converter;

    @MockBean
    EmployeeService employeeService;

    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1l, "name1", 12, "female", 1234, "name1@name1.com",false));

        List<EmployeeDto> employeess = new ArrayList<>();
        employeess.add(new EmployeeDto(1L, "name1", 12, "female", 1234, "name1@name1.com"));

        Mockito.when(employeeService.getAllEmployee()).thenReturn(employees);
        Mockito.when(converter.entityToDto(employees)).thenReturn(employeess);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "[{\"empId\":1,\"empName\":\"name1\",\"age\":12,\"gender\":\"female\",\"salary\":1234,\"emailId\":\"name1@name1.com\",\"departments\":[]}]";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Employee employee = new Employee(1l, "name1", 12, "female", 1234, "name1@name1.com",false);

        EmployeeDto dto = new EmployeeDto(1l, "name", 12, "female", 1234, "name1@name1.com");

        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong())).thenReturn(employee);
        Mockito.when(converter.entityToDto(employee)).thenReturn(dto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees/1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"empId\":1,\"empName\":\"name\",\"age\":12,\"gender\":\"female\",\"salary\":1234,\"emailId\":\"name1@name1.com\",\"departments\":[]}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }


    @Test
    public void testCreateEmployee() throws Exception {
        Employee employee = new Employee(1L, "name1", 12, "female", 1234, "name1@name1.com",false);

        EmployeeDto dto = new EmployeeDto(1L, "name1", 12, "female", 1234, "name1@name1.com");

        Mockito.when(converter.dtoToEntity(dto)).thenReturn(employee);
        Mockito.when(employeeService.createEmployee(new Employee())).thenReturn(employee);
        Mockito.when(converter.entityToDto(employee)).thenReturn(dto);

        String URI = "http://localhost/api/employees/";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON).content(mapToJson(dto)).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String actual = response.getContentAsString();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    void testDeleteEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employee/0").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee employee = new Employee(1l, "name1", 12, "female", 1234, "name1@name1.com",false);

        EmployeeDto dto = new EmployeeDto(1l, "name1", 12, "female", 1234, "name1@name1.com");

        Mockito.when(converter.dtoToEntity(dto)).thenReturn(employee);
        Mockito.when(employeeService.updateEmployee(Mockito.any(Employee.class))).thenReturn(employee);
        Mockito.when(converter.entityToDto(employee)).thenReturn(dto);

        String URI = "http://localhost:8080/api/employees/1";
        String expected = "{\"empId\":1,\"empName\":\"name1\",\"age\":12,\"gender\":\"female\",\"salary\":1234,\"emailId\":\"name1@name1.com\",\"departments\":[]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON).content(mapToJson(dto)).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}