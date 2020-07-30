package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.ui.fragment.ProductListFragment;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.fan.baseuilibrary.view.DeleteTextView;
import com.fan.baseuilibrary.view.FlowLayout;

import java.util.ArrayList;
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
public class ProductListActivity extends BaseActivity {
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

    private Map<Integer, String> mProfessionMap = new HashMap<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_product_list;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        mTvTitle.setText("商品列表");
        initProductListView();

        mTvSchool.setCallBack(()->{
            updateFlowlayout(0,"delete","");
        });
        mTvProfession.setCallBack(()->{
            updateFlowlayout(1,"delete","");
        });
        mTvYear.setCallBack(()->{
            updateFlowlayout(2,"delete","");
        });
        mTvSchool.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.drawable.ui_shap_verification_code_select));
        mTvSchool.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
        mTvProfession.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.drawable.ui_shap_verification_code_select));
        mTvProfession.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
        mTvYear.setBackgroundDrawable(SkinCompatResources.getDrawable(this,R.drawable.ui_shap_verification_code_select));
        mTvYear.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
    }

    private void initProductListView() {
        ProductListFragment fragment = ProductListFragment.getInstance(10);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flt_content, fragment);
        fragmentTransaction.commit();

        mFlowContent.setVisibility(View.GONE);

//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("1");
//        list.add("1");
//        list.add("1");
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRvProductList.setLayoutManager(linearLayoutManager);
//        BaseQuickAdapter adapter = new
//                BaseQuickAdapter<String, BaseViewHolder>(R.layout.order_item_product, list) {
//                    @Override
//                    protected void convert(@NotNull BaseViewHolder helper,
//                                           String bean) {
//                    }
//                };
//        mRvProductList.setAdapter(adapter);
    }

    @OnClick({R2.id.rlt_search, R2.id.tv_school_select, R2.id.tv_profession_select, R2.id.tv_year_select})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_school_select) {
            chooseType(0, "");
        } else if (view.getId() == R.id.tv_profession_select) {
            chooseType(1, "");
        } else if (view.getId() == R.id.tv_year_select) {
            chooseType(2, "");
        }
    }

    private void chooseType(int key, String s) {
        List<String> list = new ArrayList<>();
        if (key == 0) {
            list.add("武汉大学");
            list.add("武汉理工大学");
            list.add("华中科技大学");
            list.add("华中农业大学");
        } else if (key == 1) {
            list.add("计算机科学与技术");
            list.add("软件工程");
            list.add("信息技术");
            list.add("工业制造");
        } else if (key == 2) {
            list.add("1学年");
            list.add("2学年");
            list.add("3学年");
            list.add("4学年");
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String s = list.get(options1);
                updateFlowlayout(key,"add",s);
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


    /**
     * 更新flowLayout
     *
     * @param key     0 1 2
     * @param type    "add"  "delete"
     * @param content
     */
    public void updateFlowlayout(int key, String type, String content) {
        if (type.equals("add")) {
            mProfessionMap.put(key, content);
        } else if (type.equals("delete")) {
            mProfessionMap.remove(key);
        }
        Set<Map.Entry<Integer, String>> entries = mProfessionMap.entrySet();
        mFlowContent.setVisibility(entries.size() > 0 ? View.VISIBLE : View.GONE);

        switch (key){
            case 0:
                mTvSchool.setVisibility(type.equals("add")?View.VISIBLE:View.GONE);
                if(type.equals("add")){
                    mTvSchool.setText(content);
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
                    mTvProfession.setText(content);
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
                    mTvYear.setText(content);
                    mTvYearSelect.setTextColor(SkinCompatResources.getColor(this,R.color.ui_primary_color));
                    mTvYearSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,SkinCompatResources.getDrawable(this,R.mipmap.order_icon_xiala_select),null);
                }else{
                    mTvYearSelect.setTextColor(SkinCompatResources.getColor(this,R.color.ui_txt_normal_color));
                    mTvYearSelect.setCompoundDrawablesWithIntrinsicBounds(null,null,SkinCompatResources.getDrawable(this,R.mipmap.order_icon_xiala_unselect),null);
                }
                break;
        }
    }
}
