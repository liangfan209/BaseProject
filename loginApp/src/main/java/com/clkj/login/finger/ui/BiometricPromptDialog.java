package com.clkj.login.finger.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clkj.login.R;
import com.clkj.login.finger.utils.BiUIUtils;
import com.clkj.login.finger.utils.ViUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;


/**
 * Created by wuchao
 */
public class BiometricPromptDialog extends DialogFragment {

    public static final int STATE_NORMAL = 1;
    public static final int STATE_FAILED = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_SUCCEED = 4;
    public static final int STATE_ERROR_TIMES = 5;
    private TextView mStateTv;
    private TextView mCancelBtn;
    private TextView mInfoBtn;
    private ImageView fingerPrintIV;
    private Activity mActivity;
    private OnBiometricPromptDialogActionCallback mDialogActionCallback;
    private FingerDrawableBg fingerDrawableBg;

    public interface OnBiometricPromptDialogActionCallback {
        void onDialogDismiss();
        void onUsePassword();
        void onCancel();
    }

    public static BiometricPromptDialog newInstance() {
        BiometricPromptDialog dialog = new BiometricPromptDialog();
        return dialog;
    }

    public void setOnBiometricPromptDialogActionCallback(OnBiometricPromptDialogActionCallback callback) {
        mDialogActionCallback = callback;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupWindow(getDialog().getWindow());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_layout_biometric_prompt_dialog, container);

        RelativeLayout rootView = view.findViewById(R.id.root_view);
        rootView.setClickable(false);

        mStateTv = view.findViewById(R.id.state_tv);
        mCancelBtn = view.findViewById(R.id.cancel_btn);
        mInfoBtn = view.findViewById(R.id.info_tv);
        fingerPrintIV = view.findViewById(R.id.iv_finger_print);
        fingerDrawableBg = new FingerDrawableBg(mActivity, BiUIUtils.dp2px(mActivity, 61),BiUIUtils.dp2px(mActivity, 61));

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDialogActionCallback != null) {
                    mDialogActionCallback.onCancel();
                }
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(R.color.bg_biometric_prompt_dialog);
        }
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (mDialogActionCallback != null) {
            mDialogActionCallback.onDialogDismiss();
        }
    }

    private void setupWindow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.dimAmount = 0;
            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(lp);
            window.setBackgroundDrawableResource(R.color.bg_biometric_prompt_dialog);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    public void setState(int state) {
        switch (state) {
            case STATE_NORMAL:
            default:
                mStateTv.setTextColor(ContextCompat.getColor(mActivity, R.color.text_quaternary));
                mStateTv.setText(mActivity.getString(R.string.biometric_dialog_state_normal));
                break;
            case STATE_FAILED:
                fingerDrawableBg.updateBgColor(ContextCompat.getColor(mActivity, R.color.iv_yellow));
                fingerPrintIV.setBackground(fingerDrawableBg);
                mStateTv.setText(mActivity.getString(R.string.biometric_dialog_state_failed));
                ViUtils.vibrate(mActivity, 50);
                break;
            case STATE_ERROR:
                fingerDrawableBg.updateBgColor(ContextCompat.getColor(mActivity, R.color.iv_yellow));
                fingerPrintIV.setBackground(fingerDrawableBg);
                mStateTv.setText(mActivity.getString(R.string.biometric_dialog_state_error));
                mInfoBtn.setVisibility(View.INVISIBLE);
                mCancelBtn.setText("确认");
                dismiss();
//                ViUtils.vibrate(mActivity, 50);
                break;
            case STATE_ERROR_TIMES:

            case STATE_SUCCEED:
                fingerDrawableBg.updateBgColor(ContextCompat.getColor(mActivity, R.color.iv_blue));
                fingerPrintIV.setBackground(fingerDrawableBg);
                mStateTv.setText(mActivity.getString(R.string.biometric_dialog_state_succeeded));
                mStateTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 500);
                break;
        }
    }

}
