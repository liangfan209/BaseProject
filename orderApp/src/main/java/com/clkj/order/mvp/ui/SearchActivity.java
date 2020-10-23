package com.clkj.order.mvp.ui;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.Utils;
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.requset.bean.ProductSearchBean;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.FlowLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述： 支付成功
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_SEARCH_ACTIVITY)
public class SearchActivity extends BaseActivity {

    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.det_search)
    DeletableEditText mDetSearch;
    @BindView(R2.id.rlt_search)
    RelativeLayout mRltSearch;
    @BindView(R2.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R2.id.flt_content)
    FlowLayout mFltContent;

    List<String> labelList = new ArrayList<>();

    @Autowired
    String searchName;


    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_search;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("搜索");
        getLabels();
        initEditText();
        if(!StringUtils.isEmpty(searchName)){
            mDetSearch.setText(searchName);
        }
        KeyboardUtils.showSoftInput(mDetSearch);
    }


    private void initEditText() {
        mDetSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
//                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    goSearch();
                }
                return true;
            }
        });
    }

    @OnClick({R2.id.rlt_search, R2.id.tv_cancel,R2.id.iv_delete})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.rlt_search){
            goSearch();
        }else if(view.getId() == R.id.tv_cancel){
            finish();
        }else if(view.getId() == R.id.iv_delete){
            labelList.removeAll(labelList);
            CommSpUtils.saveLables("");
            mFltContent.removeAllViews();
        }
    }

    void goSearch(){
        String lable = mDetSearch.getText().toString();
        if(!StringUtils.isEmpty(lable)){
            saveLalbels(lable);
        }
        //进入下一个页面
        jumpProductListActivity(lable);
        finish();
    }

    //获取本地的标签
    void getLabels(){
        String labels = CommSpUtils.getLables();
        if(StringUtils.isEmpty(labels)) return;
        String[] splitLabels = labels.split(",");
        for (int i = 0; i < splitLabels.length; i++) {
            labelList.add(splitLabels[i]);
        }
//        labelList = Arrays.asList(splitLabels);
        showLabelsView();
    }

    private void showLabelsView() {
        for (int i = 0; i < labelList.size(); i++) {
            View labelView = LayoutInflater.from(this).inflate(R.layout.order_item_laybel,null);
            TextView tv = labelView.findViewById(R.id.tv_click);
            tv.setText(labelList.get(i));
            mFltContent.addView(labelView);
            tv.setOnClickListener(v->{
                String text = ((TextView) v).getText().toString();
                jumpProductListActivity(text);
                finish();
            });
        }
    }

    void saveLalbels(String label){
        for (int i = 0; i < labelList.size(); i++) {
            if(labelList.get(i).equals(label)){
                return;
            }
        }
        int size = labelList.size();
        if(size >=10){
            labelList.remove(labelList.size() -1);
        }
        labelList.add(0,label);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < labelList.size(); i++) {
            sb.append(labelList.get(i));
            if(i != (labelList.size()-1)){
                sb.append(",");
            }
        }
        CommSpUtils.saveLables(sb.toString());
    }

    //根据搜索内容跳转到商品列表页面
    private void jumpProductListActivity(String lable){
        //取消焦点，隐藏键盘
        Utils.cancelFocus(mDetSearch);
        //跳转到下一个页面进行搜索
//        String titleSearch = mDetSearch.getText().toString();

        ProductSearchBean bean = new ProductSearchBean(CommSpUtils.getLocation());

        bean.setTitle(lable);
        String serachInfo = new Gson().toJson(bean);
//        ActivityUtils.finishActivity(ProductListActivity.class);
        ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
                .withString("mSearchInfo",serachInfo)
                .withString("mSearchName",lable).navigation();
    }
}
