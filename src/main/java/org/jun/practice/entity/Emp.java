package org.jun.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Emp {
    private long id;
    private String name;
    private int age;
    private double salary;

    public Emp(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public void work() {
        System.out.println("工作中...");
        System.out.println("挣钱中...");
        System.out.println("学习中...");
    }
}
