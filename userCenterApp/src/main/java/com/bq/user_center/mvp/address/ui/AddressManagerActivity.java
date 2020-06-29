package com.bq.user_center.mvp.address.ui;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePersenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.address.presenter.AddressManagerPresenter;
import com.bq.user_center.requset.bean.AddressBean;
import com.bq.user_center.requset.bean.BankCard;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.fan.baseuilibrary.view.dialog.CustomDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/18
 * 版权：
 */
@Route(path = AppArouter.USER_CENTER_ADDRESS_LIST)
public class AddressManagerActivity extends BaseAcitivty implements MyRefreshLayout.LayoutInterface<AddressBean>,
        AddressBaseIView {


    public static String UPDATE_ADDRESS = "updateAddress";

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.bt_add_address)
    Button mBtAddAddress;
    private CustomDialog mCustomDialog;
    private AddressManagerPresenter mAddressManagerPresenter;


    @Override
    protected int getContentViewLayout() {
        return R.layout.user_center_activity_addresslist;
    }

    @Override
    protected BasePersenter createPersenter() {
        mAddressManagerPresenter = new AddressManagerPresenter(this);
        return mAddressManagerPresenter;
    }

    @Override
    protected void attach() {

        mTvTitle.setText("地址管理");
        mRefreshLayout = new MyRefreshLayout<BankCard>(this, this);
        mRefreshLayout.setbackgroundColor(R.color.ui_recycleview_color);
        mRefreshLayout.setRefresh(false, false);
        mFltContent.addView(mRefreshLayout);
        mCustomDialog = new CustomDialog();
        mRefreshLayout.adapter.setOnItemClickListener((adapter, view, position) -> {
            ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_OPTION)
                    .withInt("optionType", AddressOptionActivity.ADDRESS_EDIT).navigation();
        });
    }


    @Override
    public BaseQuickAdapter<AddressBean, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.user_center_item_addresslist, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, AddressBean s) {
//                helper.setText(R.id.tv_name, s.getName());
//                helper.setText(R.id.tv_phone, s.getPhoneNumber());
//                helper.setText(R.id.tv_detail, s.getProvinces() + " " + s.getDetailAddress());
                RadioButton cb = helper.getView(R.id.cb_address);
                cb.setChecked(s.getType() == 1 ? true : false);

                cb.setOnClickListener(v->{
                    if(s.getType() == 1){
                        cb.setChecked(true);
                    }else{
                        s.setType(1);
                        mAddressManagerPresenter.updateAddress(s);
                    }
                });

                helper.getView(R.id.tv_delete).setOnClickListener(v -> {
                    mCustomDialog.showDialog(v.getContext(), "删除", "确定删除", "取消", "确定", new CustomDialog.ClickCallBack() {
                        @Override
                        public void ok() {
                            mAddressManagerPresenter.deleteAddress(s);
                        }

                        @Override
                        public void cacel() {
                        }
                    });
                });
            }
        };
    }

    @Override
    public void getAddressList(List<AddressBean> list) {
        mRefreshLayout.adapter.setNewData(list);
        mRefreshLayout.adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(int page, int pageSize) {
        mAddressManagerPresenter.getAddressList();
    }

    @OnClick(R2.id.bt_add_address)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.bt_add_address) {
            ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_OPTION)
                    .withInt("optionType", AddressOptionActivity.ADDRESS_ADD).navigation();
        }
    }
}
