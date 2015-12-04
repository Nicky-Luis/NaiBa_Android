package com.zhichou.naiba.login.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhichou.naiba.R;
import com.zhichou.naiba.base.manager.ActivityManager;
import com.zhichou.naiba.base.ui.activity.BaseActivity;

public class LaunchActivity extends BaseActivity {

	@ViewInject(R.id.vPager)
	private ViewPager mPager;// 页卡内容
	private static final int sleepTime = 2000;

	@Override
	public int setLayoutId() {
		return R.layout.activity_launch;
	}

	@Override
	protected void setUpView() {

		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// 如果后台有程序
		if (ActivityManager.getActivityStack().size() > 1) {
			finish();
			return;
		}
		// 如果本地有数据 并且环信登录存在 自动登录
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				}
				startActivity(new Intent(LaunchActivity.this,
						LoginActivity.class));
				finish();
			}
		}).start();
	}

	@Override
	protected void loadLayout(View v) {

	}

}
