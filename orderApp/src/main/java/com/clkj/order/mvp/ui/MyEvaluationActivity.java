package com.clkj.order.mvp.ui;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.mvp.presenter.ProductPresenter;
import com.clkj.order.requset.bean.EvaluationInfo;
import com.fan.baseuilibrary.view.CircleImageView;
import com.fan.baseuilibrary.view.MyRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 文件名：
 * 描述： 学校列表
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_MY_EVALUATION_ACTIVITY)
public class MyEvaluationActivity extends BaseActivity implements MyRefreshLayout.LayoutInterface<EvaluationInfo>, ProductIview {

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;
    private String imgPath = "";
    private String name = "";

    private ProductPresenter mProductPresenter;
    private List<EvaluationInfo> mEvaluationInfoList = new ArrayList<>();

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_my_evaluation_activity;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected void attach() {
        mTvTitle.setText("我的评价");
        mRefreshLayout = new MyRefreshLayout<String>(this, this);
        mRefreshLayout.setRefresh(false, true);
        mFltContent.addView(mRefreshLayout);
        imgPath = CommSpUtils.getUserInfo().getHead_url();
        name = CommSpUtils.getUserInfo().getNick();
    }


    @Override
    public BaseQuickAdapter<EvaluationInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<EvaluationInfo, BaseViewHolder>(R.layout.order_item_my_evaluation, mEvaluationInfoList) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, EvaluationInfo bean) {
                //rcv_img_list
                RecyclerView imgRcv = helper.getView(R.id.rcv_img_list);
                CircleImageView iv = helper.getView(R.id.civ_img);
                ImageView productIv = helper.getView(R.id.iv_product);
                Utils.showImageHeader(imgPath, iv);
                helper.setText(R.id.tv_name, name);
                helper.setText(R.id.tv_content, bean.getContent());
                //标题
                helper.setText(R.id.tv_title, bean.getTitle());
                helper.setText(R.id.tv_school, bean.getSchool());
                helper.setText(R.id.tv_profession, bean.getMajor());
                Utils.showImage(bean.getShow_image(), productIv, 5);
                //创建一个图组列表
                initImgRcv(imgRcv, bean.getPics());
            }
        };
    }

    //初始化图片列表
    void initImgRcv(RecyclerView imgRcv, List<String> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        imgRcv.setLayoutManager(gridLayoutManager);
        BaseQuickAdapter adapter = new
                BaseQuickAdapter<String, BaseViewHolder>(R.layout.order_item_evaluation_img, list) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           String bean) {
                        ImageView iv = helper.getView(R.id.iv_item);
                        Utils.showImage(bean, iv, 3);
                    }
                };
        imgRcv.setAdapter(adapter);
    }

    @Override
    public void getMyEvaluationList(List<EvaluationInfo> info) {
        mRefreshLayout.addData(info);
    }

    @Override
    public void loadData(int page, int pageSize) {
//        mProductPresenter.getSearchSchoolList(page,mSearchStr);
        mProductPresenter.getMyEvaluation(page);
    }

}
