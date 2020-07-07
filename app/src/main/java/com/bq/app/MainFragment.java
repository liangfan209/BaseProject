package com.bq.app;

import android.view.View;
import android.widget.Button;

import com.bq.base.R;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.SkinCompatManager;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/1/001
 * 版权：
 */
public class MainFragment extends BaseFragment {


    @BindView(R.id.bt_default)
    Button mBtDefault;
    @BindView(R.id.bt_red)
    Button mBtRed;

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void attach() {

    }

    @OnClick({R.id.bt_default, R.id.bt_red})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_default:
                SkinCompatManager.getInstance().restoreDefaultTheme();
                break;
            case R.id.bt_red:
                SkinCompatManager.getInstance().loadSkin("appskin-debug.apk", null, SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
                break;
        }
    }
}
