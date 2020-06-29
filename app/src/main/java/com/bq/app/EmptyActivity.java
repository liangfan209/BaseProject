package com.bq.app;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.FileIOUtils;
import com.bq.base.R;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;

import java.io.File;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class EmptyActivity extends BaseAcitivty {
    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_empty;
    }

    @Override
    protected BasePersenter createPersenter() {
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
        ARouter.getInstance().build(AppArouter.WALLET_MY_ACTIVITY).navigation();
        finish();
    }
}
