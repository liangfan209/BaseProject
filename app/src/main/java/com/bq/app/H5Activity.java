package com.bq.app;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bq.base.R;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseAcitivty;
import com.fan.baseuilibrary.view.dialog.LoadingDialog;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/28
 * 版权：
 */

@Route( path= AppArouter.H5_ACTIVITY)
public class H5Activity extends BaseAcitivty{

    private WebView mWebView;
    private TextView titleTv;
    private boolean loadBoo = true;
    LoadingDialog mLoadingDialog;

    @Autowired
    String h5url;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_webview;
    }
    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        ARouter.getInstance().inject(this);
        mWebView = findViewById(R.id.webView);
        titleTv = findViewById(R.id.tv_title);
        WebSettings settings = mWebView.getSettings();
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024 * 1024 * 8);
        settings.setJavaScriptEnabled(true);
        //是否清楚缓存
        settings.setAppCacheEnabled(false);
        settings.setAllowFileAccess(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);


        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                titleTv.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100 && loadBoo) {
                    mLoadingDialog.dismiss();
                    loadBoo = false;
                }
            }

        };
        mWebView.setWebChromeClient(wvcc);
        mWebView.loadUrl(h5url);
    }
}
