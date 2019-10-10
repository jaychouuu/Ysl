package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.UserBean;
import com.google.gson.Gson;
import com.service.UserService;
import com.util.GlobalFadace;
import com.util.PageUtil;
import com.util.ResultInfo;

public class UserServlet extends HttpServlet {

	private UserService userService;
	private PrintWriter out;

	public UserServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);

	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request  the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException      if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		userService = GlobalFadace.getUserService();
		out = response.getWriter();
		String task = request.getParameter("task");

		if (task.equals("change")) {

			this.change(request, response);

		} else if (task.equals("form")) {
			this.updateform(request, response);
		} else if (task.equals("editpass")) {
			this.editpass(request, response);

		} else if (task.equals("editform")) {
			this.editform(request, response);
		} else if (task.equals("usermanage")) {
			this.usermanage(request, response);
		} else if (task.equals("adminmanage")) {
			this.adminmanage(request, response);
		} else if (task.equals("frezze")) {
			this.frezze(request, response);

		} else if (task.equals("frontchange")) {
			this.frontchange(request, response);
		} else if (task.equals("frontchangepass")) {
			this.frontchangepass(request, response);
		} else if (task.equals("isadmin")) {
			this.isadmin(request, response);
		}

		out.flush();
		out.close();
	}

	private void isadmin(HttpServletRequest request, HttpServletResponse response) {

		String userid = request.getParameter("userid");
		ResultInfo resultInfo = new ResultInfo();
		try {
			userService.isadmin(userid);
			resultInfo.setFlag(true);
		} catch (Exception e) {

			e.printStackTrace();
			resultInfo.setFlag(false);
			resultInfo.setMessage(e.getMessage());
		}
		String Jsonstr = new Gson().toJson(resultInfo);
		out.print(Jsonstr);

	}

	private void frezze(HttpServletRequest request, HttpServletResponse response) {

		String userid = request.getParameter("userid");
		ResultInfo resultInfo = new ResultInfo();
		try {
			userService.freeze(userid);

			resultInfo.setFlag(true);
		} catch (Exception e) {

			e.printStackTrace();
			resultInfo.setFlag(false);
			resultInfo.setMessage(e.getMessage());
		}

		String Jsonstr = new Gson().toJson(resultInfo);
		out.print(Jsonstr);

	}

	private void adminmanage(HttpServletRequest request, HttpServletResponse response) {

		try {

			userService = GlobalFadace.getUserService();

			String username = request.getParameter("username");

			Map<String, String> paramMap = new LinkedHashMap<String, String>();

			paramMap.put("username", username);

			// 用于下拉框的显示
			try {
				PageUtil pageUtil = new PageUtil(request);
				int rsCount = userService.getUserCount(paramMap);
				int pageSize = 5;

				pageUtil.setPageSize(pageSize);
				pageUtil.setRsCount(rsCount);

				int pageCount = pageUtil.getPageCount();
				int currentPage = pageUtil.getCurrentPage();

				// 生成工具栏
				String pageTool = pageUtil.createPageTool(PageUtil.BbsText);

				int startIndex = (currentPage - 1) * pageSize;
				int endIndex = pageSize;

				List<UserBean> userlist = userService.getUserList(paramMap, startIndex, endIndex);
				// 用于列表的显示

				request.setAttribute("userlist", userlist);
				request.setAttribute("pageTool", pageTool);

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.getRequestDispatcher("/admin/user/adminmanage.jsp").forward(request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void usermanage(HttpServletRequest request, HttpServletResponse response) {

		userService = GlobalFadace.getUserService();

		String username = request.getParameter("username");

		Map<String, String> paramMap = new LinkedHashMap<String, String>();

		paramMap.put("username", username);

		// 用于下拉框的显示
		try {
			PageUtil pageUtil = new PageUtil(request);
			int rsCount = userService.getUserCount(paramMap);
			int pageSize = 5;

			pageUtil.setPageSize(pageSize);
			pageUtil.setRsCount(rsCount);

			int pageCount = pageUtil.getPageCount();
			int currentPage = pageUtil.getCurrentPage();

			// 生成工具栏
			String pageTool = pageUtil.createPageTool(PageUtil.BbsText);

			int startIndex = (currentPage - 1) * pageSize;
			int endIndex = pageSize;

			List<UserBean> userlist = userService.getUserList(paramMap, startIndex, endIndex);
			// 用于列表的显示

			request.setAttribute("userlist", userlist);
			request.setAttribute("pageTool", pageTool);

			request.getRequestDispatcher("/admin/user/usermanage.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void editform(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserBean userBean = new UserBean();
		userBean = (UserBean) session.getAttribute("userBean");
		int userid = userBean.getUserid();

		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		ResultInfo resultInfo = new ResultInfo();
		System.out.println("userid" + userid);
		System.out.println("password" + password);
		System.out.println("newpassword" + newpassword);
		try {
			userService.editpass(userid, password, newpassword);
			resultInfo.setFlag(true);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setFlag(false);
			resultInfo.setMessage(e.getMessage());
		}

		String JsonStr = new Gson().toJson(resultInfo);

		out.print(JsonStr);
	}

	private void editpass(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserBean userBean = new UserBean();

		try {
			userBean = (UserBean) session.getAttribute("userBean");
			request.getRequestDispatcher("/admin/user/editpass.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void updateform(HttpServletRequest request, HttpServletResponse response) {
		UserBean userBean = new UserBean();
		HttpSession session = request.getSession();
		ResultInfo resultInfo = new ResultInfo();

		userBean = (UserBean) session.getAttribute("userBean");
		userBean.setUsername(request.getParameter("username"));
		userBean.setBirthday(request.getParameter("birthday"));
		userBean.setAddress(request.getParameter("address"));
		userBean.setUsersex(request.getParameter("usersex"));
		userBean.setTelphone(request.getParameter("telphone"));
		userBean.setMail(request.getParameter("mail"));
		userBean.setUserage(request.getParameter("userage"));
		userBean.setTruename(request.getParameter("truename"));

		try {
			userService.updateuserBean(userBean);
			resultInfo.setFlag(true);

		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setFlag(false);
			resultInfo.setMessage(e.getMessage());
		}
		String jsonStr = new Gson().toJson(resultInfo);
		out.print(jsonStr);

	}

	private void change(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserBean userBean = new UserBean();

		try {
			userBean = (UserBean) session.getAttribute("userBean");
			request.getRequestDispatcher("/admin/user/change.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void frontchange(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserBean userBean = new UserBean();

		try {
			userBean = (UserBean) session.getAttribute("userBean");
			request.getRequestDispatcher("/front/user/changeinfo.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void frontchangepass(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserBean userBean = new UserBean();

		try {
			userBean = (UserBean) session.getAttribute("userBean");
			request.getRequestDispatcher("/front/user/changepass.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
	}

}
