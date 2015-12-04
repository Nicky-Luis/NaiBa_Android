package com.zhichou.naiba.base.app;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhichou.naiba.base.manager.DBManager;
import com.zhichou.naiba.base.manager.UserManager;
import com.zhichou.naiba.base.utils.FileUtil;

import android.app.Application;

public class NBApplication extends Application {
	// application
	public static NBApplication application;
	public static boolean isDebug;

	public static NBApplication getInstance() {
		return application;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		application = (NBApplication) getApplicationContext();

		// 数据库初始化
		DBManager.getInstance();
		// FileUtils初始化
		FileUtil.makeDirs();
		// 初始化用户模型
		UserManager.getInstance().getUser();

		// 初始化图片加载对象
		ImageLoader.getInstance()
				.init(ImageLoaderConfiguration
						.createDefault(getApplicationContext()));
	}
}
