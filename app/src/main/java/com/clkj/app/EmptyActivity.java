package com.clkj.app;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;

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
//        String path = "file:///android_asset/user_center/userConfig.json";
//        File file = new File(path);
////        String contentStr = AppUtils.getAssetJson(this,"user_center/userConfig.json");
//        byte[] bts = FileIOUtils.readFile2BytesByStream(file);
//        System.out.println("====>"+ bts);
//        //跳转到登录界面
//        ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY).navigation();


//        ARouter.getInstance().build(AppArouter.ORDER_WELCOME_ACTIVITY).navigation();
        finish();
    }
}
