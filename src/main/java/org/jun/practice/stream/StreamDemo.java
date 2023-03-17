package org.jun.practice.stream;

import org.jun.practice.entity.Emp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 流(Stream)知识点：
 *
 * 概念：操作集合、数组等数据源 所生成的元素序列。
 *
 * 注意点：
 * 1.流自己不存储元素
 * 2.流不改变源对象,返回新的Stream
 * 3.流操作是延迟的,会等到需要结果的时候才执行。
 *
 * 三步骤：
 *      创建操作：
 *
 *      中间操作：
 *
 *      终止操作：
 *
 *
 */
public class StreamDemo {

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
     * 创建流
     * 1.Collection接口的stream创建流、parallelStream创建并行流
     * 2.Arrays.stream 通过数组，创建流(返回值不同的重载) 流、int流、long流、double流
     *
     * 3.Stream的静态方法创建流
     *      Stream.of  是用of传入一个参数或者可变参数,创建流
     *      Stream.iterator  使用迭代函数创,建无限流
     *      Stream.generator 使用迭代生成函数,创建无限流
     */
    @Test
    public void demo01() {
        //1
        List<Emp> empList2 = new ArrayList<>();
        empList2.addAll(empList);
        Stream<Emp> empStream = empList2.stream();
        Stream<Emp> empStream1 = empList2.parallelStream();

        //2
        Integer[] integers = new Integer[6];
        Stream<Integer> integerStream = Arrays.stream(integers);
        int[] ints = {1,2,3};
        IntStream intStream = Arrays.stream(ints);
        long[] longs = {1,2,3};
        LongStream longStream = Arrays.stream(longs);
        double[] doubles = {1,2,3};
        DoubleStream doubleStream = Arrays.stream(doubles);

        //3
        Stream<Integer> integerStream1 = Stream.of(1, 2, 3);
        Stream<Integer> iterate = Stream.iterate(0, x -> x + 3).limit(6);
        Stream<Double> generate = Stream.generate(Math::random).limit(6);
        generate.forEach(System.out::println);
    }

}
