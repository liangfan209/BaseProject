package com.clkj.order.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.utils.Utils;
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.mvp.presenter.ProductPresenter;
import com.clkj.order.mvp.ui.ProductIview;
import com.clkj.order.mvp.ui.SchoolProfessionListActivity;
import com.clkj.order.requset.bean.AgentInfo;
import com.clkj.order.requset.bean.ProfessionInfo;
import com.clkj.order.requset.bean.SchoolInfo;
import com.clkj.order.requset.bean.SchoolProfessionInfo;
import com.clkj.order.requset.bean.SchoolProfessionRequstBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;

/**
 * 文件名：
 * 描述：院校专业详情
 * 作者：梁帆
 * 时间：2020/7/30
 * 版权：
 */
public class SchoolProfressionListFragment extends BaseFragment implements MyRefreshLayout.LayoutInterface<SchoolProfessionInfo>
        , ProductIview {


    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    private List<SchoolProfessionInfo> mlist = new ArrayList<>();
    private SchoolProfessionRequstBean mSchoolProfessionRequstBean;
    private ProductPresenter mProductPresenter;

    public static SchoolProfressionListFragment getInstance(SchoolProfessionRequstBean professionRequstBean) {
        SchoolProfressionListFragment fragment = new SchoolProfressionListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mProfessionRequstBean", professionRequstBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.laoyut_frame_layout;
    }

    @Override
    protected BasePresenter createPersenter() {
        mProductPresenter = new ProductPresenter(this);
        return mProductPresenter;
    }

    @Override
    protected void attach() {
        mSchoolProfessionRequstBean = (SchoolProfessionRequstBean) getArguments().get("mProfessionRequstBean");
        mRefreshLayout = new MyRefreshLayout<String>(this.getContext(), this);
        mRefreshLayout.setRefresh(false, true);
        mFltContent.addView(mRefreshLayout);

        mRefreshLayout.adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String school_id = mSchoolProfessionRequstBean.getSchool_id();
                SchoolProfessionInfo info = (SchoolProfessionInfo) adapter.getData().get(position);
                info.setCategory(mSchoolProfessionRequstBean.getCategory());
                if(!StringUtils.isEmpty(school_id)){
                    //专业列表
                    //从父activity去拿学校info
                    SchoolInfo schoolInfo =((SchoolProfessionListActivity)getActivity()).mSchoolInfo;
                    ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_PROFRESSION_DETAIL_ACTIVITY)
                            .withSerializable("mSchoolInfo",schoolInfo)
                            .withSerializable("mInfo",info).navigation();
                }else{
                    ProfessionInfo professionInfo =((SchoolProfessionListActivity)getActivity()).mProfessionInfo;
                    ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_PROFRESSION_DETAIL_ACTIVITY)
                            .withSerializable("mProfessionInfo",professionInfo)
                            .withSerializable("mInfo",info).navigation();
                }
            }
        });
    }

    @Override
    public BaseQuickAdapter<SchoolProfessionInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<SchoolProfessionInfo, BaseViewHolder>(R.layout.order_item_school_type2,mlist) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, SchoolProfessionInfo bean) {
                ImageView iv = helper.getView(R.id.iv_icon);
                Utils.showImage(bean.getIcons(),iv);
                helper.setText(R.id.tv_title,bean.getName());
                helper.setText(R.id.tv_content,bean.getContent());
                TextView tvLeft = helper.getView(R.id.agent_left);
                TextView tvRight = helper.getView(R.id.agent_right);
                List<AgentInfo> agent_list = bean.getAgent_list();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < agent_list.size(); i++) {
                    sb.append(agent_list.get(i).getName());
                    if(agent_list.size() != 1 && i != agent_list.size()-1){
                        sb.append(";");
                    }
                }
                tvLeft.setText(sb.toString());
                if(agent_list.size() > 0){
                    tvRight.setVisibility(View.VISIBLE);
                    if(agent_list.size() == 1){
                        tvRight.setText("1家机构为您服务");
                    }else{
                        tvRight.setText(String.format("等%s家机构为您服务",agent_list.size()+""));
                    }
                }else{
                    tvRight.setVisibility(View.GONE);
                    tvLeft.setText("暂无机构");
                }
            }
        };
    }

    @Override
    public void getSchoolProfessionListView(List<SchoolProfessionInfo> list) {
        mRefreshLayout.addData(list);
    }

    @Override
    public void getSchoolProfessionListErrorView() {
        mRefreshLayout.addData(null);
    }

    @Override
    public void loadData(int page, int pageSize) {
        String info = new Gson().toJson(mSchoolProfessionRequstBean);
        String school_id = mSchoolProfessionRequstBean.getSchool_id();
        if(!StringUtils.isEmpty(school_id)){
            //搜索专业
            mProductPresenter.getSchoolProfessionList(page,2,info);
        }else{
            //搜索学校
            mProductPresenter.getSchoolProfessionList(page,1,info);
        }


    }
}
