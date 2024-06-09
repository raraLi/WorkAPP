package com.example.workapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1000; // 3秒延迟时间
    private static final long COUNTDOWN_INTERVAL = 100; // 计时器间隔时间
    private static final long COUNTDOWN_TOTAL_TIME = 1000; // 总倒计时时间

    private Handler mHandler;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化Handler
        mHandler = new Handler();

        // 初始化CountDownTimer，并开始倒计时
        mCountDownTimer = new CountDownTimer(COUNTDOWN_TOTAL_TIME, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 倒计时中执行的操作，这里可以更新UI显示剩余时间
            }

            @Override
            public void onFinish() {
                // 倒计时结束时执行的操作
                redirectToHome();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在Activity销毁时取消倒计时
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    private void redirectToHome() {
        // 使用Handler延迟启动HomeActivity
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
