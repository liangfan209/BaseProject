package com.bq.user_center.mvp.bankcard;

import com.bq.comm_config_lib.http.SignJsonCallBack;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.netlibrary.NetManager;
import com.bq.netlibrary.http.BaseResponse;
import com.bq.user_center.ApiUserCenter;
import com.bq.user_center.bean.BankCard;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/11
 * 版权：
 */
public class BankCardPersenter implements BasePersenter {
    private BankCardIView mBankCardView;
    public BankCardPersenter(BankCardIView bankCardView) {
        mBankCardView = bankCardView;
    }

    public void getBankList(SmartRefreshLayout layout){
        String token = CommSpUtils.getToken();
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("api", ApiUserCenter.API_BANK_LIST);
        NetManager.getNetManger().request(map, new SignJsonCallBack<BaseResponse<List<BankCard>>>(mBankCardView,layout) {
            @Override
            public void onSuccess(Response<BaseResponse<List<BankCard>>> response) {
                super.onSuccess(response);
                mBankCardView.getBankListView(response.body().data);
            }
        });
    }

    public void addBank(){
        String token = CommSpUtils.getToken();
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        map.put("api", ApiUserCenter.API_BANK_LIST);
    }
}
