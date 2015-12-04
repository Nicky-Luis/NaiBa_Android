package com.zhichou.naiba.base.ui.activity;

import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.zhichou.naiba.R;
import com.zhichou.naiba.base.manager.NewVersionCheckManager;
import com.zhichou.naiba.community.ui.fragment.CommunityFragment;
import com.zhichou.naiba.discovery.ui.fragment.DiscoveryFragment;
import com.zhichou.naiba.home.ui.fragment.IndexFragment;
import com.zhichou.naiba.me.ui.fragment.PersonalFragment;

public class MainTabActivity extends BaseActivity {

	// FragmentTabHost对象
	@ViewInject(android.R.id.tabhost)
	public FragmentTabHost mTabHost;

	private LayoutInflater layoutInflater;

	private Class<?> fragmentArray[] = { IndexFragment.class,
			DiscoveryFragment.class, CommunityFragment.class,
			PersonalFragment.class };

	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_message_btn, R.drawable.tab_contact_btn,
			R.drawable.tab_me_btn };

	private String mTextviewArray[] = { "首页", "发现", "社区", "我的" };

	/**
	 * 初始化tab
	 * */
	public void initTab() {

		layoutInflater = LayoutInflater.from(this);

		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.getTabWidget().setDividerDrawable(null);
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
		}

	}

	/**
	 * 获取最新版本号
	 * */
	private void getLastVersion() {
		new NewVersionCheckManager(this, this).checkNewVersion(false, null);
	}

	/**
	 * TabItem
	 * */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);

		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		String title = "";
		switch (index) {
		case 0:
			title = getString(R.string.main_home_title);
			break;
		case 1:
			title = getString(R.string.main_discovery_title);
			break;
		case 2:
			title = getString(R.string.main_community_title);
			break;
		case 3:
			title = getString(R.string.main_me_title);
			break;

		default:
			break;
		}
		textView.setText(title);
		return view;
	}

	@Override
	public int setLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	protected void loadLayout(View v) {

	}

	@Override
	protected void setUpView() {
		// 初始化tab
		initTab();
		// 获取最新版本
		getLastVersion();
	}

	/**
	 * 重写返回操作
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
}
