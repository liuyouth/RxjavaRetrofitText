package com.iolll.liubo.retrofitrxjava.views;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.iolll.liubo.retrofitrxjava.R;
import com.iolll.liubo.retrofitrxjava.model.net.cancle.ApiCancleManager;

/**
 * Created by LiuBo on 2018/5/25.
 */
public class CustomProgressDialog extends Dialog {
    private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;
    private static Runnable runnable;
    private static Handler handler = new Handler();
    private static int p;

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }


    public static CustomProgressDialog createDialog(Context context, final boolean canCancle, final String desc) {
        p = 0;
        String text = "正在加载中";
        if (!TextUtils.isEmpty(desc)) {
            text = desc;
        }
        customProgressDialog = new CustomProgressDialog(context,
                R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.layout_loading);
        customProgressDialog.setCanceledOnTouchOutside(false); //若为true，则会dismiss掉Dialog

        ImageView iv_loading_logo = (ImageView) customProgressDialog.findViewById(R.id.iv_loading_logo);
        ImageView iv_loading_center = (ImageView) customProgressDialog.findViewById(R.id.iv_loading_center);
        final TextView tv_loading_desc = (TextView) customProgressDialog.findViewById(R.id.tv_loading_desc);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_loading_logo, "rotation", 0F, 360F);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setRepeatCount(1000);
        animator1.setDuration(2000).start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_loading_center, "rotation", 0F, -360F);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.setRepeatCount(1000);
        animator2.setDuration(2000).start();
        stopTextChange();
        final String finalText = text;
        runnable = new Runnable() {
            @Override
            public void run() {
                if (p % 3 == 0) {
                    tv_loading_desc.setText(finalText + ".");
                }
                if (p % 3 == 1) {
                    tv_loading_desc.setText(finalText + "..");
                }
                if (p % 3 == 2) {
                    tv_loading_desc.setText(finalText + "...");
                }
                p++;
                handler.postDelayed(runnable, 500);
            }
        };
        handler.post(runnable);
        customProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (canCancle) {
                        ApiCancleManager.getInstance().cancelAll();
                        return false;
                    }else {
                        return true;
                    }
                }
                return false;
                //return keyCode == KeyEvent.KEYCODE_BACK && !canCancle;
            }
        });
        Window dialogWindow = customProgressDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

//        lp.x = locationX; // 新位置X坐标
//        lp.y = locationY; // 新位置Y坐标
//        lp.width = width; // 宽度
//        lp.height = height; // 高度
//
//        LogFactory.i("dialog的Y位置=" + locationY);
//        LogFactory.i("dialog的高度=" + height);
//		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//		customProgressDialog.getWindow().getAttributes().width = WindowManager.LayoutParams.MATCH_PARENT;
//		customProgressDialog.getWindow().getAttributes().height = height;
//		customProgressDialog.getWindow().getAttributes().
        dialogWindow.setAttributes(lp);
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {

        if (customProgressDialog == null) {
            return;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        customProgressDialog = null;
        stopTextChange();
    }

    private static void stopTextChange() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    public CustomProgressDialog setTitile(String strTitle) {
        return customProgressDialog;
    }




}

