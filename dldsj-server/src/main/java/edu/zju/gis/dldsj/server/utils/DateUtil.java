package edu.zju.gis.dldsj.prototype.utils;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {
    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateUtil() {
    }

    public static String format(Date date) {
        return FORMAT.format(date);
    }

    public static Date parse(String date) throws ParseException {
        return FORMAT.parse(date);
    }

    public static Date parse(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }

    /**
     * @return 当前时间
     */
    public static Date now() {
        return new Date();
    }

    public static int getHour(Date date) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 当天零点
     */
    public static Date beginOfDay(Date date) {
        Calendar cal = new Calendar.Builder().setInstant(date).setTimeOfDay(0, 0, 0, 0).build();
        return cal.getTime();
    }

    /**
     * @param date 起始时间
     * @param days 需要增减的天数
     * @return 天数增减后的时间
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    /**
     * @param date  起始时间
     * @param hours 需要增减的小时数
     * @return 天数增减后的时间
     */
    public static Date addHours(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);
        return cal.getTime();
    }

    public static Date addMonths(Date date, int amounts) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amounts);
        return cal.getTime();
    }

    /**
     * @param date 输入的时间
     * @return 输入时间的前一天晚上23：59：59
     */
    public static Date endOfYesterday(Date date) {
        Date begin = beginOfDay(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(begin);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }

    /**
     * @return 输入日期的当月1日凌晨
     */
    public static Date firstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * @return 输入日期的上月最后一天23：59：59
     */
    public static Date lastDayOfLastMonth(Date date) {
        return endOfYesterday(firstDayOfMonth(date));
    }

    /**
     * @return 当年一月一日零点
     */
    public static Date firstDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * @param date
     * @param amount 月份的增减
     * @return N月前（后）的1日零点
     */
    public static Date firstDayOfNMonthsBefore(Date date, int amount) {
        Date newDate = addMonths(date, amount);
        Calendar cal = new Calendar.Builder().setInstant(newDate).build();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1, 0, 0, 0);
        return cal.getTime();
    }

    /**
     * @param date
     * @param amount 月份的增减
     * @return N月前（后）末的23：59：59
     */
    public static Date lastDayOfNMonthsBefore(Date date, int amount) {
        return endOfYesterday(firstDayOfNMonthsBefore(date, amount + 1));
    }

    /**
     * 上年年末
     */
    public static Date lastDayOfLastYear(Date date) {
        Calendar cal = Calendar.getInstance();
        Date firstdayofyear = firstDayOfYear();
        cal.setTime(firstdayofyear);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date beginOfTomorrow(Date date) {
        return beginOfDay(addDays(date, 1));
    }

    public static String getLastDayOfMonth(Date date) {
        SimpleDateFormat simpleDateFormat = getDateParser("yyyy-MM-dd");
        String today = simpleDateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(today.split("-")[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(today.split("-")[1]) - 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        return simpleDateFormat.format(cal.getTime());
    }

    public static Date endDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return cal.getTime();
    }

    /**
     * N个月的同一时间
     */
    public static Date getDayOfMonthsBefore(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -month);
        return cal.getTime();
    }

    public static SimpleDateFormat getDateParser(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static SimpleDateFormat getDateParserUS(String pattern) {
        return new SimpleDateFormat(pattern, Locale.US);
    }

    public static SimpleDateFormat getGenenalParserUS() {
        return getDateParserUS("EEE MMM dd HH:mm:ss Z yyyy");
    }

    public static String getGenenalDateMillis(Date date) {
        return new SimpleDateFormat("yyMMddHHmmssSSS").format(date);
    }

    /**
     * @return 返回时间yyyyMMddHHmmss 14位形式
     */
    public static String curDateTimeStr14() {
        Date date = new Date();
        return getDateParser("yyyyMMddHHmmss").format(date);
    }

    /**
     * @return 返回时间yyyy-MM-dd HH:mm:ss
     */
    public static String curDateTimeStrDefault() {
        Date date = new Date();
        return getDateParser("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String format(String format, Date date) {
        return getDateParser(format).format(date);
    }

    /**
     * @return 返回时间yyyy-MM-dd 8位形式
     */
    public static String curDateToDay() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static Date endOfDate(String dateTime) {
        try {
            return DateUtil.endOfYesterday(DateUtil.addDays(getDateParser("yyyy-MM-dd").parse(dateTime), 1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getYesterday() {
        return addDays(new Date(), -1);
    }

    /**
     * 获得当前日期是星期几
     *
     * @return { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }
     */
    public static String curWeekDay() {
        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }

    public static float getByCompare(String preDatetime, String afterDatetime, TimeUnit unit) throws ParseException {
        Date preD = FORMAT.parse(preDatetime);
        Date afterD = FORMAT.parse(afterDatetime);
        return getByCompare(preD, afterD, unit);
    }

    public static float getByCompare(@NotNull Date preDate, @NotNull Date afterDate, TimeUnit unit) {
        long diff = afterDate.getTime() - preDate.getTime();
        return 1.0f * diff / unit.getMilliseconds();
    }

    public enum TimeUnit {
        MILLISECOND(1),
        SECOND(1000),
        MINUTE(1000 * 60),
        HOUR(1000 * 60 * 60),
        DAY(1000 * 60 * 60 * 24),
        WEEK(1000 * 60 * 60 * 24 * 7);
        int milliseconds;

        TimeUnit(int milliseconds) {
            this.milliseconds = milliseconds;
        }

        int getMilliseconds() {
            return milliseconds;
        }
    }
}
