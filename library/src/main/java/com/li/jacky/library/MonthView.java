package com.li.jacky.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.Calendar;

/**
 * Created by Jacky on 2017/10/26.
 *
 */

public class MonthView extends View {

    private Calendar c = Calendar.getInstance();
    private int[][] days;
    private Region[][] monthRegion = new Region[6][7];
    private Paint mPaint;
    private int mTextHeight;
    private float singleTextWidth;
    private float doubleTextWidth;
    private int mTouchX;
    private int mTouchY;
    private int mWeekCount;
    private int mSelectedDay = -1;
    private Callback mCallback;

    public MonthView(Context context) {
        super(context);
        init();
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setBackgroundColor(Color.LTGRAY);
        mPaint = new Paint();
        mPaint.setTextSize(36);
        singleTextWidth = mPaint.measureText("1");
        doubleTextWidth = mPaint.measureText("10");
        Rect rect = new Rect();
        mPaint.getTextBounds("1", 0, 1, rect);
        mTextHeight = rect.height();
    }

    public void setDate(int year, int month, int day) {
        mSelectedDay = day;
        setDate(year, month);
    }

    public void setDate(int year, int month) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 1);
        int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = c.get(Calendar.DAY_OF_WEEK);

        days = new int[6][7];
        int day = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && j >= firstDay - 1) {
                    days[0][j] = day;
                    day++;
                }  else if (i != 0 && day <= totalDays) {
                    days[i][j] = day;
                    day++;
                }
            }
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        float dayWidth = w / 7f;
        int dayHeight =  h / mWeekCount;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Region region = new Region();
                region.set((int)(j * dayWidth + 1), (i * dayHeight + 1), (int)((j + 1) * dayWidth ),
                    (int)dayWidth + (i * dayHeight));
                monthRegion[i][j] = region;
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (days[4][0]  == 0) {
            mWeekCount = 4;
        } else if (days[5][0] == 0) {
            mWeekCount = 5;
        }   else {
            mWeekCount = 6;
        }

        setMeasuredDimension(measureWidth, (int) (measureWidth * mWeekCount / 7F));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < days.length; i++) {
            for (int j = 0; j < days[i].length; j++) {
                drawDay(canvas, monthRegion[i][j], days[i][j]+"");
            }
        }
    }

    private void drawDay(Canvas canvas, Region region, String str) {
        Rect rect = region.getBounds();
        if (region.contains(mTouchX, mTouchY) || str.equals(mSelectedDay + "")) {
            mPaint.setColor(Color.parseColor("#0093ff"));
            mCallback.onDateSelected(Integer.valueOf(str));
            mTouchX = 0;
            mTouchY = 0;
        }  else {
            mPaint.setColor(Color.WHITE);
        }
        canvas.drawRect(rect, mPaint);
        if (str.equals("0")) {
            return;
        }
        mPaint.setColor(Color.BLACK);
        if (str.length() == 1) {
            canvas.drawText(str, rect.centerX()-singleTextWidth/2, rect.centerY() + mTextHeight / 2, mPaint);
        }else {
            canvas.drawText(str, rect.centerX()-doubleTextWidth/2, rect.centerY() + mTextHeight / 2, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                mTouchX = (int) event.getX();
                mTouchY = (int) event.getY();
                mSelectedDay = -1;
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public interface Callback {

        void onDateSelected(int day);

    }
}
