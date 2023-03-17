package org.jun.practice.stream;

import org.jun.practice.entity.Emp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.*;

/**
 * 流(Stream)知识点：
 *
 * 概念：操作集合、数组等数据源 所生成的元素序列。
 *
 * 注意点：
 * 1.流自己不存储元素
 * 2.流不改变源对象,返回新的Stream
 * 3.流操作是延迟的,会等到需要结果的时候才执行。(惰性求值,遇到终止操作，所有操作才一次全部执行)
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
            new Emp(101L,"刘二",23,15000),
            new Emp(102L,"张三",24,13000),
            new Emp(103L,"李四",26,30000),
            new Emp(103L,"李四",26,30000),
            new Emp(104L,"王五",25,35000),
            new Emp(105L,"小刘",22,40000)
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

    /**
     * 中间操作
     *      1.筛选与切片（筛选 去重 截断 跳过）
     *          Stream的filter接口
     *          Stream的distinct接口（通过所生成元素的hashCode和equals方法去除重复元素）
     *          Stream的limit接口
     *          Stream的skip接口
     *
     *      2.映射
     *          map
     *          mapToInt/Long/Double
     *          flatMap
     *
     *      3.排序
     *          自然排序 生成新流
     *          按比较器排序 生成新流
     */
    @Test
    public void demo2() {
        //middleOpt01();
        //middleOpt02();
        middleOpt03();

    }

    /**
     * 中间操作03 sort
     */
    private void middleOpt03() {
        //sort() 自然排序 生成新流
        empList.stream().map(Emp::getName).limit(3).sorted().forEach(System.out::println);
        System.out.println();
        empList.stream().map(Emp::getId).limit(3).sorted().forEach(System.out::println);
        System.out.println();

        //按比较器排序 生成新流
        empList.stream().sorted(
                (e1, e2) -> (int) (e2.getSalary() - e1.getSalary())
        ).forEach(System.out::println);
    }

    /**
     * 中间操作02 map
     */
    private void middleOpt02() {
        Stream<Emp> empStream = empList.stream();
        Stream<String> stringStream = empStream.map(Emp::getName).distinct();
        stringStream.forEach(System.out::println);
        System.out.println();

        List<String> list = Arrays.asList("aa", "bb", "cc");
        list.stream().map(String::toUpperCase).forEach(System.out::println);
        System.out.println();

        Stream<String> stream = list.stream();
        //stream.flatMap(s -> Stream.of(s,1)).forEach(System.out::println);
        Stream<Character> characterStream = stream.flatMap(StreamDemo::getCharacterStream);
        characterStream.forEach(System.out::println);
        System.out.println();

        Stream<Stream<Character>> streamStream = list.stream().map(StreamDemo::getCharacterStream);
        streamStream.forEach(item->{
            item.forEach(System.out::println);
        });
        System.out.println();

        // Int/Long/DoubleStream
        DoubleStream doubleStream = empList.stream().mapToDouble(Emp::getSalary);
        doubleStream.forEach(System.out::println);
    }

    private static Stream<Character> getCharacterStream(String s) {
        List<Character> characterList = new ArrayList<>();
        char[] chars = s.toCharArray();
        for (char ch : chars) {
            characterList.add(ch);
        }
        return characterList.stream();
    }


    /**
     * 筛选与切片
     */
    private void middleOpt01() {
        //demoFilter();

        //todo 关于移除Emp @EqualsAndHashCode后 此处仍能成功过滤 李一的原因 类的成员？把李一当成一个对象引用了？
        empList.stream().distinct().forEach(System.out::println);
        System.out.println();

        //limit skip 效果互补
        empList.stream().filter(emp->{
            //test打印几次？ 3
            System.out.println("test");
            return emp.getAge()< 25;
        }).limit(2).forEach(System.out::println);
        System.out.println();

        empList.stream().filter(emp -> emp.getAge() < 25).skip(2).forEach(System.out::println);
    }

    private void demoFilter() {
        Stream<Emp> empStream = empList.stream();
        Stream<Emp> empStream1 = empStream.filter(emp -> {
            //惰性求值
            System.out.println("中间操作");
            //return emp.getAge() >=25 && emp.getAge() <= 30;
            return emp.getAge() < 25 ;
        });

        //内部迭代：StreamAPI 内部完成迭代
        empStream1.forEach(System.out::println);
        //empStream empStream1 都关闭了  执行结束操作,相关的流均会关闭，无法再此使用

        //外部迭代
        Iterator<Emp> iterator = empList.iterator();
        while (iterator.hasNext()){
            System.out.println("iterator.next() = " + iterator.next());
        }
    }


}
