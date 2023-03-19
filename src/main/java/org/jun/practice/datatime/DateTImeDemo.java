package org.jun.practice.datatime;

import org.junit.Test;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

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
 *  Temporal Adjuster
 *
 *  DateTimeFormatter
 *      format
 *      parse
 *
 *  Zone[Date/Time/DateTime]
 */
public class DateTImeDemo {

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
}
