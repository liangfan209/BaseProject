package com.bquan.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.FileIOUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;

import java.io.File;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class EmptyActivity extends BaseActivity {
    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_empty;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }
    @Override
    protected void attach() {
        String path = "file:///android_asset/user_center/userConfig.json";
        File file = new File(path);
//        String contentStr = AppUtils.getAssetJson(this,"user_center/userConfig.json");
        byte[] bts = FileIOUtils.readFile2BytesByStream(file);
        System.out.println("====>"+ bts);
        //跳转到登录界面
        ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY).navigation();
        finish();
    }
}
