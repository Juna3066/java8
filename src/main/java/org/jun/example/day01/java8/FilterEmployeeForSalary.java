package org.jun.example.day01.java8;

import org.jun.example.day01.java8.entity.Employee;

public class FilterEmployeeForSalary implements MyPredicate<Employee> {

	@Override
	public boolean test(Employee t) {
		return t.getSalary() >= 5000;
	}

}
