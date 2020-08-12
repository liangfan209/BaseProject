package com.bq.comm_config_lib.request.dowload;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/11
 * 版权：
 */
public class DoloadHelper {
    public static void downloadApk(String url) {
        //APK的下载目录，如无特定需求不用改

        final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //下载的APK名，可自行修改
        final String fileName = "yoyo.pdf";
        //如无下载目录则创建目录
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();

        OkGo.<File>get(url)
                .execute(new FileCallback(filePath, fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                    }


                    //@Override
                    @SuppressLint("SetTextI18n")
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        //下载进度回调方法(此回调在主线程,可更新ui)
                        //布局文件添加ProgressBar  显示并将进度回调传递给进度条
//                        mProgress.setVisibility(View.VISIBLE);
//                        mProgress.setProgress((int)(progress.fraction*100));
                        //当前进度等于总进度，说明下载完成，隐藏进度条，安装apk
                        if (progress.currentSize == progress.totalSize) {
//                            mProgress.setVisibility(View.GONE);
//                            installApk();
                        }
                    }
                });
    }
}
