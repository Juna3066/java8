package org.jun.practice.datatime;

import org.junit.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Set;

/***
 *
 *  新时间日期api
 *
 *  01. Local[Date/Time/DateTime]
 *      创建、加减、修改、获取、比较、是否闰年、Period对象
 *  02.Instance
 *      时间戳
 *      使用Unix元年 1970 1 1 00:00:00 所经历的毫秒值
 *  03. Period
 *      Duration
 *
 *  04. Temporal Adjuster
 *
 *  05.DateTimeFormatter
 *      format
 *      parse
 *
 *  06.Zone[Date/Time/DateTime]
 *      带时区的时间，时区ID:{区域}/{城市}
 *          of(id)
 *          getAvailableZoneId
 *
 *  07.日期转化
 *
 */
public class DateTImeDemo {


    /**
     *  老->新 都是 oldX.toNewX
     *
     *  新->老 调用工具类 静态方法
     *  java.time.Instant -> java.util.Date              Date.from(instance)
     *  java.time.Instant -> java.sql.Timestamp          Timestamp.from(instance)
     *  java.time.LocalDateTime 和 java.sql.Timestamp    Timestamp.valueOf(ldt)
     *  java.time.LocalDate -> java.sql.Date             Date.valueOf(ld)
     *  java.time.LocalTime -> java.sql.Time             Time.valueOf(ld)
     *
     * java.sql.Date java.util.Date区别？
     *      java.sql.Date extends java.util.Date,且不包含时间、只有日期。
     *      如果需要表示日期、时间，且不需要和数据库交互，则用java.util.Date
     *      需要和数据库交互，用java.sql.Date
     *
     */
    @Test
    public void demo08() {
        Date date = Date.from(Instant.now());
        java.sql.Timestamp timestamp = Timestamp.from(Instant.now());
        java.sql.Timestamp timestamp1 = Timestamp.valueOf(LocalDateTime.now());
        java.sql.Date date1 = java.sql.Date.valueOf(LocalDate.now());
        java.sql.Time time1 = Time.valueOf(LocalTime.now());


    }


    /**
     * 01. Local[Date/Time/DateTime]
     *      类的实例是不可变对象
     *      表示使用ISO-8601日历系统 的 日期、时间、日期和时间
     *      提供简单的日期或时间
     *          不包含当前的时间信息
     *          不包含时区相关的信息
     *
     *    Local[Date/Time/DateTime]
     *      创建
     *          now
     *          of
     *          from 补充
     *      加减
     *          [plus/minus][Days/Weeks/Months/Years]
     *      加减Duration/Period
     *           plus/minus 添加或减少一个
     *      修改
     *          with[Year/Month/DayOfMonth/DayOfYear]
     *          1-31 1-366
     *      获取
     *          get[Year/Month/MonthValue]
     *      比较
     *          isBefore isAfter
     *      是否是闰年
     *          isLeapYear
     *          
     *      获取两个日期间的Period（一段时间）对象
     *      until
     */
    @Test
    public void demo01() {
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.withDayOfMonth(1);
        Period until = now.until(localDate);
        System.out.println("until = " + until);
        System.out.println("until.getDays() = " + until.getDays());

    }

    /**
     * 02 instance
     *      时间戳 使用Unix元年所经历的 1970毫秒数
     *      Instant.now().toEpochMilli()  纪元毫秒数
     */
    @Test
    public void demo02() {
        Instant now = Instant.now();
        System.out.println("now = " + now);
        //时间戳
        System.out.println(now.toEpochMilli());
        System.out.println(now.getNano());

        /*int res = (int) (1679193191828.0 / 1000 / 60 / 60 / 24) / 366;
        System.out.println("res = " + res);//2023年 1970年*/

        //元年加5秒
        Instant instant = Instant.ofEpochSecond(5);
        System.out.println(instant);

        //todo 不可能完成掌握 对于我
        OffsetDateTime odt = now.atOffset(ZoneOffset.ofHours(8));
        System.out.println("odt = " + odt);
    }

    /**
     * Period 日期间隔 LocalDate
     * Duration 时间间隔 Instance Duration只有秒 纳秒
     */
    @Test
    public void demo03() {
        LocalDate ld = LocalDate.now();
        LocalDate ld2 = ld.withDayOfMonth(1);
        //1号到19号 18天
        Period between = Period.between(ld2, ld);
        System.out.println("between = " + between);

        Instant now = Instant.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Instant now1 = Instant.now();

        Duration between1 = Duration.between(now, now1);
        System.out.println("between1 = " + between1);

    }

    /**
     * TemporalAdjuster
     * 时间矫正器 工具类
     *      例子：下一个周六
     *          下一个工作日
     */
    @Test
    public void demo04() {
        //todo 不可能都掌握，结合场景例子 回忆知识点
        LocalDateTime now = LocalDateTime.now();
        //下一个周六
        LocalDateTime with = now.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        System.out.println("with = " + with);

        LocalDateTime res = now.with(temporal -> {
            //下一个工作日
            LocalDateTime ldt = (LocalDateTime) temporal;

            //enum
            DayOfWeek dayOfWeek = ldt.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)){
                return ldt.plusDays(3);
            }else if (dayOfWeek.equals(DayOfWeek.FRIDAY)){
                return ldt.plusDays(2);
            }else {
                return ldt.plusDays(1);
            }
        });

        System.out.println("res = " + res);
    }

    /**
     * 格式化 解析
     */
    @Test
    public void demo5() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("now = " + ldt);
        String format = dtf.format(ldt);
        System.out.println("format = " + format);

        LocalDateTime parse = ldt.parse(format, dtf);
        System.out.println("parse = " + parse);

        TemporalAccessor parse1 = dtf.parse(format);
        System.out.println("parse1 = " + parse1);

        LocalDateTime from = LocalDateTime.from(parse1);
        System.out.println("from = " + from);

    }


    /**
     * 带时区的
     *      日期 时间
     */
    @Test
    public void demo6() {
        //zoneId();
        //从指定时区的系统时钟中获取当前日期时间。
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("US/Alaska"));
        System.out.println("ldt = " + ldt);


        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("now = " + now);
        ZonedDateTime now1 = ZonedDateTime.now(ZoneId.of("US/Alaska"));
        System.out.println("now1 = " + now1);
    }

    /**
     * 时区
     */
    private static void zoneId() {
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        //System.out.println("zoneIds = " + zoneIds);
        /*Iterator<String> iterator = zoneIds.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }*/
        //外部循环
        //zoneIds.forEach(System.out::println);
        //内部循环
        zoneIds.stream().forEach(System.out::println);
    }
}
