package com.fan.baseuilibrary.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.fan.baseuilibrary.R;
import com.fan.baseuilibrary.bean.ItemBean;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2019/3/29/029
 * 版权：
 */
public class ChooseShare {
    private Context mContext;
    private CustomPopWindow mCustomPopWindow;
    PopupWindow.OnDismissListener mOnDismissListener;


    List<ItemBean> itemBeans1 = Arrays.asList(new ItemBean[]{
            new ItemBean(R.mipmap.share_wchat, "微信", null),
            new ItemBean(R.mipmap.share_friend, "微信朋友圈", null),
            new ItemBean(R.mipmap.share_qq, "QQ", null),
            new ItemBean(R.mipmap.share_qzong, "QQ空间", null),
    });

    public interface ChooseShareImpl {
        void choosePlat(int index);
    }

    public ChooseShare(Context context) {
        this.mContext = context;
    }

    public ChooseShare(Context context, PopupWindow.OnDismissListener listener) {
        this.mContext = context;
        this.mOnDismissListener = listener;
    }

    public void showBottomView(final ChooseShareImpl callBack) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.public_pop_share_select, null);
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .size(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                .setView(contentView)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.public_dialog_inout_anim)
                .setOnDissmissListener(mOnDismissListener)
                .create()
                .showAtLocation(((Activity) mContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //处理popWindow 显示内容
        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mCustomPopWindow.dissmiss();
                if (view.getId() == R.id.tv_cancel) {
                }
            }
        };
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(listener);
        RecyclerView rcv = contentView.findViewById(R.id.rcyView);
        initRcv(rcv, callBack);
    }

    void initRcv(RecyclerView rcv, final ChooseShareImpl callBack) {
        GridLayoutManager layoutManager = new GridLayoutManager(rcv.getContext(), 4);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(layoutManager);
        BaseQuickAdapter<ItemBean, BaseViewHolder> top2Adataper =
                new BaseQuickAdapter<ItemBean, BaseViewHolder>(R.layout.item_share, itemBeans1) {
                    @Override
                    protected void convert(BaseViewHolder helper, ItemBean item) {
                        TextView tv = helper.getView(R.id.item_text);
                        tv.setTextColor(rcv.getContext().getResources().getColor(R.color.color_333333));
                        helper.setText(R.id.item_text, item.getStr());
                        helper.setBackgroundResource(R.id.item_image, item.getRes());
                    }
                };
        rcv.setAdapter(top2Adataper);
        rcv.setHasFixedSize(true);
        rcv.setNestedScrollingEnabled(false);
        top2Adataper.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                callBack.choosePlat(position + 1);
                mCustomPopWindow.dissmiss();
            }
        });
    }

    //分享内容
    public static void shareMsg(Activity activity, SHARE_MEDIA platform, int channel) {
        UMImage image = new UMImage(activity, R.mipmap.icon_logo);//资源文件
        UMWeb web =
                new UMWeb("http://www.baidu.com");
        web.setTitle("title");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("msg");//描述
        new ShareAction(activity).withMedia(web).setPlatform(platform).setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                ToastUtils.showToast(activity,throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
            }
        }).share();
    }
}
