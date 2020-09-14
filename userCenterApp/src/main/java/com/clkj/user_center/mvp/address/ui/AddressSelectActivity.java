package com.clkj.user_center.mvp.address.ui;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.mvp.ui.CommomMultiItemQuickAdapter;
import com.clkj.user_center.R;
import com.clkj.user_center.R2;
import com.clkj.user_center.mvp.address.presenter.AddressManagerPresenter;
import com.clkj.user_center.mvp.bankcard.presenter.BankCardPresenter;
import com.clkj.user_center.requset.bean.AddressInfo;
import com.clkj.user_center.requset.bean.AddressListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.widget.SkinCompatCheckBox;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/18
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_ADDRESS_SELECT)
public class AddressSelectActivity extends BaseActivity implements MyRefreshLayout.LayoutInterface<AddressInfo>,
        AddressBaseIView {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    BankCardPresenter mBankCardPersenter;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.rlt_add_address)
    RelativeLayout mRltAddAddress;
    private AddressManagerPresenter mAddressManagerPresenter;

    @Autowired
    int addressId;

    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_address_selecter;
    }

    @Override
    protected BasePresenter createPersenter() {
        mAddressManagerPresenter = new AddressManagerPresenter(this);
        return mAddressManagerPresenter;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("选择地址");
        mRefreshLayout = new MyRefreshLayout<AddressListBean>(this, this);
        mRefreshLayout.setbackgroundColor(R.color.white);
        mFltContent.addView(mRefreshLayout);
        mRefreshLayout.setRefresh(false, false);
        //选择某一个类型
        mRefreshLayout.adapter.setOnItemClickListener((adapter, view, position) -> {
            AddressInfo info = (AddressInfo) adapter.getData().get(position);
            Intent intent = new Intent();
            intent.putExtra("address_info",new Gson().toJson(info));
            setResult(0,intent);
            finish();
        });
    }


    @Override
    public BaseQuickAdapter<AddressInfo, ? extends BaseViewHolder> createAdapter() {
        return new CommomMultiItemQuickAdapter<AddressInfo, BaseViewHolder>(new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, AddressInfo addressBean) {
                baseViewHolder.getView(R.id.tv_edit).setOnClickListener(v -> {
                    ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_OPTION)
                            .withInt("optionType", AddressOptionActivity.ADDRESS_EDIT)
                            .withSerializable("mAddressInfo", addressBean).navigation();
                });


                    SkinCompatCheckBox cb = baseViewHolder.getView(R.id.skcb);
                    if(addressId == -1){
                        if(addressBean.getIs_default() == 1)
                            cb.setVisibility(addressBean.getIs_default() == 1?View.VISIBLE:View.GONE);
                    }else{
                        cb.setVisibility(addressId == Integer.valueOf(addressBean.getId())?View.VISIBLE:View.GONE);
                    }



                TextView tvAdress = baseViewHolder.getView(R.id.tv_txt);
                    TextView tvName = baseViewHolder.getView(R.id.tv_name);
                    TextView tvPhone = baseViewHolder.getView(R.id.tv_phone);
                    tvName.setText(addressBean.getContacts());
                    String phone = addressBean.getPhone();
                    String formatPhone = phone.substring(0,3)+"****"+phone.substring(8);
                    tvPhone.setText(formatPhone);



                if (baseViewHolder.getItemViewType() == 1) {
                    tvAdress.setText("         " + addressBean.getCity()+" "+addressBean.getAddress());
                } else if (baseViewHolder.getItemViewType() == 0) {
                    tvAdress.setText(addressBean.getCity()+" "+addressBean.getAddress());
                }
            }

            @Override
            public void addType() {
                addItemType(1, R.layout.user_center_item_address_selecter);
                addItemType(0, R.layout.user_center_item_address_unselecter);
            }
        };
    }

    @Override
    public void getAddressList(List<AddressInfo> list) {
        mRefreshLayout.adapter.setNewData(list);
        mRefreshLayout.adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(int page, int pageSize) {
        mAddressManagerPresenter.getAddressList();
    }

    @OnClick(R2.id.rlt_add_address)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.rlt_add_address) {
            ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_OPTION)
                    .withInt("optionType", AddressOptionActivity.ADDRESS_ADD).navigation();
        }
    }
}
