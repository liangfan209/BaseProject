package com.bq.walletapp.mvp.ui;

import android.graphics.Typeface;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.walletapp.R;
import com.bq.walletapp.R2;
import com.bq.walletapp.api.bean.BankCard;
import com.bq.walletapp.mvp.presenter.BankPresenter;
import com.bq.walletapp.mvp.presenter.WalletPresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.MaxHeightRecyclerView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


@Route(path = AppArouter.WALLET_TACK_CASH_ACTIVITY)
public class TackCashActivity extends BaseAcitivty implements WalletIView {
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.et_tack_money)
    DeletableEditText mEtTackMoney;
    @BindView(R2.id.tv_hint)
    TextView mTvHint;
    @BindView(R2.id.tv_add_bankcard)
    TextView mTvAddBankcard;
    @BindView(R2.id.rcy_bank)
    MaxHeightRecyclerView mRcyBank;
    @BindView(R2.id.llt_has_data)
    LinearLayout mLltHasData;
    @BindView(R2.id.llt_no_data)
    LinearLayout mLltNoData;
    @BindView(R2.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R2.id.tv_erro)
    TextView mTvErro;

    private BankPresenter mBankPresenter;
    private WalletPresenter mWalletPresenter;

    BaseQuickAdapter<BankCard, BaseViewHolder> adpater;
    private int mPosition = -1;
    private long money;


    @Override
    protected int getContentViewLayout() {
        return R.layout.wallet_activity_tack_cash;
    }

    @Override
    protected BasePresenter createPersenter() {
        mWalletPresenter = new WalletPresenter(this);
        mBankPresenter = new BankPresenter(this);
        return mBankPresenter;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("提现");
        initEditView();
        //获取余额
        mBankPresenter.getBankCardList();
        mWalletPresenter.getBalance();
    }

    @Override
    public void getBalanceView(long balance) {
        this.money = balance;
        SpannableString ss = new SpannableString(String.format("可提现金额%s元", Utils.getDouble2(money)));
        mTvHint.setText(String.format("当前账户资产%s元", Utils.getDouble2(money)));
        mEtTackMoney.setHint(ss);
    }

    private void initEditView() {
        mEtTackMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mEtTackMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEtTackMoney.setTextSize(s.length() > 0 ? 24 : 13);
                mEtTackMoney.setTypeface(s.length() > 0 ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);


                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mEtTackMoney.setText(s.subSequence(0, 1));
                        mEtTackMoney.setSelection(1);
                        return;
                    }
                }
                //如果第一为点，直接显示0.
                if (s.toString().startsWith(".")) {
                    mEtTackMoney.setText("0.");
                    mEtTackMoney.setSelection(2);
                    return;
                }
                //限制输入小数位数(2位)
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 2 + 1);
                        mEtTackMoney.setText(s);
                        mEtTackMoney.setSelection(s.length());
                    }
                }
                if ("0".equals(s.toString()) || "0.".equals(s.toString()) || "0.0".equals(s.toString()) || "0.00".equals(s.toString())) {
                    mTvConfirm.setEnabled(false);
                    return;
                }
                if (s.length() > 0) {
                    if (Double.valueOf(s.toString()) > money) {
                        //提示底部错误提示
                        mTvErro.setVisibility(View.VISIBLE);
                        mTvConfirm.setEnabled(false);
                    } else {
                        mTvErro.setVisibility(View.GONE);
                        if (mPosition != -1) {
                            mTvConfirm.setEnabled(true);
                        }
                    }
                } else {
                    mTvConfirm.setEnabled(false);
                    mTvErro.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getBankList(List<BankCard> result) {
        if (result != null && result.size() > 0) {
            mLltHasData.setVisibility(View.VISIBLE);
            mLltNoData.setVisibility(View.GONE);
            result.get(0).setCheck(true);
            mPosition = 0;
            mEtTackMoney.setText(mEtTackMoney.getText().toString()+"");
            //初始化列表页
            initRecycleView(result);
        } else {
            mLltHasData.setVisibility(View.GONE);
            mLltNoData.setVisibility(View.VISIBLE);
        }
    }


    //初始化银行卡列表页面
    private void initRecycleView(List<BankCard> result) {
        mRcyBank.setLayoutManager(new LinearLayoutManager(this));
        adpater = new BaseQuickAdapter<BankCard, BaseViewHolder>(R.layout.wallet_item_bank_select, result) {
            @Override
            protected void convert(BaseViewHolder helper, BankCard item) {
                CheckBox cbBank = helper.getView(R.id.rb_bank);
                String bankNumber = item.getBank_number();
                if(bankNumber.length() > 4)
                cbBank.setText(item.getBank_name()+String.format(" (*****%s)",bankNumber.substring(bankNumber.length()-4)));
                cbBank.setChecked(item.isCheck());
                cbBank.setOnClickListener(v -> {
                    mPosition = adpater.getData().indexOf(item);
                    //将其它的位置
                    traverseList();
                });
            }
        };
        mRcyBank.setAdapter(adpater);
    }

    //遍历集合
    private void traverseList() {
        for (int i = 0; i < adpater.getData().size(); i++) {
            if (i == mPosition) {
                adpater.getData().get(i).setCheck(true);
            } else {
                adpater.getData().get(i).setCheck(false);
            }
        }
        adpater.notifyDataSetChanged();
    }


    @OnClick({R2.id.tv_add_bankcard, R2.id.tv_confirm})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_add_bankcard) {
            ARouter.getInstance().build(AppArouter.USER_CENTER_BANK_ADD_ACTIVITY).navigation();
        }else if(view.getId() == R.id.tv_confirm){
            //确认提现
            String money = mEtTackMoney.getText().toString();
            Double moneyDouble = Double.valueOf(money);
            int moneyInt = (int) (moneyDouble*100);
            mWalletPresenter.tackCash(moneyInt+"","");
        }
    }

    @Override
    public void tackCashView() {
        ToastUtils.showToast(this,"提现成功");
        new Handler().postDelayed(() -> {
            finish();
        },1000);
    }

    //提现
    private void tackCash(String pwd) {
    }


}
