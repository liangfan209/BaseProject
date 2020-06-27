package com.fan.baseuilibrary.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;

import com.fan.baseuilibrary.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;

import io.reactivex.functions.Consumer;


public class UserPicture {

    //使用照相机拍照
    public static final int TAKE_PHOTO_CODE = 1;
    //使用相册中的图片
    public static final int SELECT_PIC_CODE = 2;
    //剪切照片
    public static final int CROP_PHOTO = 3;
    public Uri mImageUri;
    public Uri mCropImgUri;
    private Activity mActivity;

    public UserPicture(Activity activity) {
        this.mActivity = activity;
    }

    public void chooseHeadImg() {
        new ChooseImg(mActivity).showBottomView(new ChooseImg.ChooseImgImpl() {
            @Override
            public void chooseImg() {
                pickPhoto();
            }

            @Override
            public void openCamera() {
                startCamera();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void startCamera() {
        if (!hasCameraSupport() || getNumberOfCameras() < 1) {
            return;
        }
        new RxPermissions(mActivity)
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            String imageName = System.currentTimeMillis() + "output_image.jpg";
                            File outputImage = new File(mActivity.getExternalCacheDir(), imageName);
                            try {
                                if (outputImage.exists()) {
                                    outputImage.delete();
                                    outputImage.createNewFile();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            mImageUri = Uri.fromFile(outputImage);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                            mActivity.startActivityForResult(intent, TAKE_PHOTO_CODE);
                        } else {
                            ToastUtils.showToast(mActivity, "没有拍照或存储权限，请打开相关权限");
                        }
                    }
                });
    }

    /**
     * 开启裁剪相片
     */
    public void startPhotoCrop(Uri imageUri) {
        //创建file文件，用于存储剪裁后的照片
        String imageName = System.currentTimeMillis() + "crop_image.jpg";
        File cropImage = new File(mActivity.getExternalCacheDir(), imageName);
        try {
            if (cropImage.exists()) {
                cropImage.delete();
            }
            cropImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCropImgUri = Uri.fromFile(cropImage);
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置源地址uri
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("circleCrop", true);
        //设置目的地址uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCropImgUri);
        //设置图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //data不需要返回,避免图片太大异常
        intent.putExtra("return-data", false);
        // no face detection
        intent.putExtra("noFaceDetection", true);
        mActivity.startActivityForResult(intent, CROP_PHOTO);
    }


    /***
     * 从相册中取图片
     */
    @SuppressLint("CheckResult")
    private void pickPhoto() {
        new RxPermissions(mActivity)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(
                        new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    mActivity.startActivityForResult(intent, SELECT_PIC_CODE);
                                } else {
                                    ToastUtils.showToast(mActivity, "没有存储权限，请打开相关权限");
                                }
                            }
                        });
}

    //判断是否存在摄像头
    public boolean hasCameraSupport() {
        PackageManager packageManager = mActivity.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public int getNumberOfCameras() {
        int numberOfCameras = Camera.getNumberOfCameras();
        return numberOfCameras;
    }
}
