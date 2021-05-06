package com.example.demo.Java8Features.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {

    private String name;
    private int age;
    private BigDecimal salary;
}