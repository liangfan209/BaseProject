package com.bq.order.mvp.ui.fragment;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseFragment;
import com.bq.comm_config_lib.utils.CommSpUtils;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.R2;
import com.bq.order.mvp.presenter.ProductPresenter;
import com.bq.order.mvp.ui.ProductIview;
import com.bq.order.mvp.ui.adapter.SchoolBannerAdapter;
import com.bq.order.requset.bean.ProductSearchBean;
import com.bq.order.requset.bean.ProfessionInfo;
import com.bq.order.requset.bean.SchoolInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.utils.provinces.CityUtils;
import com.fan.baseuilibrary.view.DeletableEditText;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
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
public class HomeFragment extends BaseFragment implements  ProductIview {
    @BindView(R2.id.tv_address_location)
    TextView mTvAddressLocation;
    @BindView(R2.id.det_search)
    DeletableEditText mDetSearch;
    @BindView(R2.id.rlt_search)
    RelativeLayout mRltSearch;
    @BindView(R2.id.flt_home_banner)
    FrameLayout mFltHomeBanner;
    @BindView(R2.id.tv_hot_school)
    TextView mTvHotSchool;

//    @BindView(R2.id.rv_school)
//    RecyclerView mRvSchool;

    @BindView(R2.id.rv_news)
    RecyclerView mRvNews;
    @BindView(R2.id.tv_hot_profession)
    TextView mTvHotProfession;
    @BindView(R2.id.rv_profession)
    RecyclerView mRvProfession;
    @BindView(R2.id.smartlayout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R2.id.rlt_hot_school)
    RelativeLayout mRltHotSchool;
    @BindView(R2.id.rlt_hot_product)
    RelativeLayout mRltHotProduct;

    @BindView((R2.id.banner_school))
    BannerViewPager mBannerSchool;

    @BindView(R2.id.iv_scan_home)
    ImageView mIvScanHome;

    @BindView(R2.id.rlt_content_school)
    RelativeLayout mRltContentSchool;


    private HomeBannerFragment mHomeBannerFragment;
    private ProductPresenter mProductPresenter;
//    BaseQuickAdapter mSchoolAdapter;
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
        return R.layout.order_fragment_home;
    }

    @Override
    protected void attach() {
        CommSpUtils.saveLocaltion("武汉市");
        mTvAddressLocation.setText(CommSpUtils.getLocation());
        mHomeBannerFragment = new HomeBannerFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.flt_home_banner,mHomeBannerFragment);
        fragmentTransaction.commit();

        initHotSchoolView();

        intNewsView();
        intHotProfessionView();
        updateView();
        initEditText();

        mSmartRefreshLayout.setEnableLoadmore(false);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                updateView();
            }
        });
    }

    void updateView(){
        mProductPresenter.getHotSchool(searchStr);
        mProductPresenter.getHotProfessionList(searchStr);
    }

    private void initEditText() {
        mDetSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
//                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    jumpProductListActivity(null);
                }
                return true;
            }
        });
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



//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
//        mRvSchool.setLayoutManager(gridLayoutManager);
//        mSchoolAdapter = new
//                BaseQuickAdapter<SchoolInfo, BaseViewHolder>(R.layout.order_item_school, mHostSchoollist) {
//                    @Override
//                    protected void convert(@NotNull BaseViewHolder helper,
//                                           SchoolInfo bean) {
//                        helper.setText(R.id.tv_name,bean.getName());
//                        helper.setText(R.id.tv_remark,bean.getContent());
//                        CircleImageView iv = helper.getView(R.id.iv_icon);
//                        Glide.with(iv).load(bean.getLogo_url())
//                                .apply(Utils.getRequestOptionRadus(iv.getContext(),0)).into(iv);
//                    }
//                };
//        mRvSchool.setAdapter(mSchoolAdapter);
//
//        mSchoolAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                SchoolInfo info = (SchoolInfo) adapter.getData().get(position);
//                ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_DETAIL_ACTIVITY)
//                        .withString("mSchoolId",info.getId()).navigation();
//            }
//        });
    }

    @Override
    public void getProfessionListView(List<ProfessionInfo> list) {
        //        mRltContentSchool.setVisibility(list.size()>0?View.VISIBLE: View.GONE);
        mHostProfessionlist = list;
        mProductAdapter.setNewData(mHostProfessionlist);
        mProductAdapter.notifyDataSetChanged();
    }


    @Override
    public void getSchooListlView(List<SchoolInfo> list) {
        mRltContentSchool.setVisibility(list.size()>0?View.GONE: View.VISIBLE);
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

    private void intNewsView() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false);
        mRvNews.setLayoutManager(linearLayoutManager);

        BaseQuickAdapter adapter = new
                BaseQuickAdapter<String, BaseViewHolder>(R.layout.order_item_news, list) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder helper,
                                           String bean) {
                    }
                };
        mRvNews.setAdapter(adapter);
    }

    @Override
    public void onComplete() {
        super.onComplete();
        mSmartRefreshLayout.finishRefresh();
    }

    @OnClick({R2.id.rlt_search, R2.id.tv_hot_school, R2.id.tv_hot_profession,R2.id.tv_address_location,
            R2.id.iv_scan_home,R2.id.iv_address_location})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.tv_hot_school){
            ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_LIST_ACTIVITY).navigation();
        }else if(view.getId() == R.id.tv_hot_profession){
            //默认湖北武汉
            String serachInfo = new Gson().toJson(new ProductSearchBean(CommSpUtils.getLocation()));
            ARouter.getInstance().build(AppArouter.ORDER_PROFESSION_LIST_ACTIVITY)
                    .withString("mSearchInfo",serachInfo).navigation();
        }else if(view.getId() == R.id.rlt_search){
            jumpProductListActivity(null);
//            ARouter.getInstance().build(AppArouter.ORDER_SIGN_CONTRACT_ACTIVITY).navigation();
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
//            new RxPermissions(getActivity())
//                    .request(Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .subscribe(new Consumer<Boolean>() {
//                        @Override
//                        public void accept(Boolean aBoolean) throws Exception {
//                            if (aBoolean) {
//                                DoloadHelper.downloadApk("http://education.bq.com/resource/contract/4255_1597056309.pdf");
//                            } else {
//                                ToastUtils.showToast(getActivity(), "没有拍照或存储权限，请打开相关权限");
//                            }
//                        }
//                    });


        }else if(view.getId() == R.id.iv_address_location){
            searchStr = new Gson().toJson(new ProductSearchBean("武汉市"));
            mTvAddressLocation.setText("武汉市");
            CommSpUtils.saveLocaltion("武汉市");
            updateView();
        }
    }

    //根据搜索内容跳转到商品列表页面
    private void jumpProductListActivity(String catogory){
        //取消焦点，隐藏键盘
        Utils.cancelFocus(mDetSearch);
        //跳转到下一个页面进行搜索
        String titleSearch = mDetSearch.getText().toString();

        ProductSearchBean bean = new ProductSearchBean(CommSpUtils.getLocation());
        if(!StringUtils.isEmpty(catogory)){
            bean.setCategory(catogory);
        }

        bean.setTitle(titleSearch);
        String serachInfo = new Gson().toJson(bean);
        ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_LIST_ACTIVITY)
                .withString("mSearchInfo",serachInfo)
                .withString("mSearchName",titleSearch).navigation();
    }

    public void updateTest(String catogory){
        jumpProductListActivity(catogory);
    }
}
