package com.fan.baseuilibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;

import com.fan.baseuilibrary.R;


public class LoadingDialog extends Dialog {
	public Context context;
	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
		this.context = context;
		setContentView(R.layout.dialog_loading);
		this.setCanceledOnTouchOutside(false);
	}
}

