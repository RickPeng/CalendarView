package com.pxk.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/**
	 * 计算当前年 与 月 的 该月有多少天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthOfDays(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 计算当前月份第一天为星期几。注：该结果是 星期天 为 1， 星期一 为 2 ，星期二 为 3， 星期三 为 4 ，以此类推
	 * 
	 * @return
	 */
	public static int getWeekOfFirstDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 计算当前时间为该月的星期几
	 * 
	 * @return
	 */
	public static int getWeekOfDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int w = calendar.get(Calendar.DAY_OF_WEEK);
		if (w < 0) {
			w = 0;
		}
		return w;
	}
}