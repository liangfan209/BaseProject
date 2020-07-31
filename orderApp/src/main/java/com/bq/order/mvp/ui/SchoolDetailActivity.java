package com.bq.order.mvp.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.ui.fragment.ProductListFragment;
import com.bq.order.requset.bean.ProfessionList;
import com.bq.order.requset.bean.SchoolInfo;
import com.fan.baseuilibrary.view.NoAnimationViewPager;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinSlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

/**
 * 文件名：
 * 描述： 学校详情
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_SCHOOL_DETAIL_ACTIVITY)
public class SchoolDetailActivity extends BaseActivity {
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
    ImageView mTvIcon;
    @BindView(R2.id.tv_name)
    TextView mTvName;
    @BindView(R2.id.tv_detail)
    TextView mTvDetail;
    @BindView(R2.id.tablayout)
    SkinSlidingTabLayout mTablayout;
    @BindView(R2.id.viewpager)
    NoAnimationViewPager mViewpager;

    @Autowired
    SchoolInfo mSchoolInfo;


    private String[] mTitles = {"高起本 (14)", "专升本 (28)", "考研 (28)", "资格证 (28)"};
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_school_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        initView();
        List<ProfessionList> production_list = mSchoolInfo.getProduction_list();
        mTitles = new String[production_list.size()];
        for (int i = 0; i < production_list.size(); i++) {
            mTitles[i] = production_list.get(i).getName()+"("+production_list.get(i).getQuantity()+")";
            mFragmentList.add(ProductListFragment.getInstance(i));
        }
        mTvTitle.setText("全部院校");
        mTablayout.setViewPager(mViewpager,mTitles,this,mFragmentList);
    }

    private void initView() {
        Utils.showImage(mSchoolInfo.getLogo_url(),mTvIcon);
        mTvName.setText(mSchoolInfo.getName());
        mTvDetail.setText(mSchoolInfo.getContent());
    }

}
