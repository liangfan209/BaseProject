package com.bq.order.mvp.presenter;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.bq.order.mvp.ui.ProductIview;
import com.bq.order.requset.ProductHttpReqeustImp;
import com.bq.order.requset.bean.ProductBean;
import com.bq.order.requset.bean.ProductListBean;
import com.bq.order.requset.bean.ProfessionListBean;
import com.bq.order.requset.bean.SchoolBean;
import com.bq.order.requset.bean.SchoolListBean;
import com.bq.order.requset.bean.SelecterBean;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public class ProductPresenter implements BasePresenter {
    private ProductHttpReqeustImp mProductHttpReqeustImp;
    private ProductIview mIView;

    public ProductPresenter(ProductIview IView) {
        mProductHttpReqeustImp = new ProductHttpReqeustImp();
        mIView = IView;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
    }
    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
    }

    //获取热门学校
    public void getHotSchool(String strs){
        mProductHttpReqeustImp.getHostSchoolList(strs, new AbstractReqeustCallback<SchoolListBean>(mIView) {
            @Override
            public void onSuccess(SchoolListBean bean) {
                mIView.getSchooListlView(bean.getData_list());
            }
        });
    }

    //获取搜索学校列表
    public void getSearchSchoolList(int page,String strs){
        mProductHttpReqeustImp.getSearchSchoolList(page,strs, new AbstractReqeustCallback<SchoolListBean>(mIView) {
            @Override
            public void onSuccess(SchoolListBean bean) {
                mIView.getSchooListlView(bean.getData_list());
            }
        });
    }

    //获取所有学校
    public void getSchoolAll(String strs){
        mProductHttpReqeustImp.getSchoolAll(strs,new AbstractReqeustCallback<SelecterBean>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(SelecterBean bean) {
                mIView.getSchoolAllSelcterView(bean.getData_list());
            }
        });
    }

    //获取所有专业
    public void getProfessionAll(String strs){
        mProductHttpReqeustImp.getProfessionAll(strs,new AbstractReqeustCallback<SelecterBean>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(SelecterBean bean) {
                mIView.getProfessionAllSelcterView(bean.getData_list());
            }
        });
    }

    //获取所有学年
    public void getDurationAll(String strs){
        mProductHttpReqeustImp.getDurationAll(strs,new AbstractReqeustCallback<SelecterBean>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }

            @Override
            public void onSuccess(SelecterBean bean) {
                mIView.getDurationAllSelcterView(bean.getData_list());
            }
        });
    }

    //获取热门产品
    public void getHotProfessionList(String strs){
        mProductHttpReqeustImp.getHostProfessionList(strs, new AbstractReqeustCallback<ProfessionListBean>(mIView) {
            @Override
            public void onSuccess(ProfessionListBean bean) {
                mIView.getProfessionListView(bean.getData_list());
            }
        });
    }

    //获取搜索产品列表
    public void getSearchProductList(int page,String strs){
        mProductHttpReqeustImp.getSearchProductList(page,strs, new AbstractReqeustCallback<ProductListBean>(mIView) {
            @Override
            public void onSuccess(ProductListBean bean) {
                mIView.getProductListView(bean.getData_list());
            }
        });
    }

    //获取产品详情
    public void getProductDetail(String id){
        mProductHttpReqeustImp.getProductionDetail(id, new AbstractReqeustCallback<ProductBean>(mIView) {
            @Override
            public void onSuccess(ProductBean bean) {
                mIView.getProductDetailView(bean.getGoods_info());
            }
        });
    }


    /**
     * 获取学校详情
     * @param schoolId
     */
    public void getSchoolDetail(String schoolId) {
        mProductHttpReqeustImp.getSchoolDetail(schoolId, new AbstractReqeustCallback<SchoolBean>(mIView) {
            @Override
            public void onStart() {
                mIView.showLoading();
            }
            @Override
            public void onSuccess(SchoolBean bean) {
                mIView.getSchoolDetailView(bean.getSchool_info());
            }
        });
    }

    /**
     * 获取专业列表
     * @param page
     * @param searchStr
     */
    public void getprofessionList(int page, String searchStr) {
        mProductHttpReqeustImp.getProfessionList(page,searchStr, new AbstractReqeustCallback<ProfessionListBean>(mIView) {
            @Override
            public void onSuccess(ProfessionListBean bean) {
                mIView.getProfessionListView(bean.getData_list());
            }
        });
    }
}
