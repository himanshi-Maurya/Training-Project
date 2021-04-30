package com.example.demo.employee;

import org.springframework.beans.factory.annotation.Autowired;
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
        if(result.size() > 0) {
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

        Optional<Employee> emp = employeeRepository.findById(employee.getEmp_id());
        if(emp.isPresent()) {
            employeeRepository.save(employee);
            return employee;
        } else {
            return null;
        }
    }
}
