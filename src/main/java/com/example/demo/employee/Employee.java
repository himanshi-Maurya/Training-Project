package com.example.demo.employee;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emp_id;
    private String emp_name;
    private Integer age;
    private String department;



    public Employee(Long emp_id,
                    String emp_name,
                    Integer age,
                    String department) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.age = age;
        this.department = department;
    }
    public Employee() {

    }

    public Long getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(Long emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "emp_id=" + emp_id +
                ", emp_name='" + emp_name + '\'' +
                ", age=" + age +
                ", department='" + department + '\'' +
                '}';
    }

}
