package com.bq.order.mvp.ui;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.bq.order.mvp.ui.fragment.SchoolProfressionListFragment;
import com.bq.order.requset.bean.ProfessionInfo;
import com.bq.order.requset.bean.SchoolInfo;
import com.bq.order.requset.bean.SchoolProfessionRequstBean;
import com.bq.utilslib.AppUtils;
import com.fan.baseuilibrary.view.CircleImageView;
import com.fan.baseuilibrary.view.NoAnimationViewPager;
import com.fan.baseuilibrary.view.flycotablayout.widget.SkinSlidingTabLayout;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

/**
 * 文件名：
 * 描述： 学校详情
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_SCHOOL_PROFESSION_LIST_ACTIVITY)
public class SchoolProfessionListActivity extends BaseActivity implements ProductIview{
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
    @BindView(R2.id.tablayout)
    SkinSlidingTabLayout mTablayout;
    @BindView(R2.id.viewpager)
    NoAnimationViewPager mViewpager;

    @BindView(R2.id.rlt_expance)
    RelativeLayout mRltExpance;
    @BindView(R2.id.tv_expance)
    TextView mTvExpance;

    @BindView(R2.id.scrollview_txt)
    ScrollView mScrollviewTxt;

    private ProductPresenter mProductPresenter;

    @Autowired
    public SchoolInfo mSchoolInfo;
    @Autowired
    public ProfessionInfo mProfessionInfo;

    private boolean scollBoo = true;


    private String[] mTitles = {"高起专", "专升本", "考研"};
    private String[] catogoryStr ={"specialty","undergraduate","graduate"};
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_activity_school_detail;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        String title = mSchoolInfo == null?"专业详情":"院校详情";
        mTvTitle.setText(title);
        initView();

        for (int i = 0; i < mTitles.length; i++) {
            SchoolProfessionRequstBean requestInfo = new SchoolProfessionRequstBean(catogoryStr[i]);

            if(mSchoolInfo != null){
                requestInfo.setSchool_id(mSchoolInfo.getId());
                mFragmentList.add(SchoolProfressionListFragment.getInstance(requestInfo));
            }else if(mProfessionInfo != null){
                requestInfo.setMajor_id(mProfessionInfo.getId()+"");
                mFragmentList.add(SchoolProfressionListFragment.getInstance(requestInfo));
            }
        }
        mTablayout.setViewPager(mViewpager,mTitles,this,mFragmentList);
    }

    private void initView() {
        if(mSchoolInfo != null){
            if(!StringUtils.isEmpty(mSchoolInfo.getIcons())){
                Utils.showImage(mSchoolInfo.getIcons(),mTvIcon);
            }
            mTvName.setText(mSchoolInfo.getName());
            mTvDetail.setText("\t\t"+mSchoolInfo.getContent());
        }else if(mProfessionInfo != null){
            if(!StringUtils.isEmpty(mProfessionInfo.getIcons())){
                Utils.showImage(mProfessionInfo.getIcons(),mTvIcon);
            }
            mTvName.setText(mProfessionInfo.getName());
            mTvDetail.setText("\t\t"+mProfessionInfo.getContent());
        }


        if(mTvDetail.getText().length() > 80){
            mScrollviewTxt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return scollBoo;
                }
            });
            mRltExpance.setOnClickListener(v->{
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mScrollviewTxt.getLayoutParams();
                int height = layoutParams.height;
                if(height == AppUtils.dp2px(this,60)){
                    scollBoo = false;
                    layoutParams.height = AppUtils.dp2px(this,180);
                    mTvExpance.setText("收起详情");
                    mTvExpance.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_expansion_up,0);

                }else{
                    layoutParams.height = AppUtils.dp2px(this,60);
                    scollBoo = true;
                    mTvExpance.setText("展开详情");
                    mTvExpance.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.icon_expansion_down,0);
                    mScrollviewTxt.setScrollY(0);
                }
                mScrollviewTxt.setLayoutParams(layoutParams);
            });
        }else{
            mRltExpance.setVisibility(View.GONE);
        }

    }

}
