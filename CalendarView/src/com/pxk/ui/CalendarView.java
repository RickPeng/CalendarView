package com.pxk.ui;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pxk.util.CalendarUtil;

public class CalendarView extends View {

	// 横线的数量
	private int horizontalLineCount = 6;

	// 竖线的数量
	private int verticalLineCount = 7;

	private float viewWidth;

	private float viewHeight;

	// 当前年份
	private int year = 2015;

	// 当前月份
	private int month = 7;

	// 当前周几
	private String week;

	// 当前星期几
	private int week_of_day;

	// 当前月份的天数
	private int day;

	// 当前月份的第一天为星期几
	private int month_of_firstDay;

	// 当前月份有多少天
	public int days_of_month;

	private int severalBlank = 0;

	// 计算横线的偏移量
	float offseHeight;

	// 计算竖线的偏移量
	float offsetWidth;

	private String weekArray[] = getResources().getStringArray(
			com.pxk.ui.R.array.week_array);

	public CalendarView(Context context) {
		super(context);
	}

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// drawForm(canvas, horizontalLineCount, verticalLineCount);
		// drawWeekText(canvas);
		drawCalendar(canvas);
	}

	/**
	 * 设置时间
	 * 
	 * @param year
	 * @param month
	 */
	public void setTime(int year, int month) {
		this.year = year;
		this.month = month;
		month_of_firstDay = CalendarUtil.getWeekOfFirstDay(year, month);
		days_of_month = CalendarUtil.getMonthOfDays(year, month);
		week_of_day = month_of_firstDay;
	}

	/**
	 * 设置时间包括 天
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	public void setTime(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		month_of_firstDay = CalendarUtil.getWeekOfFirstDay(year, month);
		days_of_month = CalendarUtil.getMonthOfDays(year, month);
		week_of_day = month_of_firstDay;
		week = weekArray[CalendarUtil.getWeekOfDate() - 1];
	}

	/**
	 * 表格绘制
	 * 
	 * @param canvas
	 * @param horizontalLineCount
	 * @param verticalLineCount
	 */
	private void drawForm(Canvas canvas, int horizontalLineCount,
			int verticalLineCount) {
		// Paint paint = new Paint();
		paint.setColor(getResources().getColor(android.R.color.black));
		// 绘制横线
		float[] horizontallineXY = { 5, 50, viewWidth - 10, 50 };
		for (int i = 0; i < horizontalLineCount; i++) {
			canvas.drawLines(horizontallineXY, paint);
			horizontallineXY[1] = horizontallineXY[1] + offseHeight;
			horizontallineXY[3] = horizontallineXY[3] + offseHeight;
		}
		// 绘制竖线
		float[] verticalLineXY = { 5, 50, 5, horizontallineXY[3] - offseHeight };
		for (int i = 0; i < verticalLineCount; i++) {
			canvas.drawLines(verticalLineXY, paint);
			verticalLineXY[0] = verticalLineXY[0] + offsetWidth;
			verticalLineXY[2] = verticalLineXY[2] + offsetWidth;
		}
	}

	Paint paint = new Paint();

	/**
	 * 绘制当前日期时间
	 * 
	 * @param canvas
	 */
	private void drawCalendar(Canvas canvas) {
		paint.setTextSize(50);
		float x = 25, y = 50;
		week_of_day = month_of_firstDay;
		x = x + (week_of_day - 1) * offsetWidth;
		int weekOffset = week_of_day;
		int day = 1, week = 7;
		// 根据当月有多少天进行循环
		while (day <= days_of_month) {
			while (week_of_day <= week && day <= days_of_month) {
				if (severalBlank == 0) {
					if (day == this.day) {
						paint.setColor(getResources().getColor(
								com.pxk.ui.R.color.orange));
						canvas.drawCircle(x + 30, y - 4, 40, paint);
						paint.setColor(getResources().getColor(
								android.R.color.white));
						canvas.drawText(day + "", x, y, paint);
						paint.setColor(getResources().getColor(
								android.R.color.black));
					} else {
						canvas.drawText(day + "", x, y, paint);
					}
				} else {
					if (severalBlank == day + weekOffset) {
						paint.setColor(getResources().getColor(
								com.pxk.ui.R.color.orange));
						canvas.drawCircle(x + 30, y - 4, 40, paint);
						paint.setColor(getResources().getColor(
								android.R.color.black));
					}
					if (day == this.day && severalBlank == day + weekOffset) {
						paint.setColor(getResources().getColor(
								android.R.color.white));
						canvas.drawText(day + "", x, y, paint);
						paint.setColor(getResources().getColor(
								android.R.color.black));
					} else if (day == this.day) {
						paint.setColor(getResources().getColor(
								com.pxk.ui.R.color.orange));
						canvas.drawText(day + "", x, y, paint);
						paint.setColor(getResources().getColor(
								android.R.color.black));
					} else {
						canvas.drawText(day + "", x, y, paint);
					}
				}
				week_of_day++;
				x = x + offsetWidth;
				day++;
			}
			week_of_day = 1;
			x = 25;
			y = y + offseHeight;
		}
	}

	/**
	 * 设置当前时间 year , month , day
	 */
	public void setTheCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 显示上个月份
	 */
	public void showLastMonth() {
		if (month == 1) {
			--year;
			month = 12;
		} else {
			--month;
		}
		days_of_month = CalendarUtil.getMonthOfDays(year, month);
		month_of_firstDay = CalendarUtil.getWeekOfFirstDay(year, month);
		invalidate();

	}

	/**
	 * 显示下个月份
	 */
	public void showNextMonth() {
		if (month == 12) {
			year = year + 1;
			month = 1;
		} else {
			month = month + 1;
		}
		days_of_month = CalendarUtil.getMonthOfDays(year, month);
		month_of_firstDay = CalendarUtil.getWeekOfFirstDay(year, month);
		invalidate();
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public String getWeek() {
		return week;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			float x = ev.getX();
			float y = ev.getY();

			// 因为该日历共用35个空格，分别计算出横第几个hz，竖第几个 vt ,用于计算出点击了那个一个空格
			int hz = (int) ((x - 5) / offsetWidth);
			int vt = (int) ((y) / offseHeight) + 1;
			week = weekArray[hz];
			severalBlank = (vt - 1) * 7 + hz + 2;

			invalidate();
			break;

		// 后期还将为这个添加滑动
		case MotionEvent.ACTION_UP:
			break;
		}

		return super.onTouchEvent(ev);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.viewHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		this.viewWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		offseHeight = viewHeight / horizontalLineCount;
		offsetWidth = viewWidth / verticalLineCount;
		setMeasuredDimension((int) viewWidth, (int) viewHeight);
	}

}