package com.pxk.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/**
	 * ���㵱ǰ�� �� �� �� �����ж�����
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
	 * ���㵱ǰ�·ݵ�һ��Ϊ���ڼ���ע���ý���� ������ Ϊ 1�� ����һ Ϊ 2 �����ڶ� Ϊ 3�� ������ Ϊ 4 ���Դ�����
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
	 * ���㵱ǰʱ��Ϊ���µ����ڼ�
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