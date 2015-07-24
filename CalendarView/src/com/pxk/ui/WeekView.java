package com.pxk.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class WeekView extends View {

	float viewHeight;
	float viewWidth;
	private String weekArray[] = getResources().getStringArray(
			com.pxk.ui.R.array.week_array);
	float offsetWidth = 0;
	Paint paint;

	public WeekView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawWeek(canvas);
	}

	private void drawWeek(Canvas canvas) {
		float x = 25, y = 30;
		paint = new Paint();
		paint.setTextSize(30);
		paint.setColor(getResources()
				.getColor(android.R.color.holo_blue_bright));
		for (int i = 0; i < weekArray.length; i++) {
			canvas.drawText(weekArray[i], x, y, paint);
			x = x + offsetWidth;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.viewHeight = View.MeasureSpec.getSize(heightMeasureSpec);
		this.viewWidth = View.MeasureSpec.getSize(widthMeasureSpec);
		offsetWidth = viewWidth / weekArray.length;
	}
}