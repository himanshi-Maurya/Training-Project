package com.example.demo.service;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.converter.EmployeeConverter;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeConverter converter;


    @Override
    @Cacheable(value="employeesCache",key="#id")
    public EmployeeDto getEmployeeById(Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent() && employee.get().getDeleted()== Boolean.FALSE) {
            EmployeeDto dto = converter.entityToDto(employeeRepository.findById(id).get());
            return dto;
        }
        else {
               throw  new ResourceNotFoundException("User not found with id :" + id);
        }
    }

//    @Override
//    public List<Employee> getAllEmployee() {
//        List<Employee> result = employeeRepository.findAll();
//        if (result.size() > 0) {
//            return result;
//        } else {
//            return new ArrayList<Employee>();
//        }
//    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = converter.dtoToEntity(employeeDto);
        return converter.entityToDto(employeeRepository.save(employee));
    }

    @Override
    @CacheEvict(value="employeesCache",key="#id", beforeInvocation = true)
    public String deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return "Success";
        }
        throw new ResourceNotFoundException("User not found with id :"+ id);
    }

    @Override
    @CachePut(value="employeesCache",key="#id")
    public EmployeeDto updateEmployee(EmployeeDto dto, Long id) {
           Optional<Employee> existing = employeeRepository.findById(id);
           if(existing.isPresent() && (existing.get().getDeleted() == Boolean.FALSE)) {
               Employee employee = converter.dtoToEntity(dto);
               employeeRepository.save(employee);
               return converter.entityToDto(employee);
           }
          throw new ResourceNotFoundException("User not found with id :" + dto.getEmpId());
    }

    @Override
    public Page<Employee> getEmployeeByName(String name, Pageable page) {
        Page<Employee> employees = employeeRepository.getEmployeeByName(name, page);

        return employees;
    }

    @Override
    public List<EmployeeDto> getFirstFiveEmployees() {
        List<Employee> employees = employeeRepository.getFirstFiveEmployees();
        return converter.entityToDto(employees);
    }

    @Override
    @Cacheable(value="employeesCache",key="#pageNo")
    public Page<EmployeeDto> findPaginated(int pageNo, int pageSize, String sortBy) throws InterruptedException {
        Thread.sleep(4000);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(sortBy).descending());
        Page<EmployeeDto> page = this.employeeRepository.getAllEmployees(pageable);
        return  page;
    }
}
