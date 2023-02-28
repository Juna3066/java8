package org.jun.practice.lambda;

import org.junit.Test;

import java.util.*;

/**
 * 1.lambda知识点：
 *
 * 为什么使用lambda
 *      精简、写更少的代码、优雅
 *
 *  lambda是接口实现、匿名内部类简写
 *  语法：
 *      参数列表 操作符-> lambda体(接口功能实现内容)
 *
 *  省略
 *      左右为一就能省略
 *      左()右省{return}
 *
 *      参数列表的类型 因为java编译器 能通过上下文推断得出 所以可省略
 *
 *  lambda需要 函数式接口的支持（只有一个抽象方法的接口）
 *
 *  @FunctionalInterface
 *  1.接口上使用该注解、说明是函数式接口。如果接口有多个抽象方法,会报错
 *  2.生成的doc文档也有标注。
 *
 */
public class LambdaDemo {

    List<Emp> empList = Arrays.asList(
            new Emp(100L,"李一",27,24000),
            new Emp(100L,"刘二",23,15000),
            new Emp(100L,"张三",24,13000),
            new Emp(100L,"李四",26,30000),
            new Emp(100L,"王五",30,35000),
            new Emp(100L,"小刘",35,40000),
            new Emp(100L,"田七",25,21500)

    );

    /**
     * 为什么使用lambda？
     * 写更少的代码
     */
    @Test
    public void demo01() {
        //treeSetDemo();
        //empDemo();
        simpleDemo();
    }

    /**
     *  lambda是接口实现、匿名内部类简写
     *  语法：
     *      参数列表 操作符-> lambda体(接口功能实现内容)
     *
     *  省略
     *      左右为一就能省略
     *      左()右省{return}
     *
     *      参数列表的类型 因为java编译器 能通过上下文推断得出 所以可省略
     */
    private void simpleDemo() {
        Runnable r = () -> System.out.println("hello lambda");
        r.run();
        //todo 线程知识复习
        Thread t = new Thread(r);
        t.run();

        int res = calculate(6,num -> num*num);
        System.out.println("res = " + res);
    }

    private int calculate(int i, CalStrategy calStrategy) {
        return calStrategy.cal(i);
    }


    /**
     * 员工过滤案例
     * 写更少的代码
     */
    private void empDemo() {
        System.out.println("原始================");
        List<Emp> goalEmpList = new ArrayList<>();
        for (Emp emp : empList) {
            if (emp.getAge()>=26  && emp.getSalary() <=25000){
                goalEmpList.add(emp);
            }
        }
        for (Emp emp : goalEmpList) {
            System.out.println(emp);
        }
        System.out.println("策略================");
        //策略者
        goalEmpList.clear();
        goalEmpList = getEmpListFromFilter(empList,new EmpAgeFilter());
        goalEmpList = getEmpListFromFilter(goalEmpList,new EmpSalaryFilter());
        for (Emp emp : goalEmpList) {
            System.out.println(emp);
        }
        System.out.println("匿名-lambda================");
        goalEmpList.clear();
        //不能每次实现一个接口就加一个类吧、下面使用匿名内部类
        goalEmpList = getEmpListFromFilter(empList, new MyPredicate() {
            @Override
            public boolean test(Object o) {
                return ((Emp) o).getSalary() <= 25000;
            }
        });
        //匿名内部类也不优雅、lambda有点就此体现
        goalEmpList = getEmpListFromFilter(goalEmpList,e->((Emp)e).getAge()>=26);
        for (Emp emp : goalEmpList) {
            System.out.println(emp);
        }

        //使用流
        System.out.println("流================");
        empList.stream().filter(o -> o.getAge()>=26 && o.getSalary() <= 25000).forEach(System.out::println);
    }

    private List<Emp> getEmpListFromFilter(List<Emp> empList, MyPredicate myPredicate) {
        List<Emp> emps = new ArrayList<>();
        for (Emp emp : empList) {
            if (myPredicate.test(emp)){
                emps.add(emp);
            }
        }
        return emps;
    }


    /**
     * TreeSet例子
     * 从匿名内部类，到lambda，到引用，代码越来越精简
     *
     * lambda是接口实现（匿名内部类简写）
     */
    private static void treeSetDemo() {

        //匿名内部类
        Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //return o1.length() - o2.length();
                return Integer.compare(o1.length(),o2.length());
            }
        };
        TreeSet<String> ts = new TreeSet<>(c);
        //匿名内部类
        TreeSet<String> ts2 = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //return o1.length() - o2.length();
                return Integer.compare(o1.length(),o2.length());
            }
        });
        //lambda
        TreeSet<String> ts3 = new TreeSet<>((o1,o2)->Integer.compare(o1.length(),o2.length()));
        //引用 todo 什么引用
        TreeSet<String> ts4 = new TreeSet<>(Comparator.comparingInt(String::length));
    }




}
