package com.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.bean.ItemBean;
import com.bean.OrderBean;
import com.bean.OrderDetail;
import com.util.JdbcUtil;

public class OrderDao {

	public static List<OrderBean> getAllorder() throws Exception {
		String sql = "Select * from  t_order order by orderid desc";

		List<OrderBean> orderlist = JdbcUtil.executeQuery2(sql, OrderBean.class);

		return orderlist;
	}

	public static int getOrderCount(Map<String, String> paramMap) {
		String whereSQL = getwhereSQL(paramMap);
		String sql = "Select count(1) From t_order where 1=1 ";

		if (whereSQL.length() > 1) {
			sql = sql + whereSQL.toString();

		}

		return JdbcUtil.getRsCount(sql);

	}

	private static String getwhereSQL(Map<String, String> paramMap) {

		String addtime_start = paramMap.get("addtime_start");
		String addtime_end = paramMap.get("addtime_end");
		String username = paramMap.get("username");

		StringBuffer whereSQL = new StringBuffer();

		if (addtime_start != null && !addtime_start.equals("")) {

			if (addtime_end == null || addtime_end.equals("")) {
				addtime_end = addtime_start;
			}
			whereSQL.append(" and in_time >= '" + addtime_start + "'");
			whereSQL.append(" and in_time <= '" + addtime_end + "'");
		}
		if (username != null && !username.equals("")) {
			whereSQL.append(" and username like '%" + username + "%'");

		}

		return whereSQL.toString();
	}

	public static List<OrderBean> getOrderList(Map<String, String> paramMap, int startIndex, int endIndex) {

		StringBuffer querySQL = new StringBuffer();

		String whereSQL = getwhereSQL(paramMap);
		querySQL.append("SELECT * FROM(  ");

		querySQL.append("SELECT *");
		querySQL.append("FROM t_order  where 1=1 ");
		querySQL.append(whereSQL);
		querySQL.append(" ORDER BY orderid desc limit " + startIndex + "," + endIndex + ")t");

		List<OrderBean> orderlist = null;
		try {
			orderlist = JdbcUtil.executeQuery2(querySQL.toString(), OrderBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderlist;

	}

	public static List<OrderDetail> getdetailList(String orderid) throws Exception {
		String sql = "Select * from  t_order_detail where orderid = " + orderid + "    order   by orderid desc";

		List<OrderDetail> detailList = JdbcUtil.executeQuery2(sql, OrderDetail.class);

		return detailList;
	}

	public static void tofail(String orderid) throws Exception {
		String sql = "UPDATE t_order set review='2' where orderid=" + orderid;
		JdbcUtil.executeUpdate_DML(sql);
	}

	public static void topass(String orderid) throws Exception {
		String sql = "UPDATE t_order set review='1'where orderid=" + orderid;
		JdbcUtil.executeUpdate_DML(sql);
	}

	public static void saveOrder(List<ItemBean> itemlist, OrderBean orderBean) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO T_ORDER ");
		sql.append(" (orderid,orderuser,in_time,paytype,");
		sql.append("SENDTYPE,TOTALCOUNT,TOTALPRICE,RECEIVENAME,");
		sql.append("RECEIVEADDRESS,RECEIVEPHONE,RECEIVEMAIL,USERNAME)");
		sql.append(" values(");
		sql.append("'" + orderBean.getOrderid() + "'," + "'" + orderBean.getUsername() + "'," + "'"
				+ orderBean.getIn_time() + "'," + "'" + orderBean.getPaytype() + "',");
		sql.append("'" + orderBean.getSendtype() + "'," + "'" + orderBean.getTotalcount() + "'," + "'"
				+ orderBean.getTotalprice() + "'," + "'" + orderBean.getReceivename() + "',");
		sql.append("'" + orderBean.getReceiveaddress() + "'," + "'" + orderBean.getReceivephone() + "'," + "'"
				+ orderBean.getReceivemail() + "'," + "'" + orderBean.getUsername() + "')");

		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			stmt.executeUpdate(sql.toString());

			for (ItemBean itemBean : itemlist) {
				StringBuffer SQLorderdetail = new StringBuffer();
				SQLorderdetail.append("INSERT INTO t_order_detail (");
				SQLorderdetail.append("ORDERID,ITEMID,ITEMNAME,ITEMPRICE,");
				SQLorderdetail.append("ITEMCOUNT,SUMPRICE) values(");
				SQLorderdetail.append("'" + orderBean.getOrderid() + "'," + "'" + itemBean.getItemid() + "'," + "'"
						+ itemBean.getItemname() + "'," + "'" + itemBean.getItemprice() + "',");
				SQLorderdetail.append("'" + itemBean.getCount() + "'," + "'" + itemBean.getSumprice() + "')");

				stmt.executeUpdate(SQLorderdetail.toString());

			}

			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			stmt.close();
			conn.close();

		}

	}

}
