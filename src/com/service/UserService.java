package com.service;

import java.util.List;
import java.util.Map;

import com.bean.UserBean;

public interface UserService {
	List<UserBean> getUserList();

	void saveUser(UserBean userBean);

	void deleteUserById(String userid);

	int getUserListCount();

	List<UserBean> getUserList(int startIndex, int endIndex);

	UserBean getUserBeanById(String userid);

	void updateuserBean(UserBean userBean) throws Exception;

	void editpass(int userid, String password, String newpassword) throws Exception;

	int getUserCount(Map<String, String> paramMap);

	List<UserBean> getUserList(Map<String, String> paramMap, int startIndex, int endIndex);

	List<UserBean> getSuperUserList(Map<String, String> paramMap, int startIndex, int endIndex);

	void freeze(String userid) throws Exception;

	void isadmin(String userid) throws Exception;
}
