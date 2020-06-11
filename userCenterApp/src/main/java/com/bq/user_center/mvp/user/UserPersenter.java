package com.bq.user_center.mvp.user;

import com.bq.comm_config_lib.http.SignJsonCallBack;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.user_center.ApiUserCenter;
import com.bq.user_center.bean.UserInfo;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class UserPersenter implements BasePersenter {
    private UserIView mUserIView;

    public UserPersenter(UserIView userIView) {
        mUserIView = userIView;
    }

    public void showUserInfo(){
        Map<String,String> map = new HashMap<>();
        map.put("api", ApiUserCenter.API_USER_INFO);
        map.put("token", CommSpUtils.getToken());
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<UserInfo>>(mUserIView) {
            @Override
            public void onSuccess(Response<BaseResponse<UserInfo>> response) {
                super.onSuccess(response);
                mUserIView.showUser(response.body().result);
            }
        });
    }
}
