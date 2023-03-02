package org.jun.practice.inlinefunction;

import org.jun.practice.entity.Emp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

/**
 * 2.内置函数知识点：
 */
public class InlineFunctionDemo {

    /**
     * 内置函数
     *
     * csfp
     */
    @Test
    public void demo01() {
        //baseFunction();
        //otherFunction();
        simplePractice();
    }

    /**
     * 筛选符合条件的字符串
     * 内置函数简单利息
     */
    private void simplePractice() {
        List<String> list = Arrays.asList("abc", "11", "12344", "66666666", "2");
        List<String> list2 = getList(list,s->s.length()>=3);
        System.out.println("list2 = " + list2);
    }

    private List<String> getList(List<String> list,Predicate<String> p) {
        List<String> resList = new ArrayList<>();
        for (String s : list) {
            if (p.test(s))
                resList.add(s);
        }
        return resList;
    }

    /**
     * todo 多熟悉就ok了
     */
    private static void otherFunction() {
        //参数两个泛型,返回空
        BiConsumer<Integer,Integer> bc = (a,b) -> System.out.println("a="+a+",b="+b);
        bc.accept(9,6);

        //对T的一元运算,返回T
        UnaryOperator<Integer> uop = obj->obj+obj;
        Integer apply = uop.apply(10);
        System.out.println("uop.apply(10) = " + apply);

        //对T的二元运算,返回T,参数列表两个T
        BinaryOperator<Double> bop = (a,b) -> a+b;
        Double apply1 = bop.apply(15.0, 23.0);
        System.out.println("bop.apply(15.0,23.0) = " + apply1);

        //传入T1,T2,返回T3
        BiFunction<Integer,Integer,Double> bf = (a,b)->a*a*1.0+b;
        Double apply2 = bf.apply(2, 5);
        System.out.println("bf.apply(3, 5) = " + apply2);

        /**
         * IntFunction 一个参数且是int,返回值是泛型
         * LongFunction 一个参数且是long,返回值是泛型
         * DoubleFunction 一个参数且是double,返回值是泛型
         */
        IntFunction<Emp> intFunction = num -> {
            Emp emp = new Emp();
            emp.setAge(num);
            return emp;
        };
        System.out.println("intFunction = " + intFunction.apply(22));

        /**
         * ToIntFunction 一个参数,是泛型，返回int
         * ToLongFunction 一个参数,是泛型，返回long
         * ToDoubleFunction 一个参数,是泛型，返回double
         */
        ToIntFunction<String> toIntFunction = obj -> Integer.parseInt(obj);
        int i = toIntFunction.applyAsInt("23");
        System.out.println("i = " + i);

        /**
         * ToIntBiFunction 两个参数,是泛型，返回int
         * ToLongBiFunction 两个参数,是泛型，返回long
         * ToDoubleBiFunction 两个参数,是泛型，返回Double
         */
        //Long.parseLong("234L") java.lang.NumberFormatException
        ToLongBiFunction<String,Integer> toLongBiFunction = (a,b) -> Long.parseLong("234") + b;
        long l = toLongBiFunction.applyAsLong("23", 3);
        System.out.println("l = " + l);
    }

    /**
     * 四大内置函数
     */
    private static void baseFunction() {
        //消费型
        Consumer<Integer> c = obj -> System.out.println(obj * obj);
        c.accept(6);

        //供给型
        //todo (int)优先级高于*
        Supplier<Integer> s = () -> (int) (Math.random() * 100);
        System.out.println("s.get() = " + s.get());

        //函数型
        Function<Integer, Double> f = obj -> obj * 6.0;
        System.out.println("f.apply(6) = " + f.apply(6));

        //断言型
        Predicate<Integer> p = obj -> obj > 26;
        System.out.println("p.test(27) = " + p.test(27));
    }
}
