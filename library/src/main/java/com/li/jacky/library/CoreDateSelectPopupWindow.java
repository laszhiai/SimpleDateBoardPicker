package com.li.jacky.library;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by Jacky on 2017/9/12.
 * 底部日期选择弹窗
 */

public class CoreDateSelectPopupWindow extends PopupWindow {


    private TextView confirm;
    private TextView cancel;
    private DatePicker spinner;

    public CoreDateSelectPopupWindow(Context context, TitleCallback callback) {
        super(context);
        initView(context);
        initListener(callback);

    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.date_spinner, null, false);
        cancel = inflate.findViewById(R.id.date_spinner_cancel);
        confirm = inflate.findViewById(R.id.date_spinner_confirm);
        spinner = inflate.findViewById(R.id.date_board_picker);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setContentView(inflate);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    private void initListener(final TitleCallback callback) {
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onConfirm(spinner.getYear(), spinner.getMonth(), spinner.getDayOfMonth());
                dismiss();
            }
        });
    }

    public void setDate(int year, int month, int day) {
        spinner.updateDate(year, month, day);
    }

    public interface TitleCallback{

        void onConfirm(int year, int month, int day);
    }

}
