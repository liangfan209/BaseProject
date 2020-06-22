package com.bq.user_center.mvp.user.presenter;

import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.user_center.api.ComponentService;
import com.bq.user_center.mvp.user.ui.UserBaseIView;
import com.bq.user_center.requset.UserCenterHttpReqeustImp;
import com.bq.user_center.requset.bean.UserInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class UserPresenter implements BasePersenter {
    private UserBaseIView mUserIView;

    public UserPresenter(UserBaseIView userIView) {
        mUserIView = userIView;
    }

    public void showUserInfo(){
        new UserCenterHttpReqeustImp().showUserInfo(new AbstractReqeustCallback<UserInfo>(mUserIView) {
            @Override
            public void onSuccess(UserInfo info) {
                mUserIView.showUser(info);
            }
        });
    }

    public void logout(){
        ComponentService.logout((data)->{
            if(data.getCode() == MessageBody.SUCCESS_CODE){
                mUserIView.logout();
            }else{
                mUserIView.onError(data.getContent());
            }
        });
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }
}
