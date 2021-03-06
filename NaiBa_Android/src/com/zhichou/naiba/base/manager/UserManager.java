package com.zhichou.naiba.base.manager;

import com.zhichou.naiba.base.model.UserModel;

import android.database.Cursor;

/**
 * 用户Manager
 */
public class UserManager {

	private UserModel user;

	private static UserManager userManager;

	public synchronized static UserManager getInstance() {
		if (userManager == null) {
			userManager = new UserManager();
			userManager.user = new UserModel();
		}
		return userManager;
	}

	private UserManager() {
	}

	public UserModel getUser() {

		if (null == user.getUsername() && null == user.getLogin_token()) {
			find();
		}
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	// 本地持久化
	public void saveAndUpdate() {
		clear();
		String sql = "insert into kh_user (id,username,name,kh_id,sex,phone_num,head_image,head_sub_image,birthday,signature,qr_code,login_token,iosdevice_token,company_state,address_state,email_state) "
				+ "values ('"
				+ user.getUid()
				+ "', '"
				+ user.getUsername()
				+ "', '"
				+ user.getName()
				+ "', '"
				+ user.getKh_id()
				+ "',"
				+ " '"
				+ user.getSex()
				+ "', '"
				+ user.getPhone_num()
				+ "', '"
				+ user.getCompany_name()
				+ "', '"
				+ user.getAddress()
				+ "'"
				+ ", '"
				+ user.getHead_image()
				+ "', '"
				+ user.getHead_sub_image()
				+ "', '"
				+ user.getBirthday()
				+ "'"
				+ ", '"
				+ user.getE_mail()
				+ "', '"
				+ user.getSignature()
				+ "', '"
				+ user.getQr_code()
				+ "', '"
				+ user.getLogin_token()
				+ ")";
		DBManager.getInstance().excute(sql);
	}

	// 获取本地数据
	public void find() {

		String sql = "select * from kh_user Limit 1";
		Cursor cursor = DBManager.getInstance().query(sql);
		if (cursor.moveToNext()) {
			UserModel userModel = new UserModel();
			userModel.setUid(cursor.getInt(0));
			userModel.setUsername(cursor.getString(1));
			userModel.setName(cursor.getString(2));
			userModel.setKh_id(cursor.getString(3));
			userModel.setSex(cursor.getInt(4));
			userModel.setPhone_num(cursor.getString(5));
			userModel.setCompany_name(cursor.getString(6));
			userModel.setAddress(cursor.getString(7));
			userModel.setHead_image(cursor.getString(8));
			userModel.setHead_sub_image(cursor.getString(9));
			userModel.setBirthday(cursor.getString(11));
			userModel.setE_mail(cursor.getString(12));
			userModel.setSignature(cursor.getString(13));
			userModel.setQr_code(cursor.getString(14));
			userModel.setLogin_token(cursor.getString(15));
			setUser(userModel);
		}
	}

	// 清除本地数据
	public void clear() {
		String sql = "delete from kh_user";
		DBManager.getInstance().excute(sql);
	}
}
