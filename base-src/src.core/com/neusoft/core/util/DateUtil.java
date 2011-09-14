package com.neusoft.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.routines.DateValidator;

import com.neusoft.core.exception.BaseException;
import com.neusoft.core.exception.RegularMatchFailException;

/**
 * 
 * <b>Application name:</b>广发网银维护<br>
 * <b>Application describing:</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2010 东软 金融事业部版权所有。<br>
 * <b>Company:</b>Neusoft<br>
 * <b>Date:</b>2010-10-26<br>
 * 
 * @author HXJ
 * @version $Revision$
 */
public abstract class DateUtil {

	public final static int TYPE_YYYY = 0;

	public final static int TYPE_MM = 1;

	public final static int TYPE_DD = 2;

	public final static int TYPE_WEEK = 3;

	public static String DEF_DATE_FORMAT = "yyyy-MM-dd";

	public static Calendar calendar;

	public final static String KEY_START = "DATE_START";

	public final static String KEY_END = "DATE_END";

	static {
		calendar = GregorianCalendar.getInstance(Locale.CHINESE);
	}

	/**
	 * {日期转换为制定格式的字符串}
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String fromatDate(Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format == null ? DEF_DATE_FORMAT : format);
		return formatter.format(date == null ? new Date() : date);
	}

	/**
	 * 
	 * {字符串按照指定格式转换为Date}
	 * 
	 * @param date_str
	 *            待转换日期的字符串 notNull
	 * @param pattern
	 *            日期格式 默认为 ILocalEtc.DATE_FORMAT
	 * @return Date
	 */
	public static Date strToDate(String date_str, String pattern) throws BaseException {
		if (date_str.length() <= 0) {
			return null;
		}
		Date res = DateValidator.getInstance()
				.validate(date_str, pattern == null ? DEF_DATE_FORMAT : pattern);
		if (res == null) {
			throw new RegularMatchFailException("0000", null, new String[] { date_str,
					pattern == null ? DEF_DATE_FORMAT : pattern, "strToDate" });
		}
		return res;
	}

	/**
	 * {获取当天的是日期 yyyy-mm-dd 00000000}
	 * 
	 * @return
	 */
	public static Date getToday() throws BaseException {
		return strToDate(fromatDate(new Date(), null), null);
	}

	/**
	 * MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY,SUNDAY,
	 * 
	 * @param date
	 *            java.util.Date
	 * @param startDay
	 *            int
	 * @return Map<String, Date>
	 */
	public static Map<String, Date> start2end(Date date, int startDay) {
		Map<String, Date> map = new HashMap<String, Date>();
		calendar.setTime(date);
		int index = calendar.get(Calendar.DAY_OF_WEEK);
		Date std = null;
		Date end = null;
		if (index == startDay) {
			std = date;
			end = DateUtils.addDays(date, 6);
		} else if (index > startDay) {
			std = DateUtils.addDays(date, startDay - index);
			end = DateUtils.addDays(date, startDay - index + 6);
		} else if (index < startDay) {
			std = DateUtils.addDays(date, startDay - 7 - index);
			end = DateUtils.addDays(date, startDay - 1 - index);
		}
		map.put(KEY_START, std);
		map.put(KEY_END, end);
		return map;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static int dayOfWeek(Date date) {
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day != 1) {
			day = day - 1;
		} else {
			day = 7;
		}
		return day;
	}

	/**
	 * 获取当前时间的毫秒
	 * 
	 * @return
	 */
	public static long getTimeInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}
}
