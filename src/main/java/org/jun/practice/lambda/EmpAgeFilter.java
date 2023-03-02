package org.jun.practice.lambda;

import org.jun.practice.entity.Emp;

public class EmpAgeFilter implements MyPredicate<Emp>{
    @Override
    public boolean test(Emp emp) {
        return emp.getAge() >= 26;
    }
}
