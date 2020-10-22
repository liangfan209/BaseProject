package com.clkj.order.mvp.presenter;

import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.bq.comm_config_lib.request.AbstractReqeustCallback;
import com.clkj.order.mvp.ui.ProductIview;
import com.clkj.order.requset.ProductHttpReqeustImp;
import com.clkj.order.requset.bean.AppVersionBean;
import com.clkj.order.requset.bean.BannerListBean;
import com.clkj.order.requset.bean.ContractListBean;
import com.clkj.order.requset.bean.EvaluationListBean;
import com.clkj.order.requset.bean.ProductBean;
import com.clkj.order.requset.bean.ProductListBean;
import com.clkj.order.requset.bean.ProfessionListBean;
import com.clkj.order.requset.bean.ReqeusetInfo;
import com.clkj.order.requset.bean.SchoolBean;
import com.clkj.order.requset.bean.SchoolListBean;
import com.clkj.order.requset.bean.SchoolProfessionListBean;
import com.clkj.order.requset.bean.SelecterBean;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.google.gson.Gson;

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

            @Override
            public void onError(String msg) {
                super.onError(msg);
                mIView.getSchoolAllSelcterErrorView();
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
                if(bean==null || bean.getData_list() == null)
                    return;
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
                if(bean==null || bean.getData_list() == null)
                    return;
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
                if(bean==null || bean.getData_list() == null)
                    return;
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
            @Override
            public void onError(String msg) {
                super.onError(msg);
                mIView.getProductListErrorView();
            }
        });

    }


    //
    public void collectProductList(int page,String strs){
        mProductHttpReqeustImp.collectProductList(page,strs, new AbstractReqeustCallback<ProductListBean>(mIView) {
            @Override
            public void onSuccess(ProductListBean bean) {
                mIView.getProductListView(bean.getData_list());
            }
            @Override
            public void onError(String msg) {
                super.onError(msg);
                mIView.getProductListErrorView();
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

    //获取产品详情
    public void getPosterDetail(String id){
        mProductHttpReqeustImp.getPosterDetail(id, new AbstractReqeustCallback<ProductBean>(mIView) {
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

            @Override
            public void onError(String msg) {
                super.onError(msg);
                mIView.getProfessionListErrorView();
            }
        });
    }

    public void getSchoolProfessionList(int page,int type, String searchStr) {
        mProductHttpReqeustImp.getSchoolProfessionList(page,type,searchStr, new AbstractReqeustCallback<SchoolProfessionListBean>(mIView) {
            @Override
            public void onSuccess(SchoolProfessionListBean bean) {
                mIView.getSchoolProfessionListView(bean.getData_list());
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                mIView.getSchoolProfessionListErrorView();
            }
        });
    }

    public void getContractList() {
        mProductHttpReqeustImp.getContractList(new AbstractReqeustCallback<ContractListBean>(mIView) {
            @Override
            public void onSuccess(ContractListBean bean) {
                mIView.getContractListView(bean.getData_list());
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                mIView.getContractListVErrorView();
            }
        });
    }

    public void getHomeBanner(String index_banner) {
        String info = new Gson().toJson(new ReqeusetInfo(index_banner));
        mProductHttpReqeustImp.getBannerList(info,new AbstractReqeustCallback<BannerListBean>(mIView) {
            @Override
            public void onSuccess(BannerListBean bean) {
                mIView.getBannerList(index_banner,bean.getData_list());
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }

    public void updateApp() {
        mProductHttpReqeustImp.updateApp(new AbstractReqeustCallback<AppVersionBean>(mIView) {
            @Override
            public void onSuccess(AppVersionBean bean) {
                mIView.checkUpdateView(bean.getEdition_info());
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }

    public void hasCollectProduct(String id) {
        mProductHttpReqeustImp.hasCollectProduct(id,new AbstractReqeustCallback<String>(mIView) {
            @Override
            public void onSuccess(String bean) {
                ToastUtils.showToastOk(((BaseActivity)mIView),"操作成功");
                mIView.collectProductView();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }


    public void getMyEvaluation(int page) {
        mProductHttpReqeustImp.getMyEvaluation(page,new AbstractReqeustCallback<EvaluationListBean>(mIView) {
            @Override
            public void onSuccess(EvaluationListBean bean) {
                mIView.getMyEvaluationList(bean.getData_list());
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }

    public void getAllEvaluation(int page, String good_id) {
        mProductHttpReqeustImp.getAllEvaluation(page,good_id,new AbstractReqeustCallback<EvaluationListBean>(mIView) {
            @Override
            public void onSuccess(EvaluationListBean bean) {
                mIView.geEvaluationList(bean);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }

    public void searchAllEvaluation(int page, String good_id,String info) {
        mProductHttpReqeustImp.searchAllEvaluation(page,good_id,info,new AbstractReqeustCallback<EvaluationListBean>(mIView) {
            @Override
            public void onSuccess(EvaluationListBean bean) {
                mIView.geEvaluationList(bean);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }
        });
    }
}
