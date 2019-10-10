package com.service.impl;

import javax.servlet.http.HttpSession;

import com.bean.UserBean;
import com.dao.LoginDao;
import com.service.LoginService;
import com.util.ResultInfo;

public class LoginServiceImpl implements LoginService {

	@Override
	public ResultInfo loginJudge(UserBean userBean, HttpSession session, String requestURL, String background)
			throws Exception {

		return LoginDao.loginJudge(userBean, session, requestURL, background);

	}

	@Override
	public ResultInfo register(UserBean userBean, HttpSession session, String requestURI, String background)
			throws Exception {

		return LoginDao.register(userBean, session, requestURI, background);
	}

}
