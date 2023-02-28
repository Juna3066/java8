package org.jun.practice.lambda;

public class EmpSalaryFilter implements MyPredicate<Emp>{
    @Override
    public boolean test(Emp emp) {
        return emp.getSalary() <= 25000;
    }
}
