package com.service.impl;

import java.util.List;
import java.util.Map;

import com.bean.UserBean;
import com.dao.UserDao;
import com.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDao();

	public List<UserBean> getUserList() {
		return userDao.getUserList();
	}

	public void saveUser(UserBean userBean) {
		String username = userBean.getUsername();
		boolean isExist = userDao.exist(username);
		if (isExist == true) {
			throw new RuntimeException("用户名已存在");
		}
		userDao.saveUser(userBean);
	}

	public void deleteUserById(String userid) {
		userDao.deleteUserById(userid);

	}

	public List<UserBean> getUserList(int startIndex, int endIndex) {
		return userDao.getUserList(startIndex, endIndex);
	}

	public int getUserListCount() {
		return userDao.getCount();
	}

	@Override
	public UserBean getUserBeanById(String userid) {

		return UserDao.getUserBeanById(userid);
	}

	@Override
	public void updateuserBean(UserBean userBean) throws Exception {
		userDao.updateuserBean(userBean);

	}

	@Override
	public void editpass(int userid, String password, String newpassword) throws Exception {
		userDao.editpass(userid, password, newpassword);

	}

	@Override
	public int getUserCount(Map<String, String> paramMap) {
		return UserDao.getUserCount(paramMap);
	}

	@Override
	public List<UserBean> getUserList(Map<String, String> paramMap, int startIndex, int endIndex) {

		return UserDao.getUserList(paramMap, startIndex, endIndex);
	}

	@Override
	public void freeze(String userid) throws Exception {

		userDao.freeze(userid);

	}

	@Override
	public List<UserBean> getSuperUserList(Map<String, String> paramMap, int startIndex, int endIndex) {
		return UserDao.getSuperUserList(paramMap, startIndex, endIndex);
	}

	@Override
	public void isadmin(String userid) throws Exception {

		userDao.isadmin(userid);

	}
}
