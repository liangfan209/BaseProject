package com.clkj.order.mvp.ui;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.mvp.presenter.ProductPresenter;
import com.clkj.order.requset.bean.EvaluationInfo;
import com.clkj.order.requset.bean.EvaluationListBean;
import com.clkj.order.requset.bean.EvaluationSearchHttpInfo;
import com.clkj.order.requset.bean.StatisticsInfo;
import com.fan.baseuilibrary.view.CircleImageView;
import com.fan.baseuilibrary.view.FlowRadioGroup;
import com.fan.baseuilibrary.view.MyRefreshLayout;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import skin.support.widget.SkinCompatRadioButton;

/**
 * 文件名：
 * 描述： 学校列表
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_EVALUATION_ALL_ACTIVITY)
public class EvaluationAllActivity extends BaseActivity implements MyRefreshLayout.LayoutInterface<EvaluationInfo>, ProductIview{

    @BindView(R2.id.flt_content)
    FrameLayout mFltContent;
    @BindView(R2.id.tv_title)
    TextView mTvTitle;

    SkinCompatRadioButton tv0,tv1,tv2,tv3,tv4,tv5;
    FlowRadioGroup fltGp;

    private ProductPresenter mProductPresenter;
    private List<EvaluationInfo> mEvaluationInfoList = new ArrayList<>();

    @Autowired
    String good_id;
    private boolean isload = false;

    private EvaluationSearchHttpInfo searchBean;


    @Override
    protected int getContentViewLayout() {
        return R.layout.order_all_evaluation_activity;
    }

    @Override
    protected BasePresenter createPersenter() {
        return mProductPresenter = new ProductPresenter(this);
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mTvTitle.setText("全部评价");

        fltGp = findViewById(R.id.fltGp);
        tv0 = findViewById(R.id.tv_0);
        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
        tv3 = findViewById(R.id.tv_3);
        tv4 = findViewById(R.id.tv_4);
        tv5 = findViewById(R.id.tv_5);
        tv0.setChecked(true);

        fltGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                searchBean = new EvaluationSearchHttpInfo();
                if(checkedId == R.id.tv_0){
                    searchBean = null;
                }else if(checkedId == R.id.tv_1){
                    searchBean.setHas_pics(true);
                }else if(checkedId == R.id.tv_2){
                    searchBean.setHas_videos(true);
                }else if(checkedId == R.id.tv_3){
                    searchBean.setSale_guarantee(true);
                }else if(checkedId == R.id.tv_4){
                    searchBean.setGood_service(true);
                }else if(checkedId == R.id.tv_5){
                    searchBean.setCourse_all(true);
                }
                mRefreshLayout.initRefreshBoo();
//                mRefreshLayout.autoRefresh();
                loadData(1,10);
                mRefreshLayout.page++;

            }
        });

        mRefreshLayout = new MyRefreshLayout<String>(this, this);
        mRefreshLayout.setRefresh(true, true);
        mFltContent.addView(mRefreshLayout);

    }



    @Override
    public BaseQuickAdapter<EvaluationInfo, ? extends BaseViewHolder> createAdapter() {
        return new BaseQuickAdapter<EvaluationInfo, BaseViewHolder>(R.layout.order_item_all_evaluation, mEvaluationInfoList) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper, EvaluationInfo bean) {

                        //rcv_img_list
                        RecyclerView imgRcv = helper.getView(R.id.rcv_img_list);
                        CircleImageView iv = helper.getView(R.id.civ_img);
                        Utils.showImageHeader(bean.getHead_url(), iv);
                        helper.setText(R.id.tv_name, bean.getNickname());
                        helper.setText(R.id.tv_content, bean.getContent());
                        //创建一个图组列表
                        initImgRcv(imgRcv, bean.getPics());
                    }
                };
    }

    @Override
    public void geEvaluationList(EvaluationListBean bean) {
        List<EvaluationInfo> info = bean.getData_list();
        StatisticsInfo sinfo = bean.getStatistics();
        mRefreshLayout.addData(info);
        if(!isload){
            isload = true;
            if(sinfo != null){
                tv1.setText("有图("+sinfo.getPic_numbers()+")");
                tv2.setText("有视频("+sinfo.getVideo_numbers()+")");
                tv3.setText("售后保障("+sinfo.getSale_guarantee_numbers()+")");
                tv4.setText("服务周到("+sinfo.getGood_service_numbers()+")");
                tv5.setText("课程齐全("+sinfo.getCourse_all_numbers()+")");
                tv0.setText("全部 ("+bean.getTotal()+")");
            }
        }

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
    public void loadData(int page, int pageSize) {
        String info = "{}";
        if(searchBean != null){
            info = new Gson().toJson(searchBean);
            mProductPresenter.searchAllEvaluation(page,good_id,info);
        }else{
            mProductPresenter.getAllEvaluation(page,good_id);
        }
    }
}
