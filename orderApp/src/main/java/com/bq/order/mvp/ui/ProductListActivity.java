package com.bq.order.mvp.ui;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.mvp.ui.fragment.ProductListFragment;
import com.bq.order.mvp.ui.hodler.ProductType;
import com.bq.order.requset.bean.ProductSearchBean;
import com.bq.order.requset.bean.SelecterBean;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.DeleteTextView;
import com.fan.baseuilibrary.view.FlowLayout;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import skin.support.content.res.SkinCompatResources;

/**
 * 文件名：
 * 描述： 产品列表
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
public class ProductListActivity extends BaseActivity implements ProductIview{
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.tv_address_location)
    TextView mTvAddressLocation;
    @BindView(R2.id.det_search)
    DeletableEditText mDetSearch;
    @BindView(R2.id.rlt_search)
    RelativeLayout mRltSearch;
    @BindView(R2.id.iv_advertising)
    ImageView mIvAdvertising;
    @BindView(R2.id.tv_school_select)
    TextView mTvSchoolSelect;
    @BindView(R2.id.tv_profession_select)
    TextView mTvProfessionSelect;
    @BindView(R2.id.tv_year_select)
    TextView mTvYearSelect;
    @BindView(R2.id.tv_school)
    DeleteTextView mTvSchool;
    @BindView(R2.id.tv_profession)
    DeleteTextView mTvProfession;
    @BindView(R2.id.tv_year)
    DeleteTextView mTvYear;
    @BindView(R2.id.rv_product_list)
    RecyclerView mRvProductList;
    @BindView(R2.id.flt_content)
    FrameLayout mFrameLayout;
    @BindView(R2.id.flow_content)
    FlowLayout mFlowContent;

    @BindView(R2.id.rlt_profession_type)
    RelativeLayout mRltProfesionType;

    ProductListFragment mProductListFragment;
    private List<SelecterBean.SelectInfo> mSchoolList;
    private List<SelecterBean.SelectInfo> mProfessionList;
    private List<SelecterBean.SelectInfo> mDurationList;

    @Autowired
    String mSearchInfo;
    @Autowired
    String mSearchName;
    @Autowired
    SelecterBean.SelectInfo mSelectInfo;


    private ProductPresenter mProductPresenter;

    private Map<Integer, SelecterBean.SelectInfo> mProfessionMap = new HashMap<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_product_list;
    }

    @Override
    protected BasePresenter createPersenter() {

        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("商品列表");
        mTvAddressLocation.setText(CommSpUtils.getLocation());
        initProductListView();
        mTvSchool.setCallBack(()->{
            updateFlowlayout(0,"delete",null,true);
        });
        mTvProfession.setCallBack(()->{
            updateFlowlayout(1,"delete",null,true);
        });
        mTvYear.setCallBack(()->{
            updateFlowlayout(2,"delete",null,true);
        });
        mTvSchool.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.drawable.ui_shap_verification_code_select));
        mTvSchool.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
        mTvProfession.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.drawable.ui_shap_verification_code_select));
        mTvProfession.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
        mTvYear.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.drawable.ui_shap_verification_code_select));
        mTvYear.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
        initEditText();


    }

    private void initEditText() {
        mDetSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
//                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {

                    //取消焦点，隐藏键盘
                    Utils.cancelFocus(mDetSearch);
                    updateFragment();
                }
                return true;
            }
        });
        if(!StringUtils.isEmpty(mSearchName)){
            mDetSearch.setText(mSearchName);
        }
        mDetSearch.setClearDrawableVisible(false);
    }

    private void initProductListView() {
        mFlowContent.setVisibility(View.GONE);
        //从专业进来的
        if(mSelectInfo != null){
            ProductSearchBean bean  = new ProductSearchBean(CommSpUtils.getLocation());
            bean.setMajor_id(mSelectInfo.getId());
            mSearchInfo = new Gson().toJson(bean);
            //
            mProfessionMap.put(1,mSelectInfo);
            updateFlowlayout(1,"add",mSelectInfo,false);
            mFlowContent.setVisibility(View.VISIBLE);
        }
        //冲4个按钮进来的
        if(mSearchInfo.contains("资格证")){
            mRltProfesionType.setVisibility(View.GONE);
            mFlowContent.setVisibility(View.GONE);
        }

        ProductType type =  new ProductType(mSearchInfo,10);
        mProductListFragment = ProductListFragment.getInstance(type);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flt_content, mProductListFragment);
        fragmentTransaction.commit();


    }

    @OnClick({R2.id.rlt_search, R2.id.tv_school_select, R2.id.tv_profession_select, R2.id.tv_year_select,R2.id.tv_address_location,R2.id.iv_address_location})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_school_select) {
            chooseType(0);
        } else if (view.getId() == R.id.tv_profession_select) {
            chooseType(1);
        } else if (view.getId() == R.id.tv_year_select) {
            chooseType(2);
        }else if(view.getId() == R.id.rlt_search){
            Utils.cancelFocus(mDetSearch);
            updateFragment();
        }else if(view.getId() == R.id.tv_address_location){
            CityUtils.getInstance(this).showPickerView(this, new CityUtils.CityCallBack() {
                @Override
                public void getCitys(String citys) {
                    CommSpUtils.saveLocaltion(citys);
                    mTvAddressLocation.setText(citys);
                    updateFragment();
                }
            });
        }else if(view.getId() == R.id.iv_address_location){
            mTvAddressLocation.setText("武汉");
            CommSpUtils.saveLocaltion("武汉");
            updateFragment();
        }
    }

    @Override
    public void getSchoolAllSelcterView(List<SelecterBean.SelectInfo> list) {
        mSchoolList = list;
        showPopWindow(0,list);
    }

    @Override
    public void getProfessionAllSelcterView(List<SelecterBean.SelectInfo> list) {
        mProfessionList = list;
        showPopWindow(1,list);
    }
    @Override
    public void getDurationAllSelcterView(List<SelecterBean.SelectInfo> list) {
        mDurationList = list;
        showPopWindow(2,list);
    }

    void showPopWindow(int key, List<SelecterBean.SelectInfo> list){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                SelecterBean.SelectInfo info = list.get(options1);
                updateFlowlayout(key,"add",info,true);
            }
        }).setSubmitColor(SkinCompatResources.getColor(this, R.color.ui_primary_color))
                .setCancelColor(SkinCompatResources.getColor(this, R.color.ui_primary_color)).build();
        pvOptions.setPicker(list);
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
        }
        pvOptions.setSelectOptions(index);
        pvOptions.show();
    }


    private void chooseType(int key) {
       ProductSearchBean bean = new ProductSearchBean();
        if(key == 0){
            if(mSchoolList == null){
                String serachInfo = new Gson().toJson(bean);
                mProductPresenter.getSchoolAll(serachInfo);
            }else{
                getSchoolAllSelcterView(mSchoolList);
            }
        }
        if(key == 1){
            if(mProfessionList == null){
                String serachInfo = new Gson().toJson(bean);
                mProductPresenter.getProfessionAll(serachInfo);
            }else{
                getProfessionAllSelcterView(mProfessionList);
            }
        }

        if(key == 2){
            if(mDurationList == null){
                String serachInfo = new Gson().toJson(bean);
                mProductPresenter.getDurationAll(serachInfo);
            }else{
                getDurationAllSelcterView(mDurationList);
            }
        }
    }


    /**
     * 更新flowLayout
     *
     * @param key     0 1 2
     * @param type    "add"  "delete"
     * @param content
     */
    public void updateFlowlayout(int key, String type, SelecterBean.SelectInfo content,boolean isUpdate) {
        if (type.equals("add")) {
            mProfessionMap.put(key, content);
        } else if (type.equals("delete")) {
            mProfessionMap.remove(key);
        }
        Set<Map.Entry<Integer, SelecterBean.SelectInfo>> entries = mProfessionMap.entrySet();
        mFlowContent.setVisibility(entries.size() > 0 ? View.VISIBLE : View.GONE);

        switch (key){
            case 0:
                mTvSchool.setVisibility(type.equals("add")?View.VISIBLE:View.GONE);
                if(type.equals("add")){
                    mTvSchool.setText(content.getName());
                    mTvSchoolSelect.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
                    mTvSchoolSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,SkinCompatResources.getDrawable(this,R.mipmap.order_icon_xiala_select),null);
                }else{
                    mTvSchoolSelect.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_normal_color));
                    mTvSchoolSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,SkinCompatResources.getDrawable(this,R.mipmap.order_icon_xiala_unselect),null);
                }
                break;
            case 1:
                mTvProfession.setVisibility(type.equals("add")?View.VISIBLE:View.GONE);
                if(type.equals("add")){
                    mTvProfession.setText(content.getName());
                    mTvProfessionSelect.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
                    mTvProfessionSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,SkinCompatResources.getDrawable(this,R.mipmap.order_icon_xiala_select),null);
                }else{
                    mTvProfessionSelect.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_normal_color));
                    mTvProfessionSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,SkinCompatResources.getDrawable(this,R.mipmap.order_icon_xiala_unselect),null);
                }
                break;
            case 2:
                mTvYear.setVisibility(type.equals("add")?View.VISIBLE:View.GONE);
                if(type.equals("add")){
                    mTvYear.setText(content.getName());
                    mTvYearSelect.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
                    mTvYearSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,SkinCompatResources.getDrawable(this,R.mipmap.order_icon_xiala_select),null);
                }else{
                    mTvYearSelect.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_normal_color));
                    mTvYearSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,SkinCompatResources.getDrawable(this,R.mipmap.order_icon_xiala_unselect),null);
                }
                break;
        }

        //告知fragment开始搜索，搜索第一页的所有条件
        if(isUpdate)
            updateFragment();

    }

    private void updateFragment(){
        String titleSearch = mDetSearch.getText().toString();
        ProductSearchBean bean = new ProductSearchBean(CommSpUtils.getLocation());
        bean.setTitle(titleSearch);
        Set<Map.Entry<Integer, SelecterBean.SelectInfo>> entries = mProfessionMap.entrySet();
        for (Map.Entry<Integer, SelecterBean.SelectInfo> entry:entries){
            if(entry.getKey() == 0){
                bean.setSchool_id(entry.getValue().getId());
            }else if(entry.getKey() == 1){
                bean.setMajor_id(entry.getValue().getId());
            }else if(entry.getKey() == 2){
                bean.setDuration(entry.getValue().getId());
            }
            entry.getValue();
        }
        mSearchInfo = new Gson().toJson(bean);
        mProductListFragment.updateFragment(mSearchInfo);
    }
}
