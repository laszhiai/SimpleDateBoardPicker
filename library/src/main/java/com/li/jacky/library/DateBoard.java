package com.li.jacky.library;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.li.jacky.library.CoreDateSelectPopupWindow.TitleCallback;
import com.li.jacky.library.MonthView.Callback;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jacky on 2017/10/26.
 *
 */

public class DateBoard extends LinearLayout {

    private TextView title;
    private MonthView monthView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
    private Callback mCallback = new Callback() {
        @Override
        public void onDateSelected(int day) {
            int[] date = getDate((String) DateBoard.this.title.getText());
            DateBoard.this.title.setText(date[0]+ "年" + (date[1]+1) + "月" + day + "日");
        }
    };
    private View.OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int[] date = getDate((String) DateBoard.this.title.getText());
            datePopup(date[0], date[1], date[2]);
        }
    };

    public DateBoard(Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dateboard, this);
        title = inflate.findViewById(R.id.dateTitle);
        title.setText(dateFormat.format(System.currentTimeMillis()));
        title.setOnClickListener(mClickListener);

        monthView = inflate.findViewById(R.id.month_view);
        monthView.setCallback(mCallback);
        int[] date = getDate((String) title.getText());
        monthView.setDate(date[0], date[1]);
    }

    private void datePopup(int year, int month, int day) {
        setBackgroundAlpha(0.5f);
        CoreDateSelectPopupWindow coreDateSelectPopupWindow = new CoreDateSelectPopupWindow(getContext(), new TitleCallback() {
            @Override
            public void onConfirm(int year, int month, int day) {
                monthView.setDate(year, month, day);
                title.setText(year+"年" + (month + 1) +"月"+day+"日");
            }
        });
        coreDateSelectPopupWindow.setDate(year, month, day);
        coreDateSelectPopupWindow.showAtLocation(this, Gravity.BOTTOM, 0, 0);
        coreDateSelectPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });

    }

    public void setBackgroundAlpha(float alpha) {
        Window window = ((Activity) getContext()).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private int[] getDate(String str) {
        int[] date = new int[3];
        try {
            Date parse = dateFormat.parse(str);
            date[0] = parse.getYear() + 1900;
            date[1] = parse.getMonth();
            date[2] = parse.getDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
