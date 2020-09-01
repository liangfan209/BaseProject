package com.fan.baseuilibrary.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * theFilePreview
 * 1.使用方法： 在布局中加入com.yl.ylh5container.view.SuperFileView 这个View
 * 2.在Activity中 FilePreviewView.displayFile(path,null); 设置文件的路径
 * @author AlienChao
 * @date 2019/01/28 15:04
 */
public class FilePreviewView extends FrameLayout implements TbsReaderView.ReaderCallback {
    private static String TAG = "SuperFileView";
    private TbsReaderView mTbsReaderView;
    private int saveTime = -1;
    private Context context;




    public FilePreviewView(Context context) {
        this(context, null, 0);
    }

    public FilePreviewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilePreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTbsReaderView = new TbsReaderView(context, this);
        this.addView(mTbsReaderView, new LinearLayout.LayoutParams(-1, -1));
        this.context = context;
    }


    private OnGetFilePathListener mOnGetFilePathListener;




    public void setOnGetFilePathListener(OnGetFilePathListener mOnGetFilePathListener) {
        this.mOnGetFilePathListener = mOnGetFilePathListener;
    }


    private TbsReaderView getTbsReaderView(Context context) {
        return new TbsReaderView(context, this);
    }

    /**
     * 本地文件路径必填， 缓存路径可以为null 默认取文件下的目录中的temp目录
     * @param mFilePath
     * @param mTempPath
     */
    public void displayFile(String mFilePath, String mTempPath) {
        File mFile = new File(mFilePath);
        if (mFile != null && !TextUtils.isEmpty(mFile.toString())) {

            if(TextUtils.isEmpty(mTempPath)){
                mTempPath= extractFileDirectory(mFilePath);
            }

            //加载文件
            Bundle localBundle = new Bundle();
            //    Log.d(mFile.toString());
            localBundle.putString("filePath", mFile.toString());

            localBundle.putString("tempPath",mTempPath);

            if (this.mTbsReaderView == null) {
                this.mTbsReaderView = getTbsReaderView(context);
            }else{
            }
            boolean bool = this.mTbsReaderView.preOpen(getFileType(mFile.toString()), false);
            this.mTbsReaderView.openFile(localBundle);
//            if (bool) {
//                this.mTbsReaderView.openFile(localBundle);
//            } else {
//            }
        } else {

        }

    }


    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private String getFileType(String paramString) {
        String str = "";

        if (TextUtils.isEmpty(paramString)) {
            Log.d(TAG, "paramString---->null");
            return str;
        }
        Log.d(TAG, "paramString:" + paramString);
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d(TAG, "i <= -1");
            return str;
        }


        str = paramString.substring(i + 1);
        Log.d(TAG, "paramString.substring(i + 1)------>" + str);
        return str;
    }

    public void show() {
        if (mOnGetFilePathListener != null) {
            mOnGetFilePathListener.onGetFilePath(this);
        }
    }

    /***
     * 将获取File路径的工作，“外包”出去
     */
    public interface OnGetFilePathListener {
        void onGetFilePath(FilePreviewView mSuperFileView2);
    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        // Log.e("****************************************************" + integer);
    }



    /**
     * 生成缓存目录
     * @return
     */
    private String generateCacheDirectory(){

        String bsReaderTemp =  Environment.getExternalStorageDirectory() + "/" + "TbsReaderTemp";//"/storage/emulated/0/TbsReaderTemp";
        File bsReaderTempFile = new File(bsReaderTemp);

        if (!bsReaderTempFile.exists()) {
            //   Log.d("准备创建/storage/emulated/0/TbsReaderTemp！！");
            boolean mkdir = bsReaderTempFile.mkdir();
            if (!mkdir) {
                //       Log.e("创建/storage/emulated/0/TbsReaderTemp失败！！！！！");
            }
        }
        return bsReaderTemp;
    }


    /**
     * 提取File 目录
     */
    private String extractFileDirectory(String fName){
        fName = fName.trim();
        String temp[] = fName.split("/"); /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
        String fileName = temp[temp.length - 1]; //文件名
        String fileDirectory = fName.replace(fileName, "").trim(); //获取目录

        File file = new File(fileDirectory + "temp");
        if(!file.exists()){
            boolean mkdir = file.mkdir();
            if(!mkdir){
                fileDirectory = generateCacheDirectory();
            }
        }else{
            fileDirectory = file.toString();
        }
        Log.e("jsc", "SuperFileView2-extractFileDirectory:"+fileDirectory);
        return fileDirectory;
    }


    public void onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
