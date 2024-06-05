package com.example.workapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.workapp.R;

public class BeiZhuDialog extends Dialog implements View.OnClickListener {
    EditText editText;
    Button cancelBtu, ensureBtu;
    OnEnsureListener onEnsureListener;

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public BeiZhuDialog(@Nullable Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.dialog_beizhu);
        editText = findViewById(R.id.dialog_beizhu_et);
        cancelBtu = findViewById(R.id.dialog_beizhu_btn_cancel);
        ensureBtu = findViewById(R.id.dialog_beizhu_btn_ensure);
        cancelBtu.setOnClickListener(this);
        ensureBtu.setOnClickListener(this);


    }

    public interface OnEnsureListener {
        public void onEnsure();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.dialog_beizhu_btn_cancel) {
            cancel();
        } else if (viewId == R.id.dialog_beizhu_btn_ensure) {
            if (onEnsureListener != null) {
                onEnsureListener.onEnsure();
            }
        }
    }

    public String getEditText() {

        return editText.getText().toString().trim();
    }

    /* 设置Dialog的尺寸和屏幕尺寸一致*/
    public void setDialogSize() {
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int) (d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        handler.sendEmptyMessageDelayed(1,100);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}
