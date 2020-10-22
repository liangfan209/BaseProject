package com.clkj.user_center.mvp.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.clkj.user_center.R;
import com.clkj.user_center.R2;
import com.clkj.user_center.mvp.user.presenter.UserPresenter;
import com.clkj.user_center.requset.bean.MessageInfo;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;

/**
 * 文件名：
 * 描述：院校专业详情
 * 作者：梁帆
 * 时间：2020/7/30
 * 版权：
 */
public class MessageListFragment extends BaseFragment implements MyRefreshLayout.LayoutInterface<MessageInfo>
        , UserBaseIView {


    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    private List<MessageInfo> mlist = new ArrayList<>();
    private UserPresenter mPresenter;
    private int type = 1;

    public static MessageListFragment getInstance(int type) {
        MessageListFragment fragment = new MessageListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mType", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.laoyut_frame_layout;
    }

    @Override
    protected BasePresenter createPersenter() {
        mPresenter = new UserPresenter(this);
        return mPresenter;
    }

    @Override
    protected void attach() {
        type = this.getArguments().getInt("mType");
        mRefreshLayout = new MyRefreshLayout<String>(this.getContext(), this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);

        mRefreshLayout.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //进入消息详情
                MessageInfo msgInfo = (MessageInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.USER_CENTER_MESSAGE_DETAIL_ACTIVITY)
                        .withSerializable("mMessageInfo",msgInfo).navigation();
                if(type == 1){
                    mPresenter.readMessage(msgInfo.getId()+"");
                }
            }
        });
        EventBus.getDefault().register(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void providerData(String key) {
        if(key.equals("un_message") ){
            if(type == 1){
                mPresenter.getUnreadCount();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void changeMsgIView() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public BaseQuickAdapter<MessageInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<MessageInfo, BaseViewHolder>(R.layout.user_center_item_message,mlist) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, MessageInfo bean) {
                helper.setText(R.id.tv_title,bean.getTitle());
                helper.setText(R.id.tv_message,bean.getContent());
                helper.setText(R.id.tv_time,bean.getCreate_time());
                ImageView ivImg = helper.getView(R.id.iv_read);

                if(type == 1){
                    if("read".equals(bean.getStatus())){
                        ivImg.setVisibility(View.GONE);
                    }else{
                         ivImg.setVisibility(View.VISIBLE);
                    }
                }else{
                    ivImg.setVisibility(View.GONE);
                }

            }
        };
    }


    @Override
    public void messageListView(List<MessageInfo> list) {
        mRefreshLayout.addData(list);
    }


    @Override
    public void loadData(int page, int pageSize) {
        mPresenter.getMessage(page,type);
        //获取未读消息
        if(type == 1)
            mPresenter.getUnreadCount();
    }

    @Override
    public void unReadCountView(int count) {
        //将数字发送出去
        EventBus.getDefault().post("unread_count,"+count);
    }
}
