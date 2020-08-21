package com.bq.comm_config_lib.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bq.comm_config_lib.R;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.request.Api;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fan.baseuilibrary.utils.RoundCornersTransformation;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.HashMap;


/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/29
 * 版权：
 */
public class Utils {
    public static ClassicsHeader  createFreshHeader(Context context){
        ClassicsHeader header = new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = "下拉刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "刷新中...";
        ClassicsHeader.REFRESH_HEADER_LOADING = "刷新中...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放即可刷新";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";
        header.setAccentColor(context.getResources().getColor(R.color.color_999999));
        header.getTitleText().setTextSize(12);
        header.getLastUpdateText().setVisibility(View.GONE);
        header.setProgressBitmap(null);
        header.setEnableLastTime(false);
        header.setArrowBitmap(null);
        return header;
    }

    public static ClassicsFooter createFreshFooter(Context context){
        ClassicsFooter.REFRESH_FOOTER_LOADING="加载中...";
        ClassicsFooter footer = new ClassicsFooter(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        footer.setAccentColor(context.getResources().getColor(R.color.color_999999));
        footer.getTitleText().setTextSize(12);
        ViewGroup.LayoutParams layoutParams = footer.getProgressView().getLayoutParams();
        layoutParams.height = SizeUtils.sp2px(15);
        layoutParams.width = SizeUtils.sp2px(15);
        footer.getProgressView().setLayoutParams(layoutParams);
//        footer.setProgressBitmap(null);
        footer.setArrowBitmap(null);
        return footer;
    }

    public static String getDouble2(double value){
        value = value/100;
        return String.format("%.2f", value);
    }

    /**
     *  拆分时间
     * @param time 2020-07-06 12:46:36
     * @return
     */
    public static String getYearMonthByTime(String time){
        String splitStr = time.substring(0,7);
        return splitStr;
    }

    public static int getYearByTime(String time){
        String splitStr = time.substring(0,4);
        Integer integer = Integer.valueOf(splitStr);
        return integer;
    }


    private static long mLastClickTime;
    private static int mLastClickViewId;

    /**
     * 是否是快速点击
     *
     * @param v  点击的控件
     * @param intervalMillis  时间间期（毫秒）
     * @return  true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        int viewId = v.getId();
        long time = System.currentTimeMillis();

        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && viewId == mLastClickViewId) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
            return false;
        }
    }

    //取消键盘，取消焦点事件
    public static void cancelFocus(EditText et){
        et.clearFocus();
        KeyboardUtils.hideSoftInput(et);
    }

    public static void showImage(String path, ImageView iv){
        if(StringUtils.isEmpty(path)){
            path = "";
        }
        if(!path.contains("http")){
            path = Api.BASE_API+path;
        }
        Glide.with(iv).load(path)
                .apply(Utils.getRequestOptionRadus(iv.getContext(),2)).into(iv);
    }
    public static void showImage(String path, ImageView iv,int dp){
        if(StringUtils.isEmpty(path)){
            path = "";
        }
        if(!path.contains("http")){
            path = Api.BASE_API+path;
        }
        Glide.with(iv).load(path)
                .apply(Utils.getRequestOptionRadus(iv.getContext(),dp)).into(iv);
    }


//    public static RequestOptions getRequestOptionRadus(Context context, int dp){
//        RoundCornersTransformation transformation =
//                new RoundCornersTransformation(context,
//                        SizeUtils.dp2px(dp),
//                        RoundCornersTransformation.CornerType.ALL);
//        return new RequestOptions().transform(transformation)
//                .placeholder(R.mipmap.placeholder_small_pic)
//                .fallback(R.mipmap.placeholder_small_pic);
//    }

    public static RequestOptions getRequestOptionRadus(Context context, int dp){
        RoundCornersTransformation transformation =
                new RoundCornersTransformation(context,
                        SizeUtils.dp2px(dp),
                        RoundCornersTransformation.CornerType.ALL);
        RequestOptions requestOptions = Utils.getRequestOption();
        requestOptions.transform(transformation);
        return requestOptions;
    }


    public static RequestOptions getRequestOption() {
        return new RequestOptions()
                .placeholder(R.mipmap.placeholder_small_pic)
                .fallback(R.mipmap.placeholder_small_pic);

    }

    public static void clipBoard(Activity activity, String content){
        ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("simple text", content);
        clipboard.setPrimaryClip(clip);
        ToastUtils.showToastOk(activity,"复制成功");
    }

    public static void goCustomActivity(Activity activity, String result){
        String[] split = result.split("&");
        if(split.length<2){
            ToastUtils.showToast(activity,"格式错误");
            return;
        }
        String[] split1 = split[0].split("=");
        String[] split2 = split[1].split("=");
        if(split1[1].contains("goods")){
            ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_DETAIL_ACTIVITY)
                    .withString("mProductId",split2[1]).navigation();
        }else if(split[1].contains("poster")){
            ARouter.getInstance().build(AppArouter.ORDER_PRODUCT_DETAIL_ACTIVITY)
                    .withString("mPosterId",split2[1]).navigation();
        }else if(split1[1].contains("http")){
            ARouter.getInstance().build(AppArouter.H5_ACTIVITY)
                    .withString("h5url",split2[1]).navigation();
        }
    }

    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }


}
