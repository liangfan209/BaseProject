package com.bq.order.mvp.ui.hodler;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.utils.Utils;
import com.bq.order.R;
import com.bq.order.requset.bean.SchoolInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fan.baseuilibrary.view.CircleImageView;
import com.zhpan.bannerview.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class SchoolViewHolder extends BaseViewHolder<List<SchoolInfo>> {

    public SchoolViewHolder(@NonNull View itemView) {
        super(itemView);
        ImageView imageView = findView(R.id.banner_image);
    }

    @Override
    public void bindData(List<SchoolInfo> data, int position, int pageSize) {
        RecyclerView rvSchool = findView(R.id.rv_school);
        initHotSchoolView(rvSchool,data);
    }

    private void initHotSchoolView(RecyclerView mRvSchool,List<SchoolInfo> mHostSchoollist) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mRvSchool.getContext(), 2);
        mRvSchool.setLayoutManager(gridLayoutManager);
        BaseQuickAdapter<SchoolInfo, com.chad.library.adapter.base.viewholder.BaseViewHolder> mSchoolAdapter = new
                BaseQuickAdapter<SchoolInfo, com.chad.library.adapter.base.viewholder.BaseViewHolder>(R.layout.order_item_school, mHostSchoollist) {
                    @Override
                    protected void convert(@NotNull com.chad.library.adapter.base.viewholder.BaseViewHolder helper,
                                           SchoolInfo bean) {
                        helper.setText(R.id.tv_name, bean.getName());
                        helper.setText(R.id.tv_remark, bean.getContent());
                        CircleImageView iv = helper.getView(R.id.iv_icon);
                        Utils.showImage(bean.getIcons(),iv);
//                        Glide.with(iv).load(bean.getIcons())
//                                .apply(Utils.getRequestOptionRadus(iv.getContext(), 0)).into(iv);
                    }
                };
        mRvSchool.setAdapter(mSchoolAdapter);
        mSchoolAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SchoolInfo info = (SchoolInfo) adapter.getData().get(position);
                ARouter.getInstance().build(AppArouter.ORDER_SCHOOL_PROFESSION_LIST_ACTIVITY)
                        .withSerializable("mSchoolInfo",info).navigation();
            }
        });
    }
}
