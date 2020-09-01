package com.bq.order.mvp.ui;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bq.comm_config_lib.configration.AppArouter;
import com.bq.order.R;
import com.fan.baseuilibrary.view.FilePreviewView;
import com.tbruyelle.rxpermissions2.RxPermissions;


@Route(path = AppArouter.ORDER_OPEN_FILE_ACTIVITY)
public class OpenFilesActivity extends Activity {
    private FilePreviewView mFileView;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_open_file);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        openFile();
                    } else {
                        Toast.makeText(this, "请在设置中开启读写权限", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openFile() {
        mFileView = (FilePreviewView) findViewById(R.id.file_view);
        mFileView.displayFile("/storage/emulated/0/123.pdf", null);
    }


    @Override
    protected void onDestroy() {
        mFileView.onStopDisplay();
        super.onDestroy();

    }
}
