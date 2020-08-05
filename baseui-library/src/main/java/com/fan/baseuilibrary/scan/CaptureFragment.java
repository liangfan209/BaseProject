package com.fan.baseuilibrary.scan;

import android.Manifest;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fan.baseuilibrary.R;
import com.fan.baseuilibrary.scan.camera.CameraManager;
import com.fan.baseuilibrary.scan.decoding.CaptureActivityHandler;
import com.fan.baseuilibrary.scan.decoding.InactivityTimer;
import com.fan.baseuilibrary.utils.ToastUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.IOException;
import java.util.Vector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.functions.Consumer;

/**
 * 自定义实现的扫描Fragment
 */
public class CaptureFragment extends Fragment implements SurfaceHolder.Callback, View.OnClickListener {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private CodeUtils.AnalyzeCallback analyzeCallback;
    private Camera camera;


    private boolean flashLight = false;
    private TextView mTvFlashlight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getActivity().getWindow().setAttributes(attrs);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        CameraManager.init(getActivity().getApplication());

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        View view = null;
        if (bundle != null) {
            int layoutId = bundle.getInt(CodeUtils.LAYOUT_ID);
            if (layoutId != -1) {
                view = inflater.inflate(layoutId, null);
            }
        }

        if (view == null) {
            view = inflater.inflate(R.layout.public_fragment_capture, null);
        }

        viewfinderView = (ViewfinderView) view.findViewById(R.id.viewfinder_view);
        surfaceView = (SurfaceView) view.findViewById(R.id.preview_view);


        View viewLine = view.findViewById(R.id.view_line);
        ImageView ivTltleLeft = view.findViewById(R.id.iv_title_left);

        ivTltleLeft.setImageResource(R.mipmap.public_ic_arrow_back_white);
        TextView tv_right = view.findViewById(R.id.tv_right);
        mTvFlashlight = view.findViewById(R.id.tv_flashlight);

        tv_right.setText("相册");
        viewLine.setVisibility(View.GONE);
        tv_right.setVisibility(View.VISIBLE);
//        tv_left.setVisibility(View.VISIBLE);
//        tv_left.setOnClickListener(this);
        ivTltleLeft.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        mTvFlashlight.setOnClickListener(this);
        surfaceHolder = surfaceView.getHolder();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        inactivityTimer.shutdown();
    }


    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        if (result == null || TextUtils.isEmpty(result.getText())) {
            if (analyzeCallback != null) {
                analyzeCallback.onAnalyzeFailed();
            }
        } else {
            if (analyzeCallback != null) {
                analyzeCallback.onAnalyzeSuccess(barcode, result.getText());
            }
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            camera = CameraManager.get().getCamera();
        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.callBack(e);
            }
            return;
        }
        if (callBack != null) {
            callBack.callBack(null);
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet, viewfinderView);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            new RxPermissions(getActivity())
                    .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                initCamera(holder);
                            } else {
                                ToastUtils.showToast(getActivity(), "没有拍照或存储权限，请打开相关权限");
                            }
                        }
                    });
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
        if (camera != null) {
            if (camera != null && CameraManager.get().isPreviewing()) {
                if (!CameraManager.get().isUseOneShotPreviewCallback()) {
                    camera.setPreviewCallback(null);
                }
                camera.stopPreview();
                CameraManager.get().getPreviewCallback().setHandler(null, 0);
                CameraManager.get().getAutoFocusCallback().setHandler(null, 0);
                CameraManager.get().setPreviewing(false);
            }
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();


    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public CodeUtils.AnalyzeCallback getAnalyzeCallback() {
        return analyzeCallback;
    }

    public void setAnalyzeCallback(CodeUtils.AnalyzeCallback analyzeCallback) {
        this.analyzeCallback = analyzeCallback;
    }

    @Nullable
    CameraInitCallBack callBack;

    @NonNull
    PhotoAlbumCallBack mPhotoAlbumCallBack;

    /**
     * Set callback for Camera check whether Camera init success or not.
     */
    public void setCameraInitCallBack(CameraInitCallBack callBack) {
        this.callBack = callBack;
    }

    public void setPhotoAlbumCallBack(@NonNull PhotoAlbumCallBack photoAlbumCallBack) {
        mPhotoAlbumCallBack = photoAlbumCallBack;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_title_left ) {
            getActivity().finish();
        } else if (v.getId() == R.id.tv_right) {
            if (analyzeCallback != null) {
                analyzeCallback.onRightClick();
            }
        } else if (v.getId() == R.id.tv_flashlight) {
            if (analyzeCallback != null) {
                Drawable topDrawable;
                if (flashLight) {
                    topDrawable = getResources().getDrawable(R.mipmap.public_flashlight_close);
                    flashLight = false;
                } else {
                    topDrawable = getResources().getDrawable(R.mipmap.public_flashlight_open);
                    flashLight = true;
                }
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                mTvFlashlight.setCompoundDrawables(null, topDrawable, null, null);
                analyzeCallback.onFlashLight(flashLight);
            }
        }
    }

    interface CameraInitCallBack {
        /**
         * Callback for Camera init result.
         *
         * @param e If is's null,means success.otherwise Camera init failed with the Exception.
         */
        void callBack(Exception e);
    }

    interface PhotoAlbumCallBack {

        void onRightClick();
    }


}
