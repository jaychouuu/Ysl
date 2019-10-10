package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.CartBean;
import com.bean.ItemBean;
import com.bean.OrderBean;
import com.bean.UserBean;
import com.google.gson.Gson;
import com.service.ItemService;
import com.service.OrderService;
import com.util.DateUtil;
import com.util.GlobalFadace;
import com.util.ResultInfo;

public class CartServlet extends HttpServlet {

	private ItemService itemService;

	/**
	 * Constructor of the object.
	 */
	public CartServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
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
		PrintWriter out = response.getWriter();

		String task = request.getParameter("task");

		if (task.equals("add")) {
			ResultInfo resultInfo = new ResultInfo();
			DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

			try {
				String itemid = request.getParameter("itemid");
				itemService = GlobalFadace.getItemService();
				ItemBean itemBean = itemService.getItemBeanByID(itemid);
				HttpSession session = request.getSession();
				CartBean cartBean = new CartBean();
				if (session.getAttribute("cartBean") != null) {
					cartBean = (CartBean) session.getAttribute("cartBean");
				}

				List<ItemBean> itemlist = cartBean.getItemlist();
				boolean isExit = false;
				for (ItemBean rowBean : itemlist) {

					if (rowBean.getItemid() == Integer.parseInt(itemid.split("_")[0])) {
						isExit = true;
						rowBean.setCount(rowBean.getCount() + 1);
						rowBean.setSumprice(rowBean.getSumprice().add(rowBean.getItemprice()));
						break;
					}

				}
				if (isExit == false) {

					itemlist.add(itemBean);

				}

				int totalCount = 0;
				BigDecimal totalPrice = new BigDecimal(0);

				for (ItemBean rowBean : itemlist) {
					int count = rowBean.getCount();
					BigDecimal itemprice = rowBean.getItemprice();
					BigDecimal sumprice = new BigDecimal(count).multiply(itemprice).setScale(2,
							BigDecimal.ROUND_HALF_UP);
					rowBean.setSumprice_str(decimalFormat.format(sumprice));
					itemBean.setSumprice(sumprice);
					totalCount += count;
					totalPrice = totalPrice.add(sumprice);

				}
				cartBean.setTotalcount(totalCount);
				cartBean.setTotalprice(totalPrice);
				String totalprice_str = decimalFormat.format(totalPrice);

				cartBean.setTotalprice_str(totalprice_str);

				session.setAttribute("cartBean", cartBean);
				resultInfo.setFlag(true);
				resultInfo.setData(cartBean);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(" ß∞‹¡À£ø");
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());

			}
			String jsonStr = new Gson().toJson(resultInfo);
			out.print(jsonStr);

		} else if (task.equals("list")) {

			response.sendRedirect(request.getContextPath() + "/front/product_summary.jsp");
		} else if (task.equals("updata")) {
			HttpSession session = request.getSession();
			String itemid = request.getParameter("itemid");
			String new_count = request.getParameter("new_count");
			CartBean cartBean = (CartBean) session.getAttribute("cartBean");
			List<ItemBean> itemlist = cartBean.getItemlist();
			ResultInfo resultInfo = new ResultInfo();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

			try {
				for (ItemBean rowBean : itemlist) {

					if (rowBean.getItemid() == Integer.parseInt(itemid.split("_")[0])) {
						cartBean.setTotalcount(
								cartBean.getTotalcount() - rowBean.getCount() + Integer.parseInt(new_count));
						rowBean.setCount(Integer.parseInt(new_count));
						BigDecimal sumprice = new BigDecimal(rowBean.getCount()).multiply(rowBean.getItemprice())
								.setScale(2, BigDecimal.ROUND_HALF_UP);
						cartBean.setTotalprice(cartBean.getTotalprice().subtract(rowBean.getSumprice()));
						cartBean.setTotalprice(cartBean.getTotalprice().add(sumprice));
						rowBean.setSumprice(sumprice);
						rowBean.setSumprice_str(decimalFormat.format(sumprice));
						dataMap.put("sumprice_str", rowBean.getSumprice_str());

					}

				}
				String totalprice_str = decimalFormat.format(cartBean.getTotalprice());
				cartBean.setTotalprice_str(totalprice_str);

				dataMap.put("cartBean", cartBean);

				session.setAttribute("cartBean", cartBean);
				resultInfo.setFlag(true);
				resultInfo.setData(dataMap);

			} catch (NumberFormatException e) {
				e.printStackTrace();
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());
			}

			String jsonStr = new Gson().toJson(resultInfo);
			out.print(jsonStr);

		} else if (task.equals("remove")) {
			HttpSession session = request.getSession();
			String itemid = request.getParameter("itemid");
			CartBean cartBean = (CartBean) session.getAttribute("cartBean");
			List<ItemBean> itemlist = cartBean.getItemlist();
			ResultInfo resultInfo = new ResultInfo();
			DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

			try {
				for (ItemBean rowBean : itemlist) {
					if (rowBean.getItemid() == Integer.parseInt(itemid.split("_")[0])) {
						cartBean.setTotalcount(cartBean.getTotalcount() - rowBean.getCount());
						cartBean.setTotalprice(cartBean.getTotalprice().subtract(rowBean.getSumprice()));
						itemlist.remove(rowBean);
						break;
					}

				}
				String totalprice_str = decimalFormat.format(cartBean.getTotalprice());
				cartBean.setTotalprice_str(totalprice_str);

				session.setAttribute("cartBean", cartBean);
				resultInfo.setFlag(true);
				resultInfo.setData(cartBean);

			} catch (NumberFormatException e) {
				e.printStackTrace();
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());
			}

			String jsonStr = new Gson().toJson(resultInfo);
			out.print(jsonStr);

		} else if (task.equals("removeall")) {
			HttpSession session = request.getSession();
			CartBean cartBean = (CartBean) session.getAttribute("cartBean");
			List<ItemBean> itemlist = cartBean.getItemlist();
			itemlist.clear();
			cartBean.setItemlist(itemlist);
			cartBean.setTotalcount(0);
			cartBean.setTotalprice(new BigDecimal(0.00));
			cartBean.setTotalprice_str("0.00");

			ResultInfo resultInfo = new ResultInfo();
			DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

			try {
				session.setAttribute("cartBean", cartBean);
				resultInfo.setFlag(true);
				resultInfo.setData(cartBean);

			} catch (NumberFormatException e) {
				e.printStackTrace();
				resultInfo.setFlag(false);
				resultInfo.setMessage(e.getMessage());
			}

			String jsonStr = new Gson().toJson(resultInfo);
			out.print(jsonStr);

		} else if (task.equals("placeorder")) {
			HttpSession session = request.getSession();
			CartBean cartBean = new CartBean();
			UserBean userBean = new UserBean();

			if (session.getAttribute("cartBean") != null) {
				cartBean = (CartBean) session.getAttribute("cartBean");

			}
			if (session.getAttribute("userBean") != null) {
				userBean = (UserBean) session.getAttribute("userBean");

			}

			OrderBean orderBean = new OrderBean();
			int startindex = 0;
			int endIndex = 99999999;
			Map<String, String> paramMap = new HashMap<String, String>();
			OrderService orderService = GlobalFadace.getOrderService();
			int orderid = orderService.getOrderList(paramMap, startindex, endIndex).get(0).getOrderid() + 1;

			orderBean.setOrderid(orderid);
			orderBean.setTotalcount(cartBean.getTotalcount());
			orderBean.setTotalprice(cartBean.getTotalprice());
			orderBean.setIn_time(DateUtil.getNowTimeHS());

			orderBean.setReceiveaddress(userBean.getAddress());
			orderBean.setReceivemail(userBean.getMail());
			orderBean.setReceivename(userBean.getTruename());
			orderBean.setReceivephone(userBean.getTelphone());
			orderBean.setUsername(userBean.getUsername());
			orderBean.setReceivecode("");

			request.setAttribute("orderBean", orderBean);
			request.getRequestDispatcher("/front/user/placeorder.jsp").forward(request, response);

		} else if (task.equals("submitorder")) {
			HttpSession session = request.getSession();
			CartBean cartBean = new CartBean();
			cartBean = (CartBean) session.getAttribute("cartBean");
			UserBean userBean = new UserBean();
			userBean = (UserBean) session.getAttribute("userBean");
			OrderService orderService = GlobalFadace.getOrderService();

			String orderid = request.getParameter("orderid");
			String receivename = request.getParameter("receivename");
			String totalcount = request.getParameter("totalcount");
			String receiveaddress = request.getParameter("receiveaddress");
			String in_time = request.getParameter("in_time");
			String receivemail = request.getParameter("receivemail");
			String receivephone = request.getParameter("receivephone");
			String sendtype = request.getParameter("sendtype");
			String paytype = request.getParameter("paytype");

			OrderBean orderBean = new OrderBean();
			orderBean.setOrderid(Integer.parseInt(orderid));
			orderBean.setReceivename(receivename);
			orderBean.setIn_time(in_time);
			orderBean.setPaytype(paytype);
			orderBean.setReceiveaddress(receiveaddress);
			orderBean.setReceivemail(receivemail);
			orderBean.setReceivephone(receivephone);
			orderBean.setSendtype(sendtype);
			orderBean.setTotalcount(Integer.parseInt(totalcount));
			orderBean.setTotalprice(cartBean.getTotalprice());
			orderBean.setUsername(userBean.getUsername());
			List<ItemBean> itemlist = cartBean.getItemlist();
			ResultInfo resultInfo = new ResultInfo();

			try {

				orderService.saveOrder(itemlist, orderBean);
				resultInfo.setFlag(true);
				session.removeAttribute("cartBean");
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
