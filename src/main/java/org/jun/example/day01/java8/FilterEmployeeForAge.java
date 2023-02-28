package org.jun.example.day01.java8;

import org.jun.example.day01.java8.entity.Employee;

public class FilterEmployeeForAge implements MyPredicate<Employee>{

	@Override
	public boolean test(Employee t) {
		return t.getAge() <= 35;
	}

}
