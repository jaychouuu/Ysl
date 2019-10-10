package com.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Filter_IsLogin implements javax.servlet.Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String requestURI = request.getRequestURI();

		// 判断Session中是否有值、如果值、表示已经登录。
		HttpSession session = request.getSession();

		if (requestURI.indexOf("image.jsp") != -1 || requestURI.indexOf("Login.jsp") != -1) {

			chain.doFilter(request, response);

		}

		else if (requestURI.indexOf("front") != -1 && session.getAttribute("userBean") == null) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String loginURL = request.getContextPath() + "/index.jsp";
			String errorMsg = "您还未登录，或者会话已超时，请重新登录";
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
			out.println("<script language='javascript'>");
			out.println("window.alert('" + errorMsg + "');");
			out.println("window.location.href = '" + loginURL + "';");
			out.println("</script>");
		}

		else if (session.getAttribute("userBean") == null) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String loginURL = request.getContextPath() + "/admin/Login.jsp";
			String errorMsg = "您还未登录，或者会话已超时，请重新登录";
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
			out.println("<script language='javascript'>");
			out.println("window.alert('" + errorMsg + "');");
			out.println("window.location.href = '" + loginURL + "';");
			out.println("</script>");
		} else {
			// 已经登录
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
