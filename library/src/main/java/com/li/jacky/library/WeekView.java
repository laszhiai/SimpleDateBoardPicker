package com.li.jacky.library;

import static android.view.Gravity.CENTER_HORIZONTAL;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jacky on 2017/10/26.
 *
 */

public class WeekView extends LinearLayout {

    private String[] week = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    public WeekView(Context context) {
        super(context);
        init();
    }

    public WeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        for (int i = 0; i < 7; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(week[i]);
            textView.setGravity(CENTER_HORIZONTAL);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            textView.setLayoutParams(layoutParams);
            addView(textView);
        }
    }

}
