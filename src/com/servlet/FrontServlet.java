package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.ClassInfo;
import com.bean.ItemBean;
import com.bean.OrderBean;
import com.bean.UserBean;
import com.service.ClassInfoService;
import com.service.ItemService;
import com.service.OrderService;
import com.util.GlobalFadace;
import com.util.PageUtil;

public class FrontServlet extends HttpServlet {

	public FrontServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
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
		PrintWriter out = response.getWriter();
		String task = request.getParameter("task");
		if (task.equals("index")) {
			ClassInfoService classInfoService = GlobalFadace.getClassInfoService();
			List<ClassInfo> classlist = new ArrayList<ClassInfo>();
			try {
				classlist = classInfoService.getList();
				request.getSession().setAttribute("classlist", classlist);

				try {
					classlist = classInfoService.getList();

					String classid = request.getParameter("classid");

					String keyWord = request.getParameter("keyWord");

					Map<String, String> paramMap = new LinkedHashMap<String, String>();

					paramMap.put("item_class", classid);
					paramMap.put("keyWord", keyWord);

					ItemService itemService = GlobalFadace.getItemService();

					PageUtil pageUtil = new PageUtil(request);
					int rsCount = itemService.getItemCount(paramMap);
					int pageSize = 10;

					pageUtil.setPageSize(pageSize);
					pageUtil.setRsCount(rsCount);

					int pageCount = pageUtil.getPageCount();
					int currentPage = pageUtil.getCurrentPage();

					// 生成工具栏
					String pageTool = pageUtil.createPageTool(PageUtil.BbsText);

					int startIndex = (currentPage - 1) * pageSize;
					int endIndex = pageSize;

					List<ItemBean> itemlist = itemService.getItemList(paramMap, startIndex, endIndex);

					// 用于列表的显示

					request.setAttribute("itemlist", itemlist);

					request.setAttribute("classid", classid);
					request.setAttribute("keyWord", keyWord);
					request.setAttribute("pageTool", pageTool);

				} catch (Exception e) {
					e.printStackTrace();
				}

				request.getRequestDispatcher("/front/front_index.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (task.equals("product")) {
			ClassInfoService classInfoService = GlobalFadace.getClassInfoService();
			List<ClassInfo> classlist = new ArrayList<ClassInfo>();
			try {
				classlist = classInfoService.getList();

				String classid = request.getParameter("classid");
				String classname = "???";
				String classname1 = "???";
				String keyWord = request.getParameter("keyWord");

				Map<String, String> paramMap = new LinkedHashMap<String, String>();

				paramMap.put("item_class", classid);
				paramMap.put("keyWord", keyWord);

				ItemService itemService = GlobalFadace.getItemService();

				for (ClassInfo classinfo : classlist) {
					if (classinfo.getClassid() == Integer.parseInt(classid.split("_")[1])) {
						classname = classinfo.getClassname();
						for (ClassInfo classinfo1 : classinfo.getChildList()) {
							if (classinfo1.getClassid() == Integer.parseInt(classid.split("_")[0])) {
								classname1 = classinfo1.getClassname();
							}
						}

					}

				}

				PageUtil pageUtil = new PageUtil(request);
				int rsCount = itemService.getItemCount(paramMap);
				int pageSize = 10;

				pageUtil.setPageSize(pageSize);
				pageUtil.setRsCount(rsCount);

				int pageCount = pageUtil.getPageCount();
				int currentPage = pageUtil.getCurrentPage();

				// 生成工具栏
				String pageTool = pageUtil.createPageTool(PageUtil.BbsText);

				int startIndex = (currentPage - 1) * pageSize;
				int endIndex = pageSize;

				List<ItemBean> itemlist = itemService.getItemList(paramMap, startIndex, endIndex);

				// 用于列表的显示

				request.setAttribute("itemlist", itemlist);
				request.setAttribute("classname", classname);
				request.setAttribute("classname1", classname1);
				request.setAttribute("classid", classid);
				request.setAttribute("keyWord", keyWord);
				request.setAttribute("pageTool", pageTool);

				request.getRequestDispatcher("/front/products.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (task.equals("userindex")) {

			ClassInfoService classInfoService = GlobalFadace.getClassInfoService();
			List<ClassInfo> classlist = new ArrayList<ClassInfo>();
			try {
				classlist = classInfoService.getList();
				request.getSession().setAttribute("classlist", classlist);
				request.getRequestDispatcher("/front/user/front_index.jsp").forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (task.equals("orderlist")) {

			OrderService orderService;

			try {
				orderService = GlobalFadace.getOrderService();
				HttpSession session = request.getSession();
				String addtime_start = request.getParameter("addtime_start");
				String addtime_end = request.getParameter("addtime_end");
				UserBean userbean = (UserBean) session.getAttribute("userBean");
				String username = userbean.getUsername();
				Map<String, String> paramMap = new LinkedHashMap<String, String>();
				paramMap.put("addtime_start", addtime_start);
				paramMap.put("addtime_end", addtime_end);
				paramMap.put("username", username);

				// 用于下拉框的显示
				try {
					PageUtil pageUtil = new PageUtil(request);
					int rsCount = orderService.getOrderCount(paramMap);
					int pageSize = 10000;

					pageUtil.setPageSize(pageSize);
					pageUtil.setRsCount(rsCount);

					int pageCount = pageUtil.getPageCount();
					int currentPage = pageUtil.getCurrentPage();

					// 生成工具栏
					String pageTool = pageUtil.createPageTool(PageUtil.BbsText);

					int startIndex = (currentPage - 1) * pageSize;
					int endIndex = pageSize;

					List<OrderBean> orderlist = orderService.getOrderList(paramMap, startIndex, endIndex);

					if (orderlist != null) {

						int max_orderID = orderlist.get(0).getOrderid();
						request.setAttribute("max_orderID", max_orderID);
					}
					// 用于列表的显示

					request.setAttribute("orderlist", orderlist);
					request.setAttribute("pageTool", pageTool);

				} catch (Exception e) {
					e.printStackTrace();
				}

				request.getRequestDispatcher("/front/user/orderlist.jsp").forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}

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
