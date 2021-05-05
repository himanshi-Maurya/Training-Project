package com.example.demo.model;

import com.example.demo.audit.Auditable;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Employee extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated employee ID")
    @Column(name = "empId")
    private Long empId;
    @ApiModelProperty(notes = "The name of the employee")
    @Column(name = "empName")
    private String empName;
    @ApiModelProperty(notes = "The age of the employee")
    @Column(name = "age")
    private Integer age;
    @ApiModelProperty(notes = "The name of the employee's department")
    @Column(name = "gender")
    private String gender;
    @ApiModelProperty(notes = "The salary of the employee")
    @Column(name = "salary")
    private int salary;
    @ApiModelProperty(notes = "The email-id of the employee")
    @Column(name = "emailId")
    private String emailId;
    @ApiModelProperty(notes = "The email-id of the employee")
    @Column(name = "deleted")
    private Boolean deleted = false;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "employeeDepartment", joinColumns = {@JoinColumn(name = "empId")},
            inverseJoinColumns = {@JoinColumn(name = "depId")})
    private List<Department> departments = new ArrayList<>();

    public Employee(Long empId,
                    String empName,
                    Integer age,
                    String gender, int salary, String emailId,Boolean deleted) {
        this.empId = empId;
        this.empName = empName;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.emailId = emailId;
        this.deleted = deleted;
    }

}
