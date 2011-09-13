package com.googlecode.coss.common.utils.text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.googlecode.coss.common.utils.lang.StringUtils;

/**
 * <p>
 * DateFormat operation
 * </p>
 */
public class DateFormatUtils {

	/**
	 * @param value
	 *            Date String
	 * @param targetType
	 *            long or String
	 * @param formats
	 *            format String or String[]
	 * @return
	 */
	public static Date parse(String value, Class targetType, String... formats) {
		for (String format : formats) {
			try {
				long v = new SimpleDateFormat(format).parse(value).getTime();
				return (Date) targetType.getConstructor(long.class).newInstance(v);
			} catch (ParseException e) {
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			try {
				return (Date) targetType.getConstructor(String.class).newInstance(value);
			} catch (Exception e) {
			}
		}
		throw new IllegalArgumentException("cannot parse:" + value + " for date by formats:" + Arrays.asList(formats));
	}

	/**
	 * If is java.util.Date or java.sql.Date
	 * 
	 * @param targetType
	 * @return
	 */
	public static boolean isDateType(Class<?> targetType) {
		if (targetType == null)
			return false;
		return targetType == java.util.Date.class || targetType == java.sql.Timestamp.class
				|| targetType == java.sql.Date.class || targetType == java.sql.Time.class;
	}

	/**
	 * @param dateString
	 *            Date String
	 * @param dateFormat
	 *            Date Format String
	 * @return
	 */
	public static Date parse(String dateString, String dateFormat) {
		return parse(dateString, dateFormat, Date.class);
	}

	public static Date parse(String dateString, String dateFormat, Class targetResultType) {
		if (StringUtils.isEmpty(dateString))
			return null;
		DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			long time = df.parse(dateString).getTime();
			Date t = (Date) targetResultType.getConstructor(new Class[] { Long.TYPE }).newInstance(
					new Object[] { Long.valueOf(time) });
			return t;
		} catch (ParseException e) {
			String errorInfo = (new StringBuilder()).append("cannot use dateformat:").append(dateFormat)
					.append(" parse datestring:").append(dateString).toString();
			throw new IllegalArgumentException(errorInfo, e);
		} catch (Exception e) {
			throw new IllegalArgumentException((new StringBuilder()).append("error targetResultType:")
					.append(targetResultType.getName()).toString(), e);
		}
	}

	/**
	 * @param date
	 *            Date
	 * @param dateFormat
	 *            Date Format String
	 * @return
	 */
	public static String format(Date date, String dateFormat) {
		if (date == null) {
			return null;
		} else {
			DateFormat df = new SimpleDateFormat(dateFormat);
			return df.format(date);
		}
	}

	/** default DateTime format **/
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** default Date format **/
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	private static ThreadLocal<SimpleDateFormat> dateTimeThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		protected synchronized SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_TIME_FORMAT);
		}
	};

	private static ThreadLocal<SimpleDateFormat> dateThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		protected synchronized SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT);
		}
	};

	public static DateFormat getDefaultTimeFormat() {
		return dateTimeThreadLocal.get();
	}

	public static DateFormat getDefaultDateFormat() {
		return dateThreadLocal.get();
	}

	/**
	 * <p>
	 * Use appointing formatter to format input date, if date is null return
	 * string defaultNullValue
	 * </p>
	 * 
	 * @param formatter
	 *            the specify formatter
	 * @param date
	 *            the Date to format
	 * @param defaultNullValue
	 * @return
	 */
	public static String format(DateFormat formatter, Date date, String defaultNullValue) {
		if (date == null) {
			return defaultNullValue;
		} else {
			return formatter.format(date);
		}
	}

	/**
	 * <p>
	 * Use appointing formatter to format input date, if date is null return
	 * string "null"
	 * </p>
	 * 
	 * @param formatter
	 * @param date
	 * @return
	 */
	public static String format(DateFormat formatter, Date date) {
		return format(formatter, date, "null");
	}

	/**
	 * <p>
	 * Use default formatter to format input date
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static String defaultFormat(Date date) {
		return getDefaultTimeFormat().format(date);
	}

	/**
	 * <p>
	 * Parser string to Date, use appointed formatter
	 * </p>
	 * 
	 * @param formatter
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(DateFormat formatter, String str) throws ParseException {
		return formatter.parse(str);
	}

	/**
	 * <p>
	 * Parser string to Date, use appointed formatter, if conversation fails,
	 * return defaultDate
	 * </p>
	 * 
	 * @param formatter
	 * @param str
	 * @param defaultDate
	 * @return
	 */
	public static Date parse(DateFormat formatter, String str, Date defaultDate) {
		try {
			return parse(formatter, str);
		} catch (ParseException e) {
			return defaultDate;
		}
	}
}
