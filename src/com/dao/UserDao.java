package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.bean.UserBean;
import com.util.JdbcUtil;
import com.util.MD5Util;

public class UserDao {
	/**
	 * 获取用户的数据列表
	 * 
	 * @return
	 */
	public List<UserBean> getUserList() {
		String sql = "Select * From T_Userinfo order by userid asc";
		List<UserBean> userList = null;
		try {
			userList = JdbcUtil.executeQuery2(sql, UserBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	public int getCount() {
		String sql = "Select count(1) From T_Userinfo";
		return JdbcUtil.getRsCount(sql);
	}

	public List<UserBean> getUserList(int startIndex, int endIndex) {
		String sql = "Select * From T_Userinfo order by userid asc limit " + startIndex + "," + endIndex + "";

		List<UserBean> userList = null;
		try {
			userList = JdbcUtil.executeQuery2(sql, UserBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	public boolean exist(String username) {
		boolean isExist = false;
		String sql = "Select * From T_Userinfo where username = ?";
		QueryRunner queryRunner = new QueryRunner();
		Connection conn = null;
		UserBean userBean = null;
		try {
			conn = JdbcUtil.getConn();
			userBean = queryRunner.query(conn, sql, new BeanHandler<UserBean>(UserBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		if (userBean != null) {
			isExist = true;
		}
		return isExist;
	}

	public void saveUser(UserBean userBean) {
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("INSERT INTO `t_userinfo` (");
		insertSQL.append("`USERID`, `USERNAME`, `PASSWORD`, ");
		insertSQL.append("`TRUENAME`, `USERSEX`, `USERSEX`, ");
		insertSQL.append("`USERAGE`, `DEPT_ID`,`SALARY`,  ");
		insertSQL.append("`TELPHONE`, `ADDRESS`, `BIRTHDAY`, ");
		insertSQL.append("`MAIL`, `IS_ADMIN`, `Group_ID` ");
		insertSQL.append(") values(");
		insertSQL.append("?,?,?,");
		insertSQL.append("?,?,?,");
		insertSQL.append("?,?,?,");
		insertSQL.append("?,?,?,");
		insertSQL.append("?,?,?");
		insertSQL.append(")");

		List<Object> paramList = new ArrayList<Object>();
		paramList.add(userBean.getUserid());
		paramList.add(userBean.getUsername());
		paramList.add(userBean.getPassword());
		paramList.add(userBean.getTruename());
		paramList.add(userBean.getUsersex());
		paramList.add(userBean.getUserage());

		paramList.add(userBean.getTelphone());
		paramList.add(userBean.getAddress());
		paramList.add(userBean.getBirthday());
		paramList.add(userBean.getMail());
		paramList.add(userBean.getIs_admin());
		try {
			JdbcUtil.executeUpdate_DML_Prepared(insertSQL.toString(), paramList, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteUserById(String userid) {
		String sql = "Delete From T_Userinfo where userid = ?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(userid);
		try {
			JdbcUtil.executeUpdate_DML_Prepared(sql, paramList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static UserBean getUserBeanById(String userid) {

		String sql = "Select * from t_userinfo where userid  = " + userid;
		QueryRunner runner = new QueryRunner();
		Connection conn = null;
		UserBean userBean = new UserBean();
		try {
			conn = JdbcUtil.getConn();
			userBean = runner.query(conn, sql, new BeanHandler<UserBean>(UserBean.class));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);

		}

		return userBean;

	}

	public void updateuserBean(UserBean userBean) throws Exception {

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("UPDATE t_userinfo SET ");
		sqlBuffer.append(" username='" + userBean.getUsername() + "',");
		sqlBuffer.append(" truename='" + userBean.getTruename() + "',");
		sqlBuffer.append(" usersex='" + userBean.getUsersex() + "',");
		sqlBuffer.append(" telphone='" + userBean.getTelphone() + "',");
		sqlBuffer.append(" address='" + userBean.getAddress() + "',");
		sqlBuffer.append(" birthday='" + userBean.getBirthday() + "',");
		sqlBuffer.append(" mail='" + userBean.getMail() + "',");
		sqlBuffer.append(" address='" + userBean.getAddress() + "',");
		sqlBuffer.append(" address='" + userBean.getAddress() + "',");
		sqlBuffer.append(" userage='" + userBean.getUserage() + "'");
		sqlBuffer.append("  where userid = ");
		sqlBuffer.append("'" + userBean.getUserid() + "'");

		System.out.println("SQL:" + sqlBuffer.toString());

		JdbcUtil.executeUpdate_DML(sqlBuffer.toString());

	}

	public void editpass(int userid, String password, String newpassword) throws Exception {

		String sql = "UPDATE T_USERINFO SET PASSWORD = '" + MD5Util.getMD5String(newpassword) + "' WHERE USERID = '"
				+ userid + "' AND PASSWORD = '" + MD5Util.getMD5String(password) + "'";
		JdbcUtil.executeUpdate_DML(sql);

	}

	public static int getUserCount(Map<String, String> paramMap) {
		String whereSQL = getwhereSQL(paramMap);
		String sql = "Select count(1) From t_userinfo where 1=1 ";

		if (whereSQL.length() > 1) {
			sql = sql + whereSQL.toString();

		}

		return JdbcUtil.getRsCount(sql);

	}

	private static String getwhereSQL(Map<String, String> paramMap) {

		String username = paramMap.get("username");

		StringBuffer whereSQL = new StringBuffer();

		if (username != null && !username.equals("")) {
			whereSQL.append(" and username like '%" + username + "%'");

		}

		return whereSQL.toString();
	}

	public static List<UserBean> getUserList(Map<String, String> paramMap, int startIndex, int endIndex) {

		StringBuffer querySQL = new StringBuffer();

		String whereSQL = getwhereSQL(paramMap);
		querySQL.append("SELECT * FROM(  ");

		querySQL.append("SELECT *");
		querySQL.append("FROM t_userinfo  where 1=1 ");
		querySQL.append(whereSQL);
		querySQL.append(" ORDER BY userid desc limit " + startIndex + "," + endIndex + ")t");

		List<UserBean> userlist = null;
		try {
			userlist = JdbcUtil.executeQuery2(querySQL.toString(), UserBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userlist;

	}

	public void freeze(String userid) throws Exception {

		String sql = "select * from t_userinfo where userid ='" + userid + "'";
		List<UserBean> userlist = JdbcUtil.executeQuery2(sql, UserBean.class);
		if (userlist != null && userlist.size() > 0) {

			int is_freeze = userlist.get(0).getIs_freeze();
			System.out.println((is_freeze == 0) + "and" + is_freeze);

			if (is_freeze == 0) {
				is_freeze = 1;
			} else {
				is_freeze = 0;
			}

			String sql1 = "update t_userinfo set is_freeze = '" + is_freeze + "' where userid ='" + userid + "'";
			System.out.println("sadasdasdas:" + sql1);

			JdbcUtil.executeUpdate_DML(sql1);

		}

	}

	public static List<UserBean> getSuperUserList(Map<String, String> paramMap, int startIndex, int endIndex) {

		StringBuffer querySQL = new StringBuffer();

		String whereSQL = getwhereSQL(paramMap);
		querySQL.append("SELECT * FROM(  ");

		querySQL.append("SELECT *");
		querySQL.append("FROM t_userinfo  where 1=1 and is_admin = 0 ");
		querySQL.append(whereSQL);
		querySQL.append(" ORDER BY userid  desc limit " + startIndex + "," + endIndex + ")t");

		List<UserBean> userlist = null;
		try {
			userlist = JdbcUtil.executeQuery2(querySQL.toString(), UserBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userlist;

	}

	public void isadmin(String userid) throws Exception {

		String sql = "select * from t_userinfo where userid ='" + userid + "'";
		List<UserBean> userlist = JdbcUtil.executeQuery2(sql, UserBean.class);
		if (userlist != null && userlist.size() > 0) {

			int is_admin = userlist.get(0).getIs_admin();
			System.out.println((is_admin == 0) + "and" + is_admin);

			if (is_admin == 0) {
				is_admin = 1;
			} else {
				is_admin = 0;
			}

			String sql1 = "update t_userinfo set is_admin = '" + is_admin + "' where userid ='" + userid + "'";
			System.out.println("sadasdasdas:" + sql1);

			JdbcUtil.executeUpdate_DML(sql1);

		}

	}

}
