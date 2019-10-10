package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.ClassInfo;
import com.util.JdbcUtil;

public class ClassInfoDao {

	public List<ClassInfo> getlist() throws Exception {
		// TODO Auto-generated method stub
		String sql = "Select * from T_classinfo where parentid = 0 order by classid asc";
		List<ClassInfo> classlist = new ArrayList<ClassInfo>();

		try {

			classlist = JdbcUtil.executeQuery2(sql, ClassInfo.class);

			for (ClassInfo classInfo : classlist) {
				int classid = classInfo.getClassid();
				List<ClassInfo> Childlist = this.getSmallList(classid);

				classInfo.setChildList(Childlist);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return classlist;
	}

	public void save(ClassInfo classInfo) throws Exception {
		// TODO Auto-generated method stub
		int parentid = classInfo.getParentid();
		int classid;

		classid = this.getMaxClassID(classInfo);

		String classname = classInfo.getClassname();

		StringBuffer inserSQL = new StringBuffer();

		inserSQL.append("Insert Into T_classinfo(");
		inserSQL.append("classid,classname,parentid");
		inserSQL.append(")values(");
		inserSQL.append("'" + classid + "',");
		inserSQL.append("'" + classname + "',");
		inserSQL.append("'" + parentid + "'");
		inserSQL.append(")");

		JdbcUtil.executeUpdate_DML(inserSQL.toString());
	}

	private int getMaxClassID(ClassInfo classInfo) throws Exception {
		// TODO Auto-generated method stub

		String sql = "Select max(classid) as max_classid from T_classinfo where parentid = " + classInfo.getParentid();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		int max_classid;

		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {

				if (rs.getInt("max_classid") <= 1) {

					max_classid = classInfo.getParentid() * 100 + 1;

				} else {
					max_classid = rs.getInt("max_classid") + 1;

				}

			} else {

				max_classid = classInfo.getParentid() * 100 + 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}

		return max_classid;

	}

	public void change(ClassInfo classInfo, ClassInfo oldclassInfo) throws Exception {
		Connection conn = null;
		Statement stmt = null;

		String sql = "UPDATE T_classinfo SET classname = '" + classInfo.getClassname() + "' WHERE classid = "
				+ oldclassInfo.getClassid();
		String sql2 = "UPDATE T_classinfo SET classid = " + classInfo.getClassid() + "  WHERE classname ='"
				+ classInfo.getClassname() + "'";

		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql2);

			conn.commit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			stmt.close();

			conn.close();

		}

	}

	public void delete(String classid, String parentid) throws Exception {
		// TODO Auto-generated method stub

		Connection conn = null;
		Statement stmt = null;

		String sql = "Delete from  T_classinfo where parentid= " + classid;
		String sql2 = "Delete from  T_classinfo where classid=" + classid;

		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql2);

			conn.commit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			stmt.close();

			conn.close();

		}

	}

	public List<ClassInfo> getBigList() throws Exception {

		String sql = "Select * from T_classinfo where parentid = 0 order by classid asc";
		List<ClassInfo> classlist = new ArrayList<ClassInfo>();

		try {

			classlist = JdbcUtil.executeQuery2(sql, ClassInfo.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return classlist;

	}

	public static List<ClassInfo> getSmallList(int bigid) throws Exception {
		String sql = "Select * from T_classinfo where parentid = " + bigid + " order by classid asc ";
		List<ClassInfo> childlist = new ArrayList<ClassInfo>();

		try {
			childlist = JdbcUtil.executeQuery2(sql, ClassInfo.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		return childlist;
	}

	public static int getCount() {
		String sql = "Select count(1) From T_classinfo where parentid = 0 ";
		return JdbcUtil.getRsCount(sql);
	}

	public static List<ClassInfo> getClassInfoList(int startIndex, int endIndex) throws Exception {

		// TODO Auto-generated method stub
		String sql = "Select * from T_classinfo where parentid = 0 order by classid asc limit " + startIndex + ","
				+ endIndex + " ";
		List<ClassInfo> classlist = new ArrayList<ClassInfo>();

		try {

			classlist = JdbcUtil.executeQuery2(sql, ClassInfo.class);

			for (ClassInfo classInfo : classlist) {
				int classid = classInfo.getClassid();
				List<ClassInfo> Childlist = getSmallList(classid);

				classInfo.setChildList(Childlist);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		return classlist;
	}

}
