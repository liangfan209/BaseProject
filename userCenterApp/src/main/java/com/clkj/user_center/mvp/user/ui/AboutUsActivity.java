package com.clkj.user_center.mvp.user.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.clkj.user_center.R;
import com.clkj.user_center.R2;
import com.clkj.user_center.api.bean.UserInfoConfigBean;
import com.bq.utilslib.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.ChooseShare;
import com.google.gson.Gson;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/28
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_ABOUT_US_ACTIVITY)
public class AboutUsActivity extends BaseActivity {
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.view_line)
    View mViewLine;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.iv_logo)
    ImageView mIvLogo;
    @BindView(R2.id.rcyView)
    RecyclerView mRcyView;
    BaseQuickAdapter mBaseQuickAdapter;
    private UserInfoConfigBean mUserInfoConfigBean;

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_about_us;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("关于我们");
        mTvRight.setText("");
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setBackground(getResources().getDrawable(R.mipmap.icon_shap_right));

        String jsonStr = AppUtils.getAssetJson(this, "usercenter_about_us_config.json");
        mUserInfoConfigBean = new Gson().fromJson(jsonStr, UserInfoConfigBean.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRcyView.setLayoutManager(linearLayoutManager);
        mBaseQuickAdapter =
                new BaseQuickAdapter<UserInfoConfigBean.ModuleListBean, BaseViewHolder>(R.layout.user_center_item_user_info,
                        mUserInfoConfigBean.getModuleList()) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper, UserInfoConfigBean.ModuleListBean moduleListBean) {
                        helper.setText(R.id.tv_name, moduleListBean.getName());
                        helper.setText(R.id.tv_value, moduleListBean.getValue());
                        boolean hasInterval = moduleListBean.isHasInterval();
                        View interValView = helper.getView(R.id.iv_interval);
                        interValView.setVisibility(hasInterval ? View.VISIBLE : View.GONE);
                    }
                };
        mRcyView.setAdapter(mBaseQuickAdapter);
        mBaseQuickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserInfoConfigBean.ModuleListBean module = (UserInfoConfigBean.ModuleListBean) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.H5_ACTIVITY).withString("h5url", module.getPath()).navigation();
            }
        });
    }


    @OnClick({R2.id.tv_right, R2.id.rlt_title})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_right) {
            new ChooseShare(this).showBottomView(new ChooseShare.ChooseShareImpl() {
                @Override
                public void choosePlat(int index) {
                    if (index == 1) {
                        ChooseShare.shareMsg(AboutUsActivity.this, SHARE_MEDIA.WEIXIN, index);
                    } else if (index == 2) {
                    } else {
                    }
                }
            });
        }
    }
}
