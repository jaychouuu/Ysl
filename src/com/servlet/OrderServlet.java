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

import com.bean.OrderBean;
import com.bean.OrderDetail;
import com.service.OrderService;
import com.util.GlobalFadace;
import com.util.PageUtil;

public class OrderServlet extends HttpServlet {

	private OrderService orderService;

	public OrderServlet() {
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
		if (task.equals("list")) {

			this.tasklist(request, response);

		} else if (task.equals("viewDetail")) {
			this.viewDetail(request, response);

		} else if (task.equals("edit")) {
			this.edit(request, response);

		} else if (task.equals("pass")) {
			this.pass(request, response);

		} else if (task.equals("fail")) {
			this.fail(request, response);

		}
		out.flush();
		out.close();
	}

	private void fail(HttpServletRequest request, HttpServletResponse response) {

		String orderid = request.getParameter("orderid");
		try {
			orderService.tofail(orderid);
			edit(request, response);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void pass(HttpServletRequest request, HttpServletResponse response) {
		String orderid = request.getParameter("orderid");
		try {
			orderService.topass(orderid);
			edit(request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response) {

		try {
			orderService = GlobalFadace.getOrderService();

			String addtime_start = request.getParameter("addtime_start");
			String addtime_end = request.getParameter("addtime_end");
			String username = request.getParameter("username");

			Map<String, String> paramMap = new LinkedHashMap<String, String>();
			paramMap.put("addtime_start", addtime_start);
			paramMap.put("addtime_end", addtime_end);
			paramMap.put("username", username);

			// 用于下拉框的显示
			try {
				PageUtil pageUtil = new PageUtil(request);
				int rsCount = orderService.getOrderCount(paramMap);
				int pageSize = 5;

				pageUtil.setPageSize(pageSize);
				pageUtil.setRsCount(rsCount);

				int pageCount = pageUtil.getPageCount();
				int currentPage = pageUtil.getCurrentPage();

				// 生成工具栏
				String pageTool = pageUtil.createPageTool(PageUtil.BbsText);

				int startIndex = (currentPage - 1) * pageSize;
				int endIndex = pageSize;

				List<OrderBean> orderlist = orderService.getOrderList(paramMap, startIndex, endIndex);

				// 用于列表的显示

				request.setAttribute("orderlist", orderlist);
				request.setAttribute("pageTool", pageTool);

			} catch (Exception e) {
				e.printStackTrace();
			}

			request.getRequestDispatcher("/admin/order/edit.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void viewDetail(HttpServletRequest request, HttpServletResponse response) {

		OrderService orderService = GlobalFadace.getOrderService();
		try {
			String orderid = request.getParameter("orderid");
			List<OrderDetail> detaillist = orderService.getdetailList(orderid);

			request.setAttribute("detaillist", detaillist);
			request.getRequestDispatcher("/admin/order/viewDetail.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void tasklist(HttpServletRequest request, HttpServletResponse response) {

		try {
			orderService = GlobalFadace.getOrderService();

			String addtime_start = request.getParameter("addtime_start");
			String addtime_end = request.getParameter("addtime_end");
			String username = request.getParameter("username");

			Map<String, String> paramMap = new LinkedHashMap<String, String>();
			paramMap.put("addtime_start", addtime_start);
			paramMap.put("addtime_end", addtime_end);
			paramMap.put("username", username);

			// 用于下拉框的显示
			try {
				PageUtil pageUtil = new PageUtil(request);
				int rsCount = orderService.getOrderCount(paramMap);
				int pageSize = 5;

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

			request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init() throws ServletException {
		// Put your code here
	}

}
