package org.jun.practice.stream;

import lombok.SneakyThrows;
import org.jun.practice.entity.Emp;
import org.jun.practice.enums.Status;
import org.junit.Test;

import java.util.*;
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
            new Emp(100L,"李一",27,2,124000, Status.OK),
            new Emp(101L,"刘二",23,1,15000, Status.OK),
            new Emp(102L,"张三",24,1,13000, Status.OK),
            new Emp(103L,"李四",26,1,30000,Status.OK),
            new Emp(103L,"李四",26,2,30000, Status.IS_DELETE),
            new Emp(104L,"王五",25,2,35000,Status.OK),
            new Emp(105L,"小刘",22,1,40000, Status.IS_DELETE)
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
     * 终止操作
     *      匹配查找
     *          anyMatch 返回true false
     *          allMatch 返回true false
     *          noneMatch 返回true false
     *
     *          findFirst 返回Optional<T>
     *          findAny 返回Optional<T>
     *
     *          count 返回int
     *          foreach 内部迭代
     *          max 返回Optional<T>
     *          min 返回Optional<T>
     *
     *      规约（map-reduce）
     *          reduce(初始值，累加器) 返回值
     *          reduce(累加器) 返回Optional<值>
     *
     *      收集
     *      * collect(收集器实例)
     *      *      参数是Collector接口的实例,
     *             将流汇总收集（比如收集到List Set Map），转化(收集)为其他形式。
     *
     *      Collectors提供了很多静态方法，快速创建常见收集器实例  常用用法：
     *
     *      1，收集到List Set 自己创建的指定结合
     *          collect(Collectors.toList()) 元素收集到 list
     *          collect(Collectors.toSet()) 元素收集到 set
     *          collect(Collectors.toCollection(ArrayList::new)) 元素收集到 创建的集合
     *     2.流中元素数量获取
     *          collect(Collectors.counting()) 返回long数量
     *     3.流中元xxx素属性计算平均值、求和、xxx属性汇总
     *          Int/Long/Double相似
     *          collect(Collectors.averagingDouble(Emp::getSalary))
     *          collect(Collectors.summingDouble(Emp::getSalary)) 返回Double类
     *          collect(Collectors.summarizingDouble(Emp::getSalary)) 返回值DoubleSummaryStatistics 汇总
     *     4.连接流的字符串
     *          collect(Collectors.joining(",","name:[","]"))  连接流中的字符串
     *     5.获取最大最小值 返回Option<T>
     *          collect(Collectors.minBy(Comparator.comparingDouble(Emp::getSalary)));
     *          max/minBy 类似
     *     6.收集器的规约 获取单个值
     *          collect(Collectors.reducing(0.0, Emp::getSalary, Double::sum))  流中的元素 收集结合为 单个值
     *     7.分组
     *          根据属性对流分组
     *          collect(Collectors.groupingBy(Emp::getStatus, Collectors.groupingBy(Emp::getSex)))
     *          根据true false最流分组
     *          collect(Collectors.partitioningBy(e -> e.getAge() >= 25))
     *
     *          将收集结果 进行不同类型的转化
     *          collect(Collectors.collectingAndThen(Collectors.joining(","), String::toUpperCase))
     */
    @SneakyThrows
    @Test
    public void demo3() {
        matchFind();
        reduce();
        collect();
    }

    /**
     *  collect(收集器实例)
     *      参数是Collector接口的实例,将流汇总收集（比如收集到List Set Map），转化为其他形式。
     *
     *  Collectors提供了很多静态方法，快速创建常见收集器实例
     */
    private void collect() {
        //元素收集到List
        List<String> list = empList.stream().map(Emp::getName).collect(Collectors.toList());
        System.out.println("list = " + list);
        //list.forEach(System.out::println);

        //元素收集到Set
        Set<String> set = empList.stream().map(Emp::getName).collect(Collectors.toSet());
        System.out.println("set = " + set);

        //元素收集到 创建的集合
        ArrayList<String> arrayList = empList.stream().map(Emp::getName).collect(Collectors.toCollection(ArrayList::new));
        System.out.println("arrayList = " + arrayList);
        HashSet<String> hashSet = empList.stream().map(Emp::getName).collect(Collectors.toCollection(HashSet::new));
        System.out.println("hashSet = " + hashSet);

        Long count = empList.stream().collect(Collectors.counting());
        System.out.println("counting = " + count);
        Double collect = empList.stream().collect(Collectors.summingDouble(Emp::getSalary));
        System.out.println("summingDouble = " + collect);
        Double averagingDouble = empList.stream().collect(Collectors.averagingDouble(Emp::getSalary));
        System.out.println("averagingDouble = " + averagingDouble);
        DoubleSummaryStatistics summarizingDouble = empList.stream().collect(Collectors.summarizingDouble(Emp::getSalary));
        System.out.println("summarizingDouble = " + summarizingDouble);
        //summarizingDouble.getMax();
        String joining = empList.stream().map(Emp::getName).collect(Collectors.joining(",","name:[","]"));
        System.out.println("joining = " + joining);

        //minby maxBy
        Optional<Emp> emp = empList.stream().collect(Collectors.minBy((e1, e2) -> (int) (e1.getSalary() - e2.getSalary())));
        System.out.println("emp = " + emp);
        Optional<Emp> emp2 = empList.stream().collect(Collectors.minBy(Comparator.comparingDouble(Emp::getSalary)));
        System.out.println("emp2 = " + emp2);

        //工资总和
        Double aDouble = empList.stream().collect(Collectors.reducing(0.0, Emp::getSalary, Double::sum));
        System.out.println("aDouble = " + aDouble);

        //todo 记住 根据某属性值对流分组，属性为K，结果为V 带理解源码
        Map<Status, List<Emp>> statusListMap = empList.stream().collect(Collectors.groupingBy(Emp::getStatus));
        System.out.println("statusListMap = " + statusListMap);

        Map<Status, Map<Integer, List<Emp>>> mapMap = empList.stream().collect(Collectors.groupingBy(Emp::getStatus, Collectors.groupingBy(Emp::getSex)));
        System.out.println("mapMap = " + mapMap);

        Map<Boolean, List<Emp>> booleanListMap = empList.stream().collect(Collectors.partitioningBy(e -> e.getAge() >= 25));
        System.out.println("booleanListMap = " + booleanListMap);

        //collectingAndThen 将收集结果 进行不同类型的转化
        List<String> stringList = Arrays.asList("aa", "bb", "cc");
        String collect1 = stringList.stream().collect(Collectors.collectingAndThen(Collectors.joining(","), String::toUpperCase));
        System.out.println("collect1 = " + collect1);

        String[] strings = stringList.stream().collect(Collectors.collectingAndThen(Collectors.toList(), obj -> obj.toArray(new String[0])));
        //System.out.println("strings = " + strings);
        for (String string : strings) {
            System.out.println("string = " + string);
        }
    }

    /**
     * 规约
     * map和reduce的连接 叫做 map-reduce模式
     * 因google用 该模式 进行网路搜索,而使该模式出名
     */
    private static void reduce() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Integer reduce = list.stream().reduce(0, Integer::sum);
        System.out.println("reduce = " + reduce);

        Optional<Integer> reduce1 = list.stream().reduce(Integer::sum);
        System.out.println("reduce1 = " + reduce1);
    }

    /**
     * 匹配查找
     * @throws InterruptedException
     */
    private void matchFind() throws InterruptedException {
        //匹配
        Stream<Emp> empStream = empList.parallelStream();
        boolean b = empStream.anyMatch(emp -> emp.getSalary() >= 30000);
        System.out.println("b = " + b);

        boolean b1 = empList.stream().allMatch(emp -> emp.getSalary() >= 30000);
        System.out.println("b1 = " + b1);

        boolean b2 = empList.stream().noneMatch(emp -> emp.getSalary() >= 30000);
        System.out.println("b2 = " + b2);

        //查找
        Optional<Emp> first = empList.stream().filter(emp -> emp.getSalary() >= 30000).findFirst();
        System.out.println("first = " + first);

        Thread t = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                Optional<Emp> any = empList.parallelStream().filter(emp -> emp.getSalary() >= 30000).findAny();
                System.out.println("any = " + any);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.start();
        //主函数中执行线程时，程序会一直运行直到线程执行完毕
        //此处非主函数 t.join()阻塞当前线程 等待线程执行完毕 再结束测试
        t.join();

        //count 个数
        long count = empList.stream().count();
        System.out.println("count = " + count);

        //forEach 内部迭代 (使用Collection需要外部迭代)
        empList.stream().forEach(System.out::println);

        //流的最小值 最大值
        Optional<Emp> min = empList.stream().min((e1, e2) -> (int) (e2.getSalary() - e1.getSalary()));
        System.out.println("min.get() = " + min.get());

        Optional<Emp> max = empList.stream().max((e1, e2) -> (int) (e2.getSalary() - e1.getSalary()));
        System.out.println("max.get() = " + max.get());
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
