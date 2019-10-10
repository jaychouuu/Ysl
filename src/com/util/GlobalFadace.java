package com.util;

import java.io.PrintWriter;

import com.service.ClassInfoService;
import com.service.ItemService;
import com.service.LoginService;
import com.service.OrderService;
import com.service.UserService;
import com.service.impl.ClassInfoServiceImpl;
import com.service.impl.ItemServiceImpl;
import com.service.impl.LoginServiceImpl;
import com.service.impl.OrderServiceImpl;
import com.service.impl.UserServiceImpl;

public class GlobalFadace {
	/**
	 * 构造方法私有化，防止其他地方的代码使用new的方式来创建对象.
	 */
	private GlobalFadace() {

	}

	public static UserService getUserService() {
		UserService userService = new UserServiceImpl();
		return userService;
	}

	public static OrderService getOrderService() {
		OrderService orderService = new OrderServiceImpl();
		return orderService;

	}

	public static ClassInfoService getClassInfoService() {

		ClassInfoService classInfoService = new ClassInfoServiceImpl();
		return classInfoService;

	}

	public static LoginService getLoginService() {

		LoginService loginService = new LoginServiceImpl();
		return loginService;

	}

	public static void printErrorMsg(PrintWriter out, String errorMsg) {
		out.println("<script language='javascript'>");
		out.println("window.alert('" + errorMsg + "');");
		out.println("window.history.back();");
		out.println();
		out.println();
		out.println("</script>");

	}

	public static void printSuccMsg(PrintWriter out, String succMsg, String redirectURL) {
		out.println("<script language='javascript'>");
		if (succMsg != null && !succMsg.equals("")) {
			out.println("window.alert('" + succMsg + "');");
		}
		out.println("window.location.href = '" + redirectURL + "';");
		out.println();
		out.println();
		out.println("</script>");

	}

	public static ItemService getItemService() {
		ItemService itemService = new ItemServiceImpl();
		// TODO Auto-generated method stub
		return itemService;
	}

}
