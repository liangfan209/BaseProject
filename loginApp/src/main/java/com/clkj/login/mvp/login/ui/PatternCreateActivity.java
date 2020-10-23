package com.clkj.login.mvp.login.ui;

import android.text.TextUtils;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.comm_config_lib.mvp.BasePresenter;
import com.bq.comm_config_lib.mvp.ui.BaseActivity;
import com.clkj.login.R;
import com.henleylee.lockpattern.Cell;
import com.henleylee.lockpattern.CellStatus;
import com.henleylee.lockpattern.OnPatternChangedListener;
import com.henleylee.lockpattern.PatternHelper;
import com.henleylee.lockpattern.PatternLockerView;
import com.henleylee.lockpattern.style.ArrowLockerLinkedLineStyle;
import com.henleylee.lockpattern.style.RippleLockerCellStyle;

import java.util.List;

/**
 * 创建密码
 */
@Route(path = AppArouter.LOGIN_PATTERN_LOCK_ACTIVITY)
public class PatternCreateActivity extends BaseActivity {

    private static final int MIN_PASSWORD_LENGTH = 4;

    private String password;
//    private TextView tvMessage;
//    private PatternIndicatorView indicatorView;
    private PatternLockerView lockerView;


    @Override
    protected int getContentViewLayout() {
        return R.layout.login_activity_pattern_create;
    }

    @Override
    protected BasePresenter createPersenter() {
        return null;
    }

    @Override
    protected void attach() {
        lockerView = findViewById(R.id.pattern_locker_view);
        lockerView.setCellStyle(new RippleLockerCellStyle(lockerView.getDecoratorStyle()));
        lockerView.setLinkedLineStyle(new ArrowLockerLinkedLineStyle(lockerView.getDecoratorStyle()));
        lockerView.setOnPatternChangedListener(new OnPatternChangedListener() {
            @Override
            public void onPatternStart() {
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handlePatternPassword(int side, List<Cell> cells) {
        lockerView.setPatternStatus(CellStatus.ERROR);
        if (TextUtils.isEmpty(password)) {
            if (cells == null || cells.size() < 4) {
                lockerView.setPatternStatus(CellStatus.ERROR);
//                indicatorView.setSelectedCells(cells);
//                tvMessage.setTextColor(Color.RED);
//                tvMessage.setText("最少连接" + MIN_PASSWORD_LENGTH + "个点，请重新绘制");
                return;
            }
            password = PatternHelper.patternToString(side, cells);
//            indicatorView.setSelectedCells(cells);
//            tvMessage.setTextColor(Color.DKGRAY);
//            tvMessage.setText("再次绘制解锁图案");
        } else {
            if (!TextUtils.equals(password, PatternHelper.patternToString(side, cells))) {
                lockerView.setPatternStatus(CellStatus.ERROR);
//                indicatorView.setSelectedCells(cells);
//                tvMessage.setTextColor(Color.RED);
//                tvMessage.setText("与上一次绘制不一致，请重新绘制");
                return;
            }
//            indicatorView.setSelectedCells(cells);
//            Utility.savePatternPassword(this, password);
//            Utility.showToast(this, "密码设置成功");
            finish();
        }
    }

}
