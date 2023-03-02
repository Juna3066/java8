package org.jun.practice.lambda;

import org.jun.practice.entity.Emp;

//接口带不带泛型都可、根据实际情况选择
@FunctionalInterface
public interface MyPredicate2 {
    boolean test(Emp t);
}
