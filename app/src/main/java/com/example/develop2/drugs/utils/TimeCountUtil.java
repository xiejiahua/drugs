package com.example.develop2.drugs.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public class TimeCountUtil extends CountDownTimer {
    private Button mButton;

    public TimeCountUtil(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        mButton = btn;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mButton.setBackgroundColor(Color.parseColor("#B6B6D8"));
        mButton.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        mButton.setEnabled(false);
    }

    @Override
    public void onFinish() {
        mButton.setEnabled(true);
        mButton.setText("重新获取验证码");
        mButton.setBackgroundColor(Color.parseColor("#12b5f5"));

    }
}  

