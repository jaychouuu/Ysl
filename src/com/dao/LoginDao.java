package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpSession;

import com.bean.UserBean;
import com.util.JdbcUtil;
import com.util.MD5Util;
import com.util.ResultInfo;

public class LoginDao {

	public static ResultInfo loginJudge(UserBean userBean, HttpSession session, String requestURI, String background)
			throws Exception {
		String username = userBean.getUsername();
		String password = MD5Util.getMD5String(userBean.getPassword());
		ResultInfo resultInfo = new ResultInfo();

		String sql = "Select * from T_userinfo where username = ?  and password = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = JdbcUtil.getConn();
		ResultSet rs = null;
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, password);

		rs = pstmt.executeQuery();
		if (rs.next()) {

			if (rs.getInt("IS_FREEZE") == 1) {
				resultInfo.setFlag(false);
				resultInfo.setMessage("该账号已被冻结，请联系客服处理");

			} else if (background != null && rs.getInt("is_admin") == 1) {
				resultInfo.setFlag(false);
				resultInfo.setMessage("该账号没有管理员权限!");
			} else {
				userBean.setUserid(rs.getInt("userid"));
				userBean.setUsername(rs.getString("username"));
				userBean.setUserage(rs.getString("userage"));
				userBean.setTruename(rs.getString("truename"));
				userBean.setAddress(rs.getString("address"));
				userBean.setBirthday(rs.getString("birthday"));

				userBean.setUserage(String.valueOf(rs.getInt("userage")));
				userBean.setUsersex(rs.getString("usersex"));
				userBean.setTelphone(rs.getString("TELPHONE"));
				userBean.setMail(rs.getString("MAIL"));
				userBean.setIs_admin(rs.getInt("IS_ADMIN"));
				userBean.setIs_super(rs.getInt("IS_SUPER"));
				session.setAttribute("userBean", userBean);
				session.setAttribute("a", "a");
				resultInfo.setFlag(true);
			}
		} else {
			resultInfo.setFlag(false);
			resultInfo.setMessage("用户名或者密码错误");

		}

		JdbcUtil.closeResource(pstmt, conn);

		return resultInfo;

	}

	public static ResultInfo register(UserBean userBean, HttpSession session, String requestURI, String background)

			throws Exception {

		String username = userBean.getUsername();
		String password = MD5Util.getMD5String(userBean.getPassword());
		ResultInfo resultInfo = new ResultInfo();

		String sql = "Select * from T_userinfo where username = ?  and password = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		conn = JdbcUtil.getConn();
		ResultSet rs = null;
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, password);

		rs = pstmt.executeQuery();
		if (rs.next()) {

			resultInfo.setFlag(false);
			resultInfo.setMessage("用户名已被创建");

		} else {

			String sql1 = "insert into t_userinfo (username,password,is_admin,is_freeze,is_super) values('" + username
					+ "','" + password + "','1','0','0')";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql1);
			stmt.close();
			resultInfo.setFlag(true);
			resultInfo.setMessage("用户名创建成功");
		}

		JdbcUtil.closeResource(pstmt, conn);

		return resultInfo;

	}

}
