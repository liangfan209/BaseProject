package com.bq.user_center.mvp.address.ui;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.user_center.R;
import com.bq.user_center.R2;
import com.bq.user_center.mvp.address.presenter.AddressManagerPresenter;
import com.bq.user_center.requset.bean.AddressInfo;
import com.bq.user_center.requset.bean.BankCardInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.fan.baseuilibrary.view.dialog.CustomDialog;

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
@Route(path = AppArouter.USER_CENTER_ADDRESS_LIST)
public class AddressManagerActivity extends BaseAcitivty implements MyRefreshLayout.LayoutInterface<AddressInfo>,
        AddressBaseIView {




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
    protected BasePresenter createPersenter() {
        mAddressManagerPresenter = new AddressManagerPresenter(this);
        return mAddressManagerPresenter;
    }

    @Override
    protected void attach() {

        mTvTitle.setText("地址管理");
        mRefreshLayout = new MyRefreshLayout<BankCardInfo>(this, this);
        mRefreshLayout.setbackgroundColor(R.color.ui_recycleview_color);
        mRefreshLayout.setRefresh(false, false);
        mFltContent.addView(mRefreshLayout);
        mCustomDialog = new CustomDialog();
        mRefreshLayout.adapter.setOnItemClickListener((adapter, view, position) -> {
            AddressInfo info = (AddressInfo) adapter.getData().get(position);
            ARouter.getInstance().build(AppArouter.USER_CENTER_ADDRESS_OPTION)
                    .withInt("optionType", AddressOptionActivity.ADDRESS_EDIT)
                    .withSerializable("mAddressInfo",info)
                    .navigation();
        });
    }


    @Override
    public BaseQuickAdapter<AddressInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<AddressInfo, BaseViewHolder>(R.layout.user_center_item_addresslist, new ArrayList<>()) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, AddressInfo info) {
                helper.setText(R.id.tv_name, info.getContacts());
                String phone = info.getPhone();
                String formatPhone = phone.subSequence(0,3)+"****"+phone.substring(8);
                helper.setText(R.id.tv_phone, formatPhone);
                helper.setText(R.id.tv_detail, info.getCity()  +info.getAddress());
                SkinCompatCheckBox cb = helper.getView(R.id.cb_address);
                cb.setChecked(info.getIs_default() == 1?true:false);
                cb.setOnClickListener(v->{
                    if(info.getIs_default() == 1){
                        cb.setChecked(true);
                    }else{
                        info.setIs_default(1);
                        AddressInfo updateAddress = new AddressInfo(info.getContacts(), info.getGender(),
                                info.getPhone(), info.getCity(), info.getAddress(), info.getIs_default());
                        mAddressManagerPresenter.updateAddress(info.getId(),updateAddress);
                    }
                });
                helper.getView(R.id.tv_delete).setOnClickListener(v -> {
                    mCustomDialog.showDialog(v.getContext(), "删除", "确定删除", "取消", "确定", new CustomDialog.ClickCallBack() {
                        @Override
                        public void ok() {
                            mAddressManagerPresenter.deleteAddress(info.getId());
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
    public void deleteAddress() {
        ToastUtils.showToastOk(this,"删除成功");
    }

    @Override
    public void getAddressList(List<AddressInfo> list) {
        mRefreshLayout.initRefreshBoo();
        mRefreshLayout.addData(list);
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
