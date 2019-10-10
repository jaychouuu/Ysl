package com.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class Filter_Encoding implements javax.servlet.Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// 1：由于GET与POST的请求，在Tomcat8以下是区分开的。因为在编码过滤器中，是要区分
		// GET请求与POST请求和分开处理
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String method = request.getMethod();
		if (method.equalsIgnoreCase("post")) {
			// 在Tomcat8以下的版本。request.setCharacterEncoding()方法，只针对post起作用
			// 对于GET请求不起作用。Tomcat8版本以下，对get与post方法都起作用。
			request.setCharacterEncoding("UTF-8");
		} else if (method.equalsIgnoreCase("get")) {
			request = new MyGetRequestWrapper(request);
		}
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

	class MyGetRequestWrapper extends HttpServletRequestWrapper {

		public MyGetRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			String oldValue = super.getParameter(name);
			String newValue = null;
			if (oldValue != null && oldValue.equals("") == false) {
				try {
					newValue = new String(oldValue.getBytes("ISO-8859-1"),
							"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return newValue;
		}

		@Override
		public String[] getParameterValues(String name) {
			String[] oldValueArray = super.getParameterValues(name);
			String[] newValueArray = null;
			if (oldValueArray != null && oldValueArray.length > 0) {
				newValueArray = new String[oldValueArray.length];
				int i = 0;
				for (String str : oldValueArray) {
					try {
						String newStr = new String(str.getBytes("ISO-8859-1"),
								"UTF-8");
						newValueArray[i] = newStr;
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					i++;
				}
			}
			return newValueArray;
		}
	}
}
