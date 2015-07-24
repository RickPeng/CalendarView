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

	// ���ߵ�����
	private int horizontalLineCount = 6;

	// ���ߵ�����
	private int verticalLineCount = 7;

	private float viewWidth;

	private float viewHeight;

	// ��ǰ���
	private int year = 2015;

	// ��ǰ�·�
	private int month = 7;

	// ��ǰ�ܼ�
	private String week;

	// ��ǰ���ڼ�
	private int week_of_day;

	// ��ǰ�·ݵ�����
	private int day;

	// ��ǰ�·ݵĵ�һ��Ϊ���ڼ�
	private int month_of_firstDay;

	// ��ǰ�·��ж�����
	public int days_of_month;

	private int severalBlank = 0;

	// ������ߵ�ƫ����
	float offseHeight;

	// �������ߵ�ƫ����
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
	 * ����ʱ��
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
	 * ����ʱ����� ��
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
	 * ������
	 * 
	 * @param canvas
	 * @param horizontalLineCount
	 * @param verticalLineCount
	 */
	private void drawForm(Canvas canvas, int horizontalLineCount,
			int verticalLineCount) {
		// Paint paint = new Paint();
		paint.setColor(getResources().getColor(android.R.color.black));
		// ���ƺ���
		float[] horizontallineXY = { 5, 50, viewWidth - 10, 50 };
		for (int i = 0; i < horizontalLineCount; i++) {
			canvas.drawLines(horizontallineXY, paint);
			horizontallineXY[1] = horizontallineXY[1] + offseHeight;
			horizontallineXY[3] = horizontallineXY[3] + offseHeight;
		}
		// ��������
		float[] verticalLineXY = { 5, 50, 5, horizontallineXY[3] - offseHeight };
		for (int i = 0; i < verticalLineCount; i++) {
			canvas.drawLines(verticalLineXY, paint);
			verticalLineXY[0] = verticalLineXY[0] + offsetWidth;
			verticalLineXY[2] = verticalLineXY[2] + offsetWidth;
		}
	}

	Paint paint = new Paint();

	/**
	 * ���Ƶ�ǰ����ʱ��
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
		// ���ݵ����ж��������ѭ��
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
	 * ���õ�ǰʱ�� year , month , day
	 */
	public void setTheCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * ��ʾ�ϸ��·�
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
	 * ��ʾ�¸��·�
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

			// ��Ϊ����������35���ո񣬷ֱ�������ڼ���hz�����ڼ��� vt ,���ڼ����������Ǹ�һ���ո�
			int hz = (int) ((x - 5) / offsetWidth);
			int vt = (int) ((y) / offseHeight) + 1;
			week = weekArray[hz];
			severalBlank = (vt - 1) * 7 + hz + 2;

			invalidate();
			break;

		// ���ڻ���Ϊ�����ӻ���
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