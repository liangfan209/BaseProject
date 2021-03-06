package com.clkj.order.mvp.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.msgService.MessageBody;
import com.bq.comm_config_lib.msgService.MessageEvent;
import com.bq.comm_config_lib.msgService.MessageInter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.clkj.order.R;
import com.clkj.order.R2;
import com.clkj.order.mvp.presenter.ProductPresenter;
import com.clkj.order.mvp.ui.ProductIview;
import com.clkj.order.mvp.ui.adapter.BannerAdapter;
import com.clkj.order.mvp.ui.adapter.SchoolBannerAdapter;
import com.clkj.order.mvp.ui.hodler.NewTypeViewHolder;
import com.clkj.order.requset.bean.BannerData;
import com.clkj.order.requset.bean.BannerInfo;
import com.clkj.order.requset.bean.ProductSearchBean;
import com.clkj.order.requset.bean.ProductTypeBean;
import com.clkj.order.requset.bean.ProfessionInfo;
import com.clkj.order.requset.bean.SchoolInfo;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/29
 * 版权：
 */
@Route(path = AppArouter.ORDER_HOME_FRAGMENT)
public class HomeFragment1 extends BaseFragment implements  ProductIview {



    @BindView(R2.id.rv_profession)
    RecyclerView mRvProfession;
    @BindView(R2.id.smartlayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.iv_scan_home)
    ImageView mIvScanHome;
    @BindView(R2.id.tv_address_location)
    TextView mTvAddressLocation;
    @BindView(R2.id.det_search)
    TextView mDetSearch;
    @BindView(R2.id.rlt_search)
    RelativeLayout mRltSearch;




    BannerViewPager mBannerView;
    RecyclerView mRvProductType;
    BannerViewPager mBannerAdvertising;
    RelativeLayout mRltHotSchool;
    TextView mTvHotSchool;
    BannerViewPager mBannerSchool;
    RelativeLayout mRltContentSchool;
    TextView mTvHotProfession;
    RelativeLayout mRltProfessionNodata;




    private String[] professionStr = {"undergraduate", "specialty", "graduate", "highcost"};
    private String[] productTypeStr = {"专升本", "高起专", "考研", "高起本"};
    private int[] productTypeInts = {R.mipmap.icon_benke, R.mipmap.icon_zhuanke, R.mipmap.icon_kaoyan, R.mipmap.icon_zigezheng};
    private ProductPresenter mProductPresenter;
    BaseQuickAdapter mProductAdapter;
    List<SchoolInfo> mHostSchoollist = new ArrayList<>();
    List<ProfessionInfo> mHostProfessionlist = new ArrayList<>();

    String searchStr = new Gson().toJson(new ProductSearchBean(CommSpUtils.getLocation()));
    @Override
    protected BasePresenter createPersenter() {
        mProductPresenter = new ProductPresenter(this);
        return mProductPresenter;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.order_fragment_home1;
    }

    @Override
    protected void attach() {
        String token = CommSpUtils.getToken();
        if(!StringUtils.isEmpty(token)) {
            EventBus.getDefault().post(new MessageEvent("create_jpush",this.getActivity()));
        }
        mTvAddressLocation.setText(CommSpUtils.getLocation());
        intHotProfessionView();

//        initEditText();
        mSmartRefreshLayout.setEnableLoadmore(false);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                updateView();
            }
        });
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new MessageEvent("location", getActivity(), new MessageInter() {
            @Override
            public void callBack(MessageBody data) {
                CommSpUtils.saveLocaltion(data.getContent());
                searchStr = new Gson().toJson(new ProductSearchBean(data.getContent()));
                mTvAddressLocation.setText(data.getContent());
                updateView();
            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void providerData(String key) {
        if(key.equals("update_location")){
            searchStr = new Gson().toJson(new ProductSearchBean(CommSpUtils.getLocation()));
            mTvAddressLocation.setText(CommSpUtils.getLocation());
            updateView();
        }
    }

    @Override
    public void getBannerList(String type, List<BannerInfo> list) {
        if ("index_banner".equals(type)) {
            initHomeBanner(list);
        } else if ("index_mid".equals(type)) {
            initAdvertisingBeanner(list);
        }
    }

    private void initHomeBanner(List<BannerInfo> list) {
        BannerAdapter homeAdapter = new BannerAdapter(new NewTypeViewHolder.HolderInter() {
            @Override
            public void createVideo(StandardGSYVideoPlayer palyer) {
            }
        });
        mBannerView
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setInterval(5000)
                .setIndicatorGravity(IndicatorGravity.CENTER)
//                .setIndicatorView(10,10)
                .setIndicatorVisibility(View.VISIBLE)
                .setCanLoop(true)
                .setAdapter(homeAdapter)
                .setOnPageClickListener(position -> {
                    BannerData info = (BannerData) mBannerView.getData().get(position);
                    Utils.goCustomActivity(this.getActivity(), info.getUrl());
                }).create();
        ArrayList<BannerData> dataList = new ArrayList<BannerData>();
        for (BannerInfo bannerInfo : list) {
            dataList.add(new BannerData(bannerInfo.getId(), bannerInfo.getName(), bannerInfo.getThumbnail(),
                    bannerInfo.getUrl(), 1));
        }
        mBannerView.refreshData(dataList);
    }

    private void initAdvertisingBeanner(List<BannerInfo> list) {
        mBannerAdvertising
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setInterval(3000)
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setCanLoop(true)
                .setAdapter(new BannerAdapter())
                .setOnPageClickListener(position -> {
                    BannerData info = (BannerData) mBannerAdvertising.getData().get(position);
                    Utils.goCustomActivity(this.getActivity(), info.getUrl());
                }).create();
        ArrayList<BannerData> dataList = new ArrayList<BannerData>();
        for (BannerInfo bannerInfo : list) {
            dataList.add(new BannerData(bannerInfo.getId(), bannerInfo.getName(), bannerInfo.getThumbnail(),
                    bannerInfo.getUrl(), 1));
        }
        mBannerAdvertising.refreshData(dataList);
    }


    /**
     * 中间4个tab页
     */
    private void initRecycleView() {
        List<ProductTypeBean.ProductTypeInfo> list = new ArrayList<>();
        for (int i = 0; i < productTypeStr.length; i++) {
            list.add(new ProductTypeBean.ProductTypeInfo(productTypeInts[i], productTypeStr[i]));
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 4);
        mRvProductType.setLayoutManager(gridLayoutManager);
        BaseQuickAdapter adapter = new
                BaseQuickAdapter<ProductTypeBean.ProductTypeInfo, BaseViewHolder>(R.layout.order_item_product_type, list) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           ProductTypeBean.ProductTypeInfo bean) {
                        ImageView iv = helper.getView(R.id.iv_img);
                        iv.setBackground(getResources().getDrawable(bean.getId()));
                        helper.setText(R.id.tv_name, bean.getName());
                    }
                };
        mRvProductType.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ProductSearchBean bean = new ProductSearchBean();
                ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
                        .withString("forType", professionStr[position]).navigation();
//                parentFragment.updateTest();
            }
        });
    }

    /**
     * 在线更新
     */
    public void updateApp(){
        mProductPresenter.updateApp();
    }



    void updateView(){
        mProductPresenter.getHotSchool(searchStr);
        mProductPresenter.getHotProfessionList(searchStr);
        mProductPresenter.getHomeBanner("index_banner");
        mProductPresenter.getHomeBanner("index_mid");
    }

    private void initEditText() {
//        mDetSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEND
////                        || actionId == EditorInfo.IME_ACTION_DONE
//                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
//                    jumpProductListActivity(null);
//                }
//                return true;
//            }
//        });
    }

    private void intHotProfessionView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        mRvProfession.setLayoutManager(linearLayoutManager);
        mProductAdapter = new
                BaseQuickAdapter<ProfessionInfo, BaseViewHolder>(R.layout.order_item_profession, mHostProfessionlist) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           ProfessionInfo bean) {
                        ImageView iv = helper.getView(R.id.iv_item);
                        Utils.showImage(bean.getIcons(),iv);

                        helper.setText(R.id.tv_profession_title,bean.getName());
                        helper.setText(R.id.tv_profession_remark,bean.getContent());
                    }
                };
        mRvProfession.setAdapter(mProductAdapter);
        mProductAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ProfessionInfo info = (ProfessionInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_PROFESSION_LIST_ACTIVITY)
                        .withSerializable("mProfessionInfo",info).navigation();
            }
        });
        View topView = LinearLayout.inflate(mRvProfession.getContext(),R.layout.order_fragment_home_recycleview_top,null);
        mBannerView = topView.findViewById(R.id.banner_view);
        mRvProductType = topView.findViewById(R.id.rv_product_type);
        mBannerAdvertising = topView.findViewById(R.id.banner_advertising);
        mRltHotSchool = topView.findViewById(R.id.rlt_hot_school);
        mTvHotSchool = topView.findViewById(R.id.tv_hot_school);
        mTvHotProfession = topView.findViewById(R.id.tv_hot_profession);
        mBannerSchool = topView.findViewById(R.id.banner_school);
        mRltProfessionNodata = topView.findViewById(R.id.rlt_profession_nodata);

        mTvHotSchool.setOnClickListener(v->{
            ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_LIST_ACTIVITY).navigation();
        });
        mTvHotProfession.setOnClickListener(v->{
            String serachInfo = new Gson().toJson(new ProductSearchBean(CommSpUtils.getLocation()));
            ARouter.getInstance().build(AppArouter.ORDER_PROFESSION_LIST_ACTIVITY)
                    .withString("mSearchInfo",serachInfo).navigation();
        });

        initHotSchoolView();
        mProductAdapter.addHeaderView(topView);
        //获取所有接口
        initRecycleView();
        updateView();
    }



    private void initHotSchoolView() {
        mBannerSchool
                .setScrollDuration(600)
                .setLifecycleRegistry(getLifecycle())
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setIndicatorSlideMode(IndicatorSlideMode.WORM)
                .setIndicatorGravity(IndicatorGravity.END)
                .setCanLoop(false)
                .setAdapter(new SchoolBannerAdapter())
                .create();
    }

    @Override
    public void getProfessionListView(List<ProfessionInfo> list) {
        mRvProfession.setVisibility(list.size()>0?View.VISIBLE: View.GONE);
        mRltProfessionNodata.setVisibility(list.size()>0?View.GONE: View.VISIBLE);
        mHostProfessionlist = list;
        mProductAdapter.setNewInstance(mHostProfessionlist);
        mProductAdapter.notifyDataSetChanged();
    }


    @Override
    public void getSchooListlView(List<SchoolInfo> list) {
        mRltHotSchool.setVisibility(list.size()>0?View.VISIBLE: View.GONE);
        mHostSchoollist = list;
        List<List<SchoolInfo>> mlists = new ArrayList<>();
        if(mHostSchoollist.size() == 0)return;
        int page = mHostSchoollist.size()/4;
        if(mHostSchoollist.size()%4 == 0){
            page = mHostSchoollist.size()/4;
        }else{
            page = mHostSchoollist.size()/4+1;
        }
        for (int i = 0; i < page; i++) {
            List<SchoolInfo> indexList = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                if((i*4+j)<mHostSchoollist.size()){
                    indexList.add(mHostSchoollist.get(i*4+j));
                }else{
                    break;
                }
            }
            mlists.add(indexList);
        }
        mBannerSchool.refreshData(mlists);
    }


    @Override
    public void onComplete() {
        super.onComplete();
        mSmartRefreshLayout.finishRefresh();
    }

    @OnClick({R2.id.rlt_search, R2.id.tv_address_location,
            R2.id.iv_scan_home,R2.id.iv_address_location,R2.id.det_search})
    public void onViewClicked(View view) {

            if(view.getId() == R.id.rlt_search || view.getId() == R.id.det_search){
//            jumpProductListActivity(null);
            ARouter.getInstance().build(AppArouter.ORDER_SEARCH_ACTIVITY).navigation();

        }else if(view.getId() == R.id.tv_address_location){
            CityUtils.getInstance(this.getContext()).showPickerView(this.getContext(), new CityUtils.CityCallBack() {
                @Override
                public void getCitys(String citys) {
                    CommSpUtils.saveLocaltion(citys);
                    searchStr = new Gson().toJson(new ProductSearchBean(citys));
                    mTvAddressLocation.setText(citys);
                    updateView();
                }
            });
        }else if(view.getId() == R.id.iv_scan_home){

            if(!Utils.isFastDoubleClick(mIvScanHome,1000)){
                ARouter.getInstance().build(AppArouter.ORDER_SCAN_ACTIVITY).navigation();
            }
            }else if(view.getId() == R.id.iv_address_location){
            EventBus.getDefault().post(new MessageEvent("location", getActivity(), new MessageInter() {
                @Override
                public void callBack(MessageBody data) {
                    searchStr = new Gson().toJson(new ProductSearchBean(data.getContent()));
                    mTvAddressLocation.setText(data.getContent());
                    CommSpUtils.saveLocaltion(data.getContent());
                    updateView();
                }
            }));
        }
    }

    //根据搜索内容跳转到商品列表页面
    private void jumpProductListActivity(String catogory){
        //取消焦点，隐藏键盘
//        Utils.cancelFocus(mDetSearch);
        //跳转到下一个页面进行搜索
//        String titleSearch = mDetSearch.getText().toString();

        ProductSearchBean bean = new ProductSearchBean(CommSpUtils.getLocation());
        if(!StringUtils.isEmpty(catogory)){
            bean.setCategory(catogory);
        }

//        bean.setTitle(titleSearch);
        String serachInfo = new Gson().toJson(bean);
        ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
                .withString("mSearchInfo",serachInfo)
                .withString("mSearchName","").navigation();
    }

}
