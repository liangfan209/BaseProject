package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.requset.bean.ProductInfo;
import com.bq.order.requset.bean.ProductSearchBean;
import com.bq.order.requset.bean.ProfessionInfo;
import com.bq.order.requset.bean.SchoolInfo;
import com.bq.order.requset.bean.SchoolProfessionInfo;
import com.bq.utilslib.AppUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.CircleImageView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 文件名：
 * 描述： 院校专业详情
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_SCHOOL_PROFRESSION_DETAIL_ACTIVITY)
public class SchoolProfessionDetailActivity extends BaseActivity implements ProductIview{
    @BindView(R2.id.iv_title_left)
    ImageView mIvTitleLeft;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    @BindView(R2.id.tv_right)
    TextView mTvRight;
    @BindView(R2.id.view_line)
    View mViewLine;
    @BindView(R2.id.rlt_title)
    RelativeLayout mRltTitle;
    @BindView(R2.id.iv_icon)
    CircleImageView mTvIcon;
    @BindView(R2.id.tv_name)
    TextView mTvName;
    @BindView(R2.id.tv_detail)
    TextView mTvDetail;
    @BindView(R2.id.tv_profession)
    TextView mTvProfession;
    @BindView(R2.id.tv_profession_detail)
    TextView mTvProfessionDetail;

    @BindView(R2.id.rv_product_list)
    RecyclerView mRvProductList;


    @BindView(R2.id.rlt_expance)
    RelativeLayout mRltExpance;
    @BindView(R2.id.tv_expance)
    TextView mTvExpance;

    @BindView(R2.id.rlt_expance1)
    RelativeLayout mRltExpance1;
    @BindView(R2.id.tv_expance1)
    TextView mTvExpance1;



    @Autowired
    SchoolInfo mSchoolInfo;
    @Autowired
    ProfessionInfo mProfessionInfo;
    @Autowired
    SchoolProfessionInfo mInfo;


    private ProductPresenter mProductPresenter;

    List<ProductInfo> mProductlist = new ArrayList<>();


    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_school_profression_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("院校专业预览");
        initView();
        ProductSearchBean searchBean = new ProductSearchBean();
        searchBean.setCategory(mInfo.getCategory());
        if(mSchoolInfo != null){
            searchBean.setSchool_id(mSchoolInfo.getId());
        }else{
            searchBean.setSchool_id(mInfo.getId());
            if(mProfessionInfo != null){
                searchBean.setMajor_id(mProfessionInfo.getId()+"");
            }else{
                searchBean.setMajor_id(mInfo.getId()+"");
            }
        }
        mProductPresenter.getSearchProductList(1,new Gson().toJson(searchBean));
    }


    private void initView() {
        if(mSchoolInfo != null){
            if(!StringUtils.isEmpty(mSchoolInfo.getIcons())){
                Utils.showImage(mSchoolInfo.getIcons(),mTvIcon);
            }
            mTvName.setText(mSchoolInfo.getName());
            mTvDetail.setText("\t\t"+mSchoolInfo.getContent());
            mTvProfession.setText(mInfo.getName());
            mTvProfessionDetail.setText("\t\t"+mInfo.getContent());
        }else{
            if(!StringUtils.isEmpty(mInfo.getIcons())){
                Utils.showImage(mInfo.getIcons(),mTvIcon);
            }
            mTvName.setText(mInfo.getName());
            mTvDetail.setText("\t\t"+mInfo.getContent());

            mTvProfession.setText(mProfessionInfo.getName());
            mTvProfessionDetail.setText("\t\t"+mProfessionInfo.getContent());
        }

        if(mTvDetail.getText().length() >80){
            mRltExpance.setVisibility(View.VISIBLE);
            mRltExpance.setOnClickListener(v->{
                String str = mTvExpance.getText().toString();
                if(str.contains("展开")){
                    mTvExpance.setText("收起详情");
                    mTvDetail.setMaxLines(1000);
                    mTvExpance.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_expansion_up,0);
                }else{
                    mTvExpance.setText("展开详情");
                    mTvDetail.setMaxLines(3);
                    mTvExpance.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_expansion_down,0);
                }

            });
        }else{
            mRltExpance.setVisibility(View.GONE);
        }

        if(mTvProfessionDetail.getText().length() >55){
            mRltExpance1.setVisibility(View.VISIBLE);
            mRltExpance1.setOnClickListener(v-> {
                String str = mTvExpance1.getText().toString();
                if(str.contains("展开")){
                    mTvExpance1.setText("收起详情");
                    mTvProfessionDetail.setMaxLines(1000);
                    mTvExpance1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_expansion_up,0);
                }else{
                    mTvExpance1.setText("展开详情");
                    mTvExpance1.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_expansion_down,0);
                    mTvProfessionDetail.setMaxLines(2);
                }
            });
        }else{
            mRltExpance1.setVisibility(View.GONE);
        }
    }

    @Override
    public void getProductListView(List<ProductInfo> list) {
        mProductlist = list;
        intProductList();
    }

    private void intProductList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvProductList.setLayoutManager(linearLayoutManager);
        BaseQuickAdapter mProductAdapter = new
                BaseQuickAdapter<ProductInfo, BaseViewHolder>(R.layout.order_item_product, mProductlist) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           ProductInfo bean) {
                        ImageView iv = helper.getView(R.id.iv_item);
                        Glide.with(iv).load(bean.getThumbnail())
                                .apply(Utils.getRequestOptionRadus(iv.getContext(),0)).into(iv);
                        helper.setText(R.id.tv_product_title,bean.getTitle());
                        helper.setText(R.id.tv_school,bean.getSchool_name());
                        helper.setText(R.id.tv_profession,bean.getMajor_name());
                        helper.setText(R.id.tv_duration,bean.getDuration());
                        helper.setText(R.id.tv_address,bean.getSchool_city());
                        helper.setText(R.id.tv_brand,bean.getBrand_name());
                        helper.setText(R.id.tv_product,bean.getProduction_name());
                        helper.setText(R.id.tv_price, AppUtils.getDouble2(bean.getSale_price()));
                        helper.setText(R.id.tv_orgamnization, bean.getAgent_name()+"为您服务");
                    }
                };
        mRvProductList.setAdapter(mProductAdapter);

        mProductAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ProductInfo info = (ProductInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_DETAIL_ACTIVITY)
                        .withString("mProductId",info.getId()).navigation();
            }
        });
    }
}
