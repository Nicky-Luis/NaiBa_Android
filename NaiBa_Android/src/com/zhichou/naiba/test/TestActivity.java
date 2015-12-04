package com.zhichou.naiba.test;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhichou.naiba.R;
import com.zhichou.naiba.base.ui.activity.BaseActivityWithTopBar;
import com.zhichou.naiba.base.utils.ToastUtil;

public class TestActivity extends BaseActivityWithTopBar {

	@ViewInject(R.id.btn_test)
	private Button testBtn;

	@Override
	public int setLayoutId() {
		return R.layout.activity_test;
	}

	@Override
	protected void setUpView() {
		testBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ToastUtil.show(getApplicationContext(), "hahaha");

			}
		});
	}
}
