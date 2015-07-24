package com.pxk.ui;

import java.util.Calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarBox extends RelativeLayout {

	ViewPager calendarViewPager;
	TextView monthView;
	TextView yearView;
	TextView weekView;
	Context context;
	Calendar mCalendar;
	private int currentYear;
	private int currentMonth;
	private int currentDay;
	final static int INITIAL_YEAR = 1900;
	final static int INITIAL_MONTH = 1;
	final static int MAX_YEAR = 2100;
	final static int MAX_MONTH = 12;

	public CalendarBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		mCalendar = Calendar.getInstance();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		calendarViewPager = (ViewPager) findViewById(R.id.calendarViewPager);
		monthView = (TextView) findViewById(R.id.monthView);
		yearView = (TextView) findViewById(R.id.yearView);
		weekView = (TextView) findViewById(R.id.weekView);

		calendarViewPager.setAdapter(new CalendarAdapter(context));
		currentYear = mCalendar.get(Calendar.YEAR);
		currentMonth = mCalendar.get(Calendar.MONTH);
		currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
		calendarViewPager
				.setCurrentItem(((MAX_YEAR -INITIAL_YEAR) * MAX_MONTH) / 2);

	}

	/**
	 * ViewPager 适配器
	 * 
	 * @author pxk
	 * 
	 */
	class CalendarAdapter extends PagerAdapter {

		private int showYear = 0;
		private int showMonth = 0;
		private int showDay = 0;
		private int showPosition = 0;
		private CalendarView[] views = new CalendarView[5];

		public CalendarAdapter(Context context) {
			for (int i = 0; i < views.length; i++) {
				views[i] = new CalendarView(context);
			}
		}

		@Override
		public int getItemPosition(Object object) {
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public int getCount() {
			return (MAX_YEAR -INITIAL_YEAR) * MAX_MONTH;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			CalendarView cv = views[position % 5];
			if (showPosition == 0) {
				showYear = currentYear;
				showMonth = currentMonth;
				showDay = currentDay;
				cv.setTime(showYear, showMonth, showDay);
			} else if (showPosition < position) {
				showMonth = showMonth + (position -showPosition);
				if (showMonth >= 12) {
					showYear++;
					showMonth = 0;
				}
				cv.setTime(showYear, showMonth);
			} else if (showPosition > position) {
				showMonth = showMonth -(showPosition -position);
				if (showMonth <= -1) {
					showMonth = 11;
					showYear--;
				}
				cv.setTime(showYear, showMonth);
			}

			showPosition = position;

			container.addView(cv);
			return cv;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void setPrimaryItem(View container, int position, Object object) {
			CalendarView view = (CalendarView) object;
			monthView.setText(view.getMonth() + 1 + "月");
			yearView.setText(view.getYear() + "年");
			weekView.setText("第" + view.getWeek() + "周");
		}
	}

}