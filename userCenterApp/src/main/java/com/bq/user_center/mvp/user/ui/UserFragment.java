package com.bq.user_center.mvp.user.ui;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.request.Api;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.api.bean.UserCenterConfigBean;
import com.bq.user_center.mvp.user.presenter.UserPresenter;
import com.bq.user_center.requset.bean.UserInfo;
import com.bq.utilslib.AppUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.CircleImageView;
import com.fan.baseuilibrary.view.SimpleDividerItemDecoration;
import com.fan.baseuilibrary.view.dialog.CustomDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/10
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_USER_FRAGMENT)
public class UserFragment extends BaseFragment implements UserBaseIView {


    @BindView(R2.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R2.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R2.id.rlt_head)
    RelativeLayout mRltHead;

    @BindView(R2.id.rlt_logout)
    RelativeLayout mRltLogout;

    @BindView(R2.id.llt_content)
    LinearLayout mLltContent;
    private UserPresenter mUserPersenter;
    private UserCenterConfigBean userConfig;
    private UserInfo mUserinfo;

    private List<BaseQuickAdapter<UserCenterConfigBean.ModuleListBean.TabListBean, BaseViewHolder>> mBaseQuickAdapters = new ArrayList<>();
    BaseQuickAdapter<UserCenterConfigBean.ModuleListBean.TabListBean, BaseViewHolder> adapter;

    @Override
    protected BasePresenter createPersenter() {
        mUserPersenter = new UserPresenter(this);
        return mUserPersenter;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_fragment_user;
    }

    @Override
    protected void attach() {
        String jsonStr = AppUtils.getAssetJson(this.getContext(),"usercenter_center_config.json");
        userConfig = new Gson().fromJson(jsonStr, UserCenterConfigBean.class);
        updateView();

    }

    @Override
    public void onResume() {
        super.onResume();
        //获取用户信息
        String token = CommSpUtils.getToken();
        if(!StringUtils.isEmpty(token)){
            mUserPersenter.showUserInfo();
            mRltLogout.setVisibility(View.VISIBLE);
        }else{
            mRltLogout.setVisibility(View.GONE);
            mTvNickName.setText("未登录");
        }
    }

    public void updateView() {
        List<UserCenterConfigBean.ModuleListBean> moduleList = userConfig.getModuleList();
        for (int i = 0; i < moduleList.size(); i++) {
            createRecycleView(moduleList.get(i));
        }
    }

    /**
     * 创建模块
     * @param moduleListBean
     */
    private void createRecycleView(UserCenterConfigBean.ModuleListBean moduleListBean) {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        View view = inflater.inflate(R.layout.user_center_layout_card_recycleview,null);
        RecyclerView recyclerView = view.findViewById(R.id.rcyView);
        TextView tvTitle =view.findViewById(R.id.tv_title);
        int itemResource = -1;


        if (moduleListBean.getType().equals("grid")) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 4);
            recyclerView.setLayoutManager(gridLayoutManager);
            itemResource = R.layout.user_center_item_user_grid;
            tvTitle.setText(moduleListBean.getName());

        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            itemResource = R.layout.user_center_item_user_vertical;
            tvTitle.setVisibility(View.GONE);
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this.getContext(),R.color.ui_recycleview_item_line_color,1
                , AppUtils.dp2px(this.getContext(),20),AppUtils.dp2px(this.getContext(),10)));

        }
        adapter = new
                BaseQuickAdapter<UserCenterConfigBean.ModuleListBean.TabListBean, BaseViewHolder>(itemResource, moduleListBean.getTabList()) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           UserCenterConfigBean.ModuleListBean.TabListBean tabListBean) {
                        helper.setText(R.id.tv_name,tabListBean.getName());
                        TextView tvRight = helper.getView(R.id.tv_right);
                        tvRight.setText(tabListBean.getValue());
                        int resId = getResources().getIdentifier(tabListBean.getIcon(), "mipmap",
                                UserFragment.this.getActivity().getPackageName());
                        String name = tabListBean.getName();
                        ImageView ivImg = helper.getView(R.id.iv_img);
                        ivImg.setBackgroundResource(resId);
                    }
                };
        mBaseQuickAdapters.add(adapter);

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            UserCenterConfigBean.ModuleListBean.TabListBean tabBean = (UserCenterConfigBean.ModuleListBean.TabListBean) adapter1.getData().get(position);
            String path = tabBean.getPath();
            if(path.startsWith("http")){
                ARouter.getInstance().build(AppArouter.H5_ACTIVITY)
                        .withString("h5url",path).navigation();
            }else{
                if(mUserinfo == null || StringUtils.isEmpty(CommSpUtils.getToken())){
                    ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY)
                                .withString("mPath","-1").navigation();
                    return;
                }
                if("实名认证".equals(tabBean.getName())){
                    ARouter.getInstance().build(path).withInt("isCertication",mUserinfo.getCustomer_info().getIs_certify()).navigation();
                }else{
                    if(!StringUtils.isEmpty(path)){
                        ARouter.getInstance().build(path).navigation();
                    }
                }
            }

        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = AppUtils.dp2px(this.getContext(),-10);
        params.bottomMargin = AppUtils.dp2px(this.getContext(),-10);
        mLltContent.addView(view,params);
    }

    @OnClick({R2.id.rlt_head,R2.id.tv_logout})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.rlt_head){
            ARouter.getInstance().build(AppArouter.USER_CENTER_USER_INFO_ACTIVITY).navigation();
        }else if(view.getId() == R.id.tv_logout){
            new CustomDialog().showDialogDialog(this.getContext(), "退出登录", "确定退出登录吗？", new CustomDialog.ClickCallBack() {
                @Override
                public void ok() {
                    mUserPersenter.logout();
                }

                @Override
                public void cacel() {
                }
            });
        }
    }

    @Override
    public void logout() {
        ToastUtils.showToastOk(this.getActivity(), "退出成功");
        new Handler().postDelayed(() -> {
//            getActivity().finish();
//            ((MainActivity)getActivity()).selectFragment(0);
            //告诉主页切换到首页
            EventBus.getDefault().post("go_home");
            ARouter.getInstance().build(AppArouter.LOGIN_ACTVITY).navigation();
        }, 1000);
    }

    @Override
    public void showUser(UserInfo info) {
        for (BaseQuickAdapter<UserCenterConfigBean.ModuleListBean.TabListBean, BaseViewHolder> baseQuickAdapter :
                mBaseQuickAdapters) {
            List<UserCenterConfigBean.ModuleListBean.TabListBean> data = baseQuickAdapter.getData();
            for (UserCenterConfigBean.ModuleListBean.TabListBean datum : data) {
                if(datum.getName().equals("实名认证")) {
                    datum.setValue(info.getCustomer_info().getIs_certify() == 1 ? "已实名" : "");
                }
                baseQuickAdapter.notifyDataSetChanged();
            }
        }
        this.mUserinfo = info;
        mTvNickName.setText(info.getCustomer_info().getNick());
        Glide.with(this).load(Api.BASE_API+ info.getCustomer_info().getHead_url()).into(mIvHead);

    }
}
