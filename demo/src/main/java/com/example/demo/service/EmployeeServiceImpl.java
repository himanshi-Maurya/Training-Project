package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;


    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> emp = employeeRepository.findById(id);

        return emp.get();
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> result = employeeRepository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Employee>();
        }
    }

    @Override
    public Employee createEmployee(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public String deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return "Success";
    }

    @Override
    public Employee updateEmployee(Employee employee) {

        Optional<Employee> emp = employeeRepository.findById(employee.getEmpId());
        if (emp.isPresent()) {
            employeeRepository.save(employee);
            return employee;
        } else {
            return null;
        }
    }

    @Override
    public Page<Employee> getEmployeeByName(String name, Pageable page) {
        Page<Employee> employees = employeeRepository.getEmployeeByName(name, page);

        return employees;
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.employeeRepository.findAll(pageable);
    }
}
