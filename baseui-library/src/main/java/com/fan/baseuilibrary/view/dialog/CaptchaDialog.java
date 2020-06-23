package com.fan.baseuilibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bq.utilslib.DensityUtils;
import com.fan.baseuilibrary.R;
import com.fan.baseuilibrary.view.captcha.Captcha;


/**
 * Created by DELL on 2017/6/18.
 */

public class CaptchaDialog {
    private int resLayout;
    private Context context;
    private Dialog dialog;
    private Display display;
    private Captcha mCaptcha;

    public CaptchaDialog(Context context, int resLayout) {
        this.context = context;
        this.resLayout = resLayout;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        buildDialog();
    }

    private void buildDialog() {

        View view = LayoutInflater.from(context).inflate(resLayout, null);
        mCaptcha = view.findViewById(R.id.captCha);
        dialog = new Dialog(context, R.style.custom_dialogTheme);
        dialog.setContentView(view);

        view.findViewById(R.id.btn_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dissmiss();
            }
        });

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setBackgroundDrawable(new ColorDrawable());
        int d = DensityUtils.getScreenW();
        lp.height = (int) (d*0.9);
        lp.width = (int) (DensityUtils.getScreenW() * 0.85);
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    public Captcha getCaptCha(){
        return mCaptcha;
    }

    public void dissmiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void refreshImg(){
        int[] dbs = {R.mipmap.hdyzm1,R.mipmap.hdyzm2,R.mipmap.hdyzm3};
        int i = (int)(Math.random()*3+1);
        if(i == 3){
            i = 2;
        }else if(i== 1){
            i = 0;
        }
        mCaptcha.setBitmap(dbs[i]);
    }

}
