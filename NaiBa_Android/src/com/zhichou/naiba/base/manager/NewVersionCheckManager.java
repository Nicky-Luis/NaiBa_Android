package com.zhichou.naiba.base.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.zhichou.naiba.R;
import com.zhichou.naiba.base.helper.JsonRequestCallBack;
import com.zhichou.naiba.base.helper.LoadDataHandler;
import com.zhichou.naiba.base.ui.view.CustomAlertDialog;
import com.zhichou.naiba.base.utils.NBConst;
import com.zhichou.naiba.base.utils.ToastUtil;

/**
 * 
 * @Description: 用于检测新版本
 * @Author: nicky_luis
 * @CreateDate: 2015/12/4
 * @Version: [v1.0]
 * 
 */
public class NewVersionCheckManager {
	private Context context;
	private Activity activity;
	private int localVersion = 0;

	public NewVersionCheckManager(Context context, Activity activity) {
		this.context = context;
		this.activity = activity;
		try {
			localVersion = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void checkNewVersion(final boolean showToast,
			final VersionCallBack callBack) {

		String path = NBConst.GET_LASTEST_VERSION + "?sys=1";
		HttpManager.get(path, new JsonRequestCallBack<String>(
				new LoadDataHandler<String>() {

					@Override
					public void onSuccess(JSONObject jsonResponse, String flag) {
						super.onSuccess(jsonResponse, flag);
						int status = jsonResponse
								.getInteger(NBConst.HTTP_STATUS);
						// 回调
						if (null != callBack) {
							callBack.finish();
						}

						if (status == NBConst.STATUS_SUCCESS) {
							JSONObject jResult = jsonResponse
									.getJSONObject(NBConst.HTTP_RESULT);

							int remoteVersion = jResult
									.getIntValue("version_code");
							// 版本地址
							final String versionPath = jResult
									.getString("version_path");

							if (0 != localVersion
									&& remoteVersion > localVersion) {
								final CustomAlertDialog confirmDialog = new CustomAlertDialog(
										context,
										context.getString(R.string.found_new_version),
										context.getString(R.string.confirm),
										context.getString(R.string.cancel));
								confirmDialog.show();
								confirmDialog
										.setClicklistener(new CustomAlertDialog.ClickListenerInterface() {
											@Override
											public void doConfirm() {
												Intent intent = new Intent(
														Intent.ACTION_VIEW,
														Uri.parse(versionPath));
												intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
												activity.startActivity(intent);
												confirmDialog.dismiss();
											}

											@Override
											public void doCancel() {
												confirmDialog.dismiss();
											}
										});
							} else {
								if (showToast) {
									ToastUtil.show(context,
											R.string.is_at_the_latest_version);
								}
							}

						} else {
							if (showToast) {
								ToastUtil.show(context, R.string.net_error);
							}
						}
					}

					@Override
					public void onFailure(HttpException arg0, String arg1,
							String flag) {
						super.onFailure(arg0, arg1, flag);
						if (showToast) {
							ToastUtil.show(context, R.string.net_error);
						}
						// 回调
						if (null != callBack) {
							callBack.finish();
						}
					}

				}, null));

	}

	// 回调
	public interface VersionCallBack {
		public void finish();
	}

}
