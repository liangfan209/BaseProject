package com.clkj.login.mvp.login.ui;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.clkj.login.R;
import com.clkj.login.finger.BiometricPromptManager;
import com.henleylee.lockpattern.Cell;
import com.henleylee.lockpattern.CellStatus;
import com.henleylee.lockpattern.OnPatternChangedListener;
import com.henleylee.lockpattern.PatternHelper;
import com.henleylee.lockpattern.PatternIndicatorView;
import com.henleylee.lockpattern.PatternLockerView;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/22
 * 版权：
 */
@Route(path = AppArouter.LOGIN_FINGER_LOCK_ACTIVITY)
public class FingerLockActivity extends BaseActivity implements LoginBaseIView {


    private static final int MAX_RETRY_COUNT = 4;

    private int retryCount = MAX_RETRY_COUNT;
    private String password;
    private TextView tvMessage;
    private PatternIndicatorView indicatorView;
    private PatternLockerView lockerView;
    PatternLockerView patternLockerView;

    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_finger_lock;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        patternLockerView = findViewById(R.id.pattern_locker_view);
        patternLockerView.setOnPatternChangedListener(new OnPatternChangedListener() {
            @Override
            public void onPatternStart() {
//                indicatorView.setSelectedCells(null);
            }

            @Override
            public void onPatternChange(PatternLockerView view, List<Cell> cells) {

            }

            @Override
            public void onPatternComplete(PatternLockerView view, List<Cell> cells) {
                handlePatternPassword(view.getSide(), cells);
            }

            @Override
            public void onPatternClear() {

            }
        });

        findViewById(R.id.tv_finger).setOnClickListener(v->{
            gotoFingerPrint();
        });
    }

    private void handlePatternPassword(int side, List<Cell> cells) {
        if (!TextUtils.equals(password, PatternHelper.patternToString(side, cells))) {
            retryCount--;
            lockerView.setPatternStatus(CellStatus.ERROR);
            indicatorView.setSelectedCells(cells);
            tvMessage.setTextColor(Color.RED);
            if (retryCount > 0) {
                tvMessage.setText("密码错误，还可以再输入" + retryCount + "次");
            } else {
                retryCount = MAX_RETRY_COUNT;
                tvMessage.setText("密码错误，请重新输入");
            }
            return;
        }
        indicatorView.setSelectedCells(cells);
        finish();
    }

    private BiometricPromptManager mManager;
    private void gotoFingerPrint() {
        mManager = BiometricPromptManager.from(this);
        if (mManager.isBiometricPromptEnable()) {
            mManager.authenticate(new BiometricPromptManager.OnBiometricIdentifyCallback() {
                @Override
                public void onUsePassword() {
                    Toast.makeText(FingerLockActivity.this, "指纹验证失败次数过多，稍后重试！", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSucceeded() {
                    Toast.makeText(FingerLockActivity.this, "指纹登录已开启！", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailed() {

                }

                @Override
                public void onError(int code, String reason) {

                }

                @Override
                public void onCancel() {

                }
            });

        }
    }

}
