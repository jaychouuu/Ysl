package com.service;

import javax.servlet.http.HttpSession;

import com.bean.UserBean;
import com.util.ResultInfo;

public interface LoginService {

	ResultInfo loginJudge(UserBean userBean, HttpSession session, String requestURL, String background)
			throws Exception;

	ResultInfo register(UserBean userBean, HttpSession session, String requestURI, String background) throws Exception;

}
