package com.clkj.app.welcome;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bumptech.glide.Glide;
import com.clkj.app.R;
import com.zhpan.bannerview.BaseViewHolder;

import androidx.annotation.NonNull;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class WelcomeHolder extends BaseViewHolder<Integer> {

    private Activity mActivity;
    public WelcomeHolder(@NonNull View itemView, Activity activity) {
        super(itemView);
        this.mActivity = activity;
    }


    public WelcomeHolder(@NonNull View itemView) {
        super(itemView);
    }
    @Override
    public void bindData(Integer data, int position, int pageSize) {
        ImageView imageView = findView(R.id.iv_welcome);
        TextView tvInto = findView(R.id.tv_into);
        if(position == 3){
            tvInto.setVisibility(View.VISIBLE);
        }else{
            tvInto.setVisibility(View.GONE);
        }
        tvInto.setOnClickListener(v->{
            CommSpUtils.saveWelecom(true);
            ARouter.getInstance().build(AppArouter.MAIN_ACTIVITY).navigation();
            new Handler().postDelayed(()->{  this.mActivity.finish();},1000);
        });
        Glide.with(imageView).load(data).into(imageView);
    }
}
