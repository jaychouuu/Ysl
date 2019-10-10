package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.ClassInfo;
import com.google.gson.Gson;
import com.service.ClassInfoService;
import com.util.GlobalFadace;
import com.util.PageUtil;
import com.util.ResultInfo;

public class ClassInfoServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ClassInfoServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request  the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException      if an error occurred
	 */
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
		PrintWriter out = response.getWriter();

		List<ClassInfo> classlist = new ArrayList<ClassInfo>();

		String task = request.getParameter("task");

		ClassInfoService infoService = GlobalFadace.getClassInfoService();
		if (task.equals("list")) {

			PageUtil pageUtil = new PageUtil(request);
			int rsCount = ClassInfoService.getClassListCount();
			int pageSize = 5;

			pageUtil.setPageSize(pageSize);
			pageUtil.setRsCount(rsCount);
			int pageCount = pageUtil.getPageCount();
			int currentPage = pageUtil.getCurrentPage();

			// 生成工具栏
			String pageTool = pageUtil.createPageTool(PageUtil.BbsText);

			int startIndex = (currentPage - 1) * pageSize;
			int endIndex = pageSize;

			try {
				classlist = ClassInfoService.getClassInfoList(startIndex, endIndex);

			} catch (Exception e) {

				e.printStackTrace();
			}

			request.setAttribute("pageTool", pageTool);
			request.setAttribute("classlist", classlist);

			request.getRequestDispatcher("/admin/classinfo/list.jsp").forward(request, response);

		} else if (task.equals("add")) {

			try {
				classlist = infoService.getList();

			} catch (Exception e) {

				e.printStackTrace();
			}

			request.setAttribute("classlist", classlist);
			request.getRequestDispatcher("/admin/classinfo/add.jsp").forward(request, response);

		} else if (task.equals("save")) {

			String parentid = request.getParameter("parentid");
			String classname = request.getParameter("classname");
			ClassInfo classInfo = new ClassInfo();
			classInfo.setClassname(classname);
			classInfo.setParentid(Integer.parseInt(parentid));

			ResultInfo resultInfo = new ResultInfo();

			try {
				infoService.save(classInfo);
				resultInfo.setFlag(true);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());
			}
			String jsonStr = new Gson().toJson(resultInfo);
			out.print(jsonStr);
		}

		else if (task.equals("refresh")) {

			try {

				classlist = infoService.getList();
				String jsonString = new Gson().toJson(classlist);
				out.print(jsonString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (task.equals("change")) {
			ResultInfo resultInfo = null;

			try {

				String classid = request.getParameter("classid");

				String classname = request.getParameter("classname");
				String parentid = request.getParameter("parentid");
				String oldclassid = request.getParameter("oldclassid");
				String oldclassname = request.getParameter("oldclassname");

				ClassInfo classInfo = new ClassInfo();
				ClassInfo oldclassInfo = new ClassInfo();

				classInfo.setClassid(Integer.parseInt(classid));
				classInfo.setClassname(classname);
				classInfo.setParentid(Integer.parseInt(parentid));

				oldclassInfo.setClassid(Integer.parseInt(oldclassid));
				oldclassInfo.setClassname(oldclassname);
				oldclassInfo.setParentid(Integer.parseInt(parentid));

				resultInfo = new ResultInfo();
				infoService.change(classInfo, oldclassInfo);
				resultInfo.setFlag(true);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());
			}
			String jsonStr = new Gson().toJson(resultInfo);
			out.print(jsonStr);

		} else if (task.equals("delete")) {

			String classid = request.getParameter("classid");

			String parentid = request.getParameter("parentid");

			ResultInfo resultInfo = new ResultInfo();

			try {

				infoService.delete(classid, parentid);
				resultInfo.setFlag(true);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());
			}
			String jsonStr = new Gson().toJson(resultInfo);
			out.print(jsonStr);

		}

		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
