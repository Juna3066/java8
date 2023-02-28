package org.jun.practice.lambda;

public class EmpAgeFilter implements MyPredicate<Emp>{
    @Override
    public boolean test(Emp emp) {
        return emp.getAge() >= 26;
    }
}
