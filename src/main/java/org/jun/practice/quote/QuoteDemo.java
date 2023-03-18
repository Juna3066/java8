package org.jun.practice.quote;

import org.jun.practice.entity.Emp;
import org.jun.practice.enums.Status;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.*;

/**
 * lambda是接口的实现
     * Interface x = lambda
     * 有点类似 web的 IService i = new ServiceImpl
 *
 * 引用
     * 1.方法引用
     * 什么时候用？
         * lambda功能体,如果已经有方法实现的情况。
         * （被引用的方法是函数式接口的实现，即他们的【参数列表】和【返回值类型保持一直】 再省略后形式 就基本是引用了）
     *
     * 语法
         * 对象::实例方法名
         * 类::实例方法名
             * 这种情况,一般参数列表的第一个参数，调用实例方法。
             * 例如：(x,y)->x.equals(y) emp->emp.show()
             * 简写String::equals Emp::show
         * 类::静态方法
     * 2.构造器应用
         * 语法：类名::new
             * 注意构造器参数函数式接口的参数列表,返回值类型保持一直
     * 3.数组引用
         * 类型[]::new
 */
public class QuoteDemo {
    /**
     * 方法引用
     * lambda函数体已经被实现,更加简写的形式,方法引用
     */
    @Test
    public void demo01() {
        //01对象名::实例方法名
        Emp emp = new Emp(100L, "刘军军", 27,1, 30000, Status.OK);
        //Supplier<String> supplier = () -> emp.getName();
        Supplier<String> supplier = emp::getName;
        System.out.println("name = " + supplier.get());

        //02类名::实例方法名  第一个参数调用实例方法,实例方法可以空参，也可接受其他参数
        //Consumer<Emp>consumer = e -> e.work();
        Consumer<Emp>consumer = Emp::work;
        consumer.accept(emp);

        //Function<Emp,String> function = e -> e.getName();
        Function<Emp,String> function = Emp::getName;
        String apply = function.apply(emp);
        System.out.println("apply = " + apply);

        //BiFunction<String,String,Boolean> biFunction = (x,y) -> x.equals(y);
        BiFunction<String,String,Boolean> biFunction = String::equals;
        Boolean apply1 = biFunction.apply("abc", "abc");
        System.out.println("apply1 = " + apply1);

        //03类名::静态方法
        //BiFunction<Double,Double,Double> biFunction1 = (a,b)-> Math.max(a,b);
        BiFunction<Double,Double,Double> biFunction1 = Math::max;
        Double apply2 = biFunction1.apply(5.0, 6.0);
        System.out.println("apply2 = " + apply2);

        //Comparator<Integer> comparator = (a,b)-> Integer.compare(a,b);
        Comparator<Integer> comparator = Integer::compare;
    }

    /**
     * 构造器引
     */
    @Test
    public void demo02() {
        //Supplier<Emp> supplier = () -> new Emp();
        Supplier<Emp> supplier = Emp::new;
        Emp emp = supplier.get();
        System.out.println("emp = " + emp);
        BiFunction<String,Double,Emp> biFunction = Emp::new;
        Emp emp2 = biFunction.apply("小白", 35000.0);
        System.out.println("emp2 = " + emp2);
    }

    /**
     * 数组引用
     */
    @Test
    public void demo() {
        //数组需要设置初始值 所以下一行会报错
        //Supplier<String[]> supplier = new String[];
        //Function<Integer,String[]> function = (len)-> new String[len];
        Function<Integer,String[]> function = String[]::new;
        String[] apply = function.apply(10);
        System.out.println("apply.length = " + apply.length);

        //Function<Integer,Emp[]> function2 = i->new Emp[i];
        Function<Integer,Emp[]> function2 = Emp[]::new;
        Emp[] apply1 = function2.apply(6);
        System.out.println("apply1.length = " + apply1.length);
    }
}
