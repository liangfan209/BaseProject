package com.bq.comm_config_lib.mvp.ui;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/20/020
 * 版权：
 */
public abstract class CommomMultiItemQuickAdapter<T extends MultiItemEntity, VH extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {

    public CommomMultiItemQuickAdapter(@Nullable List<T> data) {
        super(data);
        addType();
    }
    public abstract void addType();
}
