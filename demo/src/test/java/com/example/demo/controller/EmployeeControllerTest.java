package com.example.demo.controller;

import com.example.demo.converter.EmployeeConverter;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeConverter employeeMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserDetailsService userDetailsService;


    @Test
    @WithMockUser(username = "spring")
    public void allEmployees() throws Exception {
        List<EmployeeDto> dtoList = new ArrayList<>();
        EmployeeDto employeeDto = new EmployeeDto(1l, "name", 123, "female", 1234, "name@name.com");
        dtoList.add(employeeDto);
        Page<EmployeeDto> page = new PageImpl<EmployeeDto>(dtoList);

        Mockito.when(employeeService.findPaginated(1,1,"empId")).thenReturn(page);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employees?pageNo=1&pageSize=1&sortBy=empId")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = "{\"content\":[{\"empId\":1,\"empName\":\"name\",\"age\":123,\"gender\":\"female\",\"salary\":1234,\"emailId\":\"name@name.com\",\"departments\":[]}],\"pageable\":\"INSTANCE\",\"totalPages\":1,\"totalElements\":1,\"last\":true,\"number\":0,\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"size\":1,\"numberOfElements\":1,\"first\":true,\"empty\":false}";
        String actual = result.getResponse().getContentAsString();
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "spring")
    public void addEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(1l, "name", 12, "female",
                1234, "name@name.com");
        String expected = this.mapToJson(employeeDto);
        Mockito.when(employeeService.createEmployee(Mockito.any(EmployeeDto.class))).thenReturn(employeeDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees/")
                .accept(MediaType.APPLICATION_JSON)
                .content(expected)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "spring")
    public void deleteEmployee() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        String actual = result.getResponse().getContentAsString();
        String expected = "Success";
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "spring")
    public void updateEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto(1l, "name",
                12, "female", 1234, "name@name.com");
        String expected = this.mapToJson(employeeDto);
        Mockito.when(employeeService.updateEmployee(Mockito.any(EmployeeDto.class),Mockito.anyLong())).thenReturn(employeeDto);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:8008/api/employees/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(expected)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(HttpStatus.ACCEPTED.value(), result.getResponse().getStatus());
        Assert.assertEquals(expected, result.getResponse().getContentAsString());
    }

    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
