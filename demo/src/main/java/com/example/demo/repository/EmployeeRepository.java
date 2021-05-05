package com.example.demo.repository;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select u FROM Employee u WHERE u.empName= :n")
    public Page<Employee> getEmployeeByName(@Param("n") String name, Pageable pePageable);

  //  @Query(value = "SELECT * FROM Employee WHERE deleted=false",nativeQuery = true)
    @Query("SELECT new com.example.demo.dto.EmployeeDto(a.empId,a.empName,a.age,a.gender,a.salary,a.emailId) FROM Employee a WHERE a.deleted=false")
    public Page<EmployeeDto> getAllEmployees(Pageable pePageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE  Employee e  set e.deleted = true where e.empId=:n")
    public void isDeleted(@Param("n") Long empId);

    @Query(value="SELECT * FROM Employee ORDER BY salary LIMIT 5", nativeQuery=true)
    public List<Employee> getFirstFiveEmployees ();
}
