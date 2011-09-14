/**
 * 
 */
package com.googlecode.coss.common.utils.lang.DateTime;

import java.util.Date;

/**
 * <p>
 * DateTime Operation
 * </p>
 * 
 */
public class TimeUnit {

	/** 1 Day **/
	public static final long DAY = 24 * 3600 * 1000;
	/** 1 HOUR **/
	public static final long HOUR = 3600 * 1000;
	/** 1 MINUTE **/
	public static final long MINUTE = 60 * 1000;
	/** 1 SECOND **/
	public static final long SECOND = 1000;

	/**
	 * <p>
	 * Add days to current Date, construct a new Date
	 * </p>
	 * 
	 * @param day
	 * @return new Date
	 */
	public static Date addDay(int day) {
		return new Date(System.currentTimeMillis() + DAY * day);
	}

	/**
	 * <p>
	 * Add days to input Date, construct a new Date
	 * </p>
	 * 
	 * @param day
	 * @return new Date
	 */
	public static Date addDay(Date date, int day) {
		return new Date(date.getTime() + DAY * day);
	}

	/**
	 * <p>
	 * Add hours to current Date, construct a new Date
	 * </p>
	 * 
	 * @param hour
	 * @return
	 */
	public static Date addHour(int hour) {
		return new Date(System.currentTimeMillis() + HOUR * hour);
	}

	/**
	 * <p>
	 * Add hours to input Date, construct a new Date
	 * </p>
	 * 
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		return new Date(date.getTime() + HOUR * hour);
	}

	/**
	 * <p>
	 * Add minutes to current Date, construct a new Date
	 * </p>
	 * 
	 * @param minute
	 * @return
	 */
	public static Date addMinute(int minute) {
		return new Date(System.currentTimeMillis() + MINUTE * minute);
	}

	/**
	 * <p>
	 * Add minutes to input Date, construct a new Date
	 * </p>
	 * 
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		return new Date(date.getTime() + MINUTE * minute);
	}

	/**
	 * <p>
	 * Add second to current Date, construct a new Date
	 * </p>
	 * 
	 * @param second
	 * @return
	 */
	public static Date addSecond(int second) {
		return new Date(System.currentTimeMillis() + SECOND * second);
	}

	/**
	 * <p>
	 * Add second to input Date, construct a new Date
	 * </p>
	 * 
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		return new Date(date.getTime() + SECOND * second);
	}
}
