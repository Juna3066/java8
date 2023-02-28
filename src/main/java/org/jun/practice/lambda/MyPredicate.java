package org.jun.practice.lambda;

//函数式接口 限制接口只能有一个抽象方法 待实现
@FunctionalInterface
public interface MyPredicate<T> {
    boolean test(T t);
    //boolean test(T t,T t2);
}
