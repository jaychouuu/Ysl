package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.UserBean;
import com.google.gson.Gson;
import com.service.LoginService;
import com.util.GlobalFadace;
import com.util.ResultInfo;

public class LoginServlet extends HttpServlet {

	private PrintWriter out;
	private LoginService loginService;
	private UserBean userBean;
	private HttpSession session;

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		out = response.getWriter();
		String task = request.getParameter("task");
		loginService = GlobalFadace.getLoginService();
		userBean = new UserBean();

		if (task.equals("login")) {

			this.login(request, response);

		} // login操作
		else if (task.equals("logout")) {
			session.removeAttribute("userBean");
			String loginURL = request.getContextPath() + "/admin/Login.jsp";
			response.sendRedirect(loginURL);

		} else if (task.equals("logoutfront")) {
			session.removeAttribute("userBean");
			String loginURL = request.getContextPath() + "/index.jsp";
			response.sendRedirect(loginURL);
		} else if (task.equals("register")) {

			this.register(request, response);

		}
		out.flush();
		out.close();
	}

	private void register(HttpServletRequest request, HttpServletResponse response) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String validata_code = request.getParameter("validata_code");
		String background = request.getParameter("background");

		userBean.setUsername(username);
		userBean.setPassword(password);
		String requestURI = request.getRequestURI();

		session = request.getSession();
		String rand = String.valueOf(session.getAttribute("rand"));
		ResultInfo resultInfo = new ResultInfo();

		if (username == null || password == null || validata_code == null) {

			resultInfo.setFlag(false);
			resultInfo.setMessage("用户名或者密码或者验证码为空");

		} else {
			if (!validata_code.equalsIgnoreCase(rand)) {
				resultInfo.setFlag(false);
				resultInfo.setMessage("验证码出错");
			} else {
				try {
					resultInfo = loginService.register(userBean, session, requestURI, background);
				} catch (Exception e) {
					e.printStackTrace();
					resultInfo.setFlag(false);
					resultInfo.setMessage(e.getMessage());

				}

			}

		} // 验证
		String jsonStr = new Gson().toJson(resultInfo);
		out.print(jsonStr);

	}

	private void login(HttpServletRequest request, HttpServletResponse response) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String validata_code = request.getParameter("validata_code");
		String background = request.getParameter("background");

		userBean.setUsername(username);
		userBean.setPassword(password);
		String requestURI = request.getRequestURI();

		session = request.getSession();
		String rand = String.valueOf(session.getAttribute("rand"));
		ResultInfo resultInfo = new ResultInfo();

		if (username == null || password == null || validata_code == null) {

			resultInfo.setFlag(false);
			resultInfo.setMessage("用户名或者密码或者验证码为空");

		} else {
			if (!validata_code.equalsIgnoreCase(rand)) {
				resultInfo.setFlag(false);
				resultInfo.setMessage("验证码出错");
			} else {
				try {
					resultInfo = loginService.loginJudge(userBean, session, requestURI, background);
				} catch (Exception e) {
					e.printStackTrace();
					resultInfo.setFlag(false);
					resultInfo.setMessage(e.getMessage());

				}

			}

		} // 验证
		String jsonStr = new Gson().toJson(resultInfo);
		out.print(jsonStr);

	}

	public void init() throws ServletException {
		// Put your code here
	}

}
