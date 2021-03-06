package com.clkj.user_center.mvp.user.presenter;

import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.clkj.user_center.api.ComponentService;
import com.clkj.user_center.api.UserCenterEventKey;
import com.clkj.user_center.mvp.user.ui.UserBaseIView;
import com.clkj.user_center.mvp.user.ui.UserFragment;
import com.clkj.user_center.mvp.user.ui.UserinfoActivity;
import com.clkj.user_center.requset.UserCenterHttpReqeustImp;
import com.clkj.user_center.requset.bean.CertificationBean;
import com.clkj.user_center.requset.bean.MessageListBean;
import com.clkj.user_center.requset.bean.UnreadCountBean;
import com.clkj.user_center.requset.bean.UserInfo;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class UserPresenter implements BasePresenter {
    private UserBaseIView mUserIView;
    private UserCenterHttpReqeustImp mUserCenterHttpReqeustImp;

    public UserPresenter(UserBaseIView userIView) {
        mUserIView = userIView;
        mUserCenterHttpReqeustImp = new UserCenterHttpReqeustImp();
    }

    /**
     * 获取用户信息
     */
    public void showUserInfo(){
        mUserCenterHttpReqeustImp.showUserInfo(new AbstractReqeustCallback<UserInfo>(mUserIView) {
            @Override
            public void onSuccess(UserInfo info) {
                mUserIView.showUser(info);
                //将用户信息保存到 sp
                CommSpUtils.saveUserInfo(new Gson().toJson(info.getCustomer_info()));
            }
        });
    }

    /**
     * 更新用户信息
     * @param tpye
     * @param value
     */
    public void updateUserInfo(String tpye,String value){
        String updateInfo = new Gson().toJson(new UserInfo.CustomerInfoBean(tpye, value));
        mUserCenterHttpReqeustImp.updateUserInfo(updateInfo, new AbstractReqeustCallback<String>(mUserIView) {
            @Override
            public void onSuccess(String str) {
//                ToastUtils.showToastOk(mUserIView.getActivity(),"修改成功");
                EventBus.getDefault().post(UserCenterEventKey.UPDATE_USER);
            }
        });
    }

    //退出登录
    public void logout(){
        mUserIView.showLoading();
        ComponentService.logout((data)->{
            mUserIView.onComplete();
            if(data.getCode() == MessageBody.SUCCESS_CODE){
                mUserIView.logout();
            }else{
                mUserIView.onError(data.getContent());
            }
        });
    }


    public void certification(String name, String id,String font,String back,String hand){
        mUserCenterHttpReqeustImp.certification(name,id,font,back,hand, new AbstractReqeustCallback<String>(mUserIView) {
            @Override
            public void onSuccess(String str) {
                EventBus.getDefault().post(UserCenterEventKey.UPDATE_USER);
                mUserIView.certificationView();
            }
        });
    }

    public void getCertification(){
        mUserCenterHttpReqeustImp.getCertification(new AbstractReqeustCallback<CertificationBean>(mUserIView) {
            @Override
            public void onSuccess(CertificationBean info) {
                mUserIView.getCertificationView(info.getCertification_info());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUser(String key){
        if(key.equals(UserCenterEventKey.UPDATE_USER)){
            if( mUserIView != null && (mUserIView instanceof UserFragment ||
                    mUserIView instanceof UserinfoActivity)) {
                showUserInfo();
            }
        }
    }

    public void getMessage(int page,int type) {
        mUserCenterHttpReqeustImp.getMessageList(page,type,new AbstractReqeustCallback<MessageListBean>(mUserIView) {
            @Override
            public void onSuccess(MessageListBean bean) {
                mUserIView.messageListView(bean.getData_list());
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }

    public void getUnreadCount() {
        mUserCenterHttpReqeustImp.unReadCount(new AbstractReqeustCallback<UnreadCountBean>(mUserIView) {
            @Override
            public void onSuccess(UnreadCountBean bean) {
                mUserIView.unReadCountView(bean.getUnread_count());
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }


    public void readMessage(String id) {
        mUserCenterHttpReqeustImp.readMessage(id,new AbstractReqeustCallback<String>(mUserIView) {
            @Override
            public void onSuccess(String bean) {
                mUserIView.changeMsgIView();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }


    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        EventBus.getDefault().unregister(this);
    }


}
