package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.bean.ItemBean;
import com.util.JdbcUtil;

public class ItemDao {

	public int getItemCount(Map<String, String> paramMap) {
		String whereSQL = this.getwhereSQL(paramMap);
		String sql = "Select count(1) From T_item where isdel = 0";

		if (whereSQL.length() > 1) {
			sql = sql + whereSQL.toString();

		}

		return JdbcUtil.getRsCount(sql);

	}

	private static String getwhereSQL(Map<String, String> paramMap) {

		String item_name = paramMap.get("item_name");
		String addtime_start = paramMap.get("addtime_start");
		String addtime_end = paramMap.get("addtime_end");
		String item_class = paramMap.get("item_class");
		String price_begin = paramMap.get("price_begin");
		String price_end = paramMap.get("price_end");
		String keyWord = paramMap.get("keyWord");
		StringBuffer whereSQL = new StringBuffer();

		if (item_class != null && !item_class.equals("")) {

			String[] classArray = item_class.split("_");
			if (classArray != null && classArray.length == 2) {
				String classid = classArray[0];
				String parentid = classArray[1];

				if (parentid.equals("0")) {

					whereSQL.append(" and bigclass ='" + classid + "' ");

				} else {

					whereSQL.append(" and smallclass ='" + classid + "' ");

				}

			}

		}

		if (item_name != null && !item_name.equals("")) {
			whereSQL.append(" and itemname like'%" + item_name + "%'");
		}
		if (keyWord != null && !keyWord.equals("")) {
			whereSQL.append(" and itemname like'%" + keyWord + "%'");
		}

		if (addtime_start != null && !addtime_start.equals("")) {

			if (addtime_end == null || addtime_end.equals("")) {
				addtime_end = addtime_start;
			}
			whereSQL.append(" and addtime >= '" + addtime_start + "'");
			whereSQL.append(" and addtime <= '" + addtime_end + "'");
		}
		if (price_begin != null && !price_begin.equals("")) {
			if (price_end == null && price_end.equals("")) {
				price_end = price_begin;
			}
			whereSQL.append(" and itemprice >= '" + price_begin + "'");
			whereSQL.append(" and itemprice <= '" + price_end + "'");
		}

		return whereSQL.toString();
	}

	public static List<ItemBean> getItemList(Map<String, String> paramMap, int startIndex, int endIndex) {

		StringBuffer querySQL = new StringBuffer();

		String whereSQL = getwhereSQL(paramMap);
		querySQL.append("SELECT * FROM( ");
		querySQL.append("SELECT a.*,b.CLASSNAME AS bigname, c.CLASSNAME AS smallname  ");
		querySQL.append("FROM t_item a LEFT OUTER JOIN t_classinfo b ON a.BIGCLASS = b.CLASSID   ");
		querySQL.append("LEFT OUTER JOIN  t_classinfo c ON a.SMALLCLASS = c.classid   ");
		querySQL.append("WHERE a.ISDEL =0 ");
		querySQL.append(whereSQL);
		querySQL.append(" ORDER BY ITEMID DESC limit " + startIndex + "," + endIndex + ")t");

		String sql = "Select * From t_item where isdel = 0 order by itemid desc limit " + startIndex + "," + endIndex
				+ "";

		List<ItemBean> itemList = null;
		try {
			itemList = JdbcUtil.executeQuery2(querySQL.toString(), ItemBean.class);
			if (paramMap.get("keyWord") != null) {

				for (ItemBean itemBean : itemList) {

					itemBean.setItemname(itemBean.getItemname().replaceAll(paramMap.get("keyWord"),
							"<span  style='color:red;' >" + paramMap.get("keyWord") + "</span>"));

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemList;

	}

	public void save(Map<String, String> valueMap) throws Exception {

		String itemname = valueMap.get("itemname");
		String itemprice = valueMap.get("itemprice");
		String filename = valueMap.get("filename");
		String filepath = valueMap.get("filepath");
		String bigclass = valueMap.get("bigclass");
		String smallclass = valueMap.get("smallclass");
		String addtime = valueMap.get("addtime");
		String temp = valueMap.get("temp");
		String isdel = "0";

		List<Object> paramList = new ArrayList<Object>();
		paramList.add(itemname);
		paramList.add(itemprice);
		paramList.add(filename);
		paramList.add(filepath);
		paramList.add(bigclass);
		paramList.add(smallclass);
		paramList.add(addtime);
		paramList.add(temp);
		paramList.add(isdel);

		StringBuffer inserSQL = new StringBuffer();
		inserSQL.append("Insert into t_item( ");
		inserSQL.append("itemname,itemprice,filename,filepath");
		inserSQL.append(",bigclass,smallclass,addtime,temp");
		inserSQL.append(",isdel) values(");
		inserSQL.append("?,?,?,?");
		inserSQL.append(",?,?,?,?");
		inserSQL.append(",?");
		inserSQL.append(")");

		JdbcUtil.executeUpdate_DML_Prepared(inserSQL.toString(), paramList, true);
	}

	public ItemBean getItemBeanByID(String itemid) {
		ItemBean itemBean = new ItemBean();

		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT a.*,b.CLASSNAME AS bigname, c.CLASSNAME AS smallname  ");
		querySQL.append("FROM t_item a LEFT OUTER JOIN t_classinfo b ON a.BIGCLASS = b.CLASSID   ");
		querySQL.append("LEFT OUTER JOIN  t_classinfo c ON a.SMALLCLASS = c.classid   ");
		querySQL.append("where itemid ='" + itemid + "'");

		Connection conn = null;
		QueryRunner runner = new QueryRunner();

		try {
			conn = JdbcUtil.getConn();
			itemBean = runner.query(conn, querySQL.toString(), new BeanHandler<ItemBean>(ItemBean.class));
			if (itemBean != null) {
				itemBean.setCount(1);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);

		}

		return itemBean;
	}

	public void modify(Map<String, String> valueMap) throws Exception {

		String itemname = valueMap.get("itemname");
		String itemprice = valueMap.get("itemprice");
		String filename = valueMap.get("filename");
		String filepath = valueMap.get("filepath");
		String bigclass = valueMap.get("bigclass");
		String smallclass = valueMap.get("smallclass");
		String addtime = valueMap.get("addtime");
		String temp = valueMap.get("temp");
		String itemid = valueMap.get("itemid");

		List<Object> paramList = new ArrayList<Object>();
		paramList.add(itemname);
		paramList.add(itemprice);
		paramList.add(filename);
		paramList.add(filepath);
		paramList.add(bigclass);
		paramList.add(smallclass);
		paramList.add(addtime);
		paramList.add(temp);
		paramList.add(itemid);

		StringBuffer inserSQL = new StringBuffer();
		inserSQL.append("Update  t_item set ");
		inserSQL.append("itemname=?,itemprice=?,filename=?,filepath=?");
		inserSQL.append(",bigclass=?,smallclass=?,addtime=?,temp=? ");
		inserSQL.append(" where 1=1 and itemid = ? ");

		JdbcUtil.executeUpdate_DML_Prepared(inserSQL.toString(), paramList, true);
	}

	public void delete(String itemid) throws Exception {
		Connection conn = null;
		Statement stmt = null;

		String sql = "Delete from  T_item where itemid= " + itemid;

		JdbcUtil.executeUpdate_DML(sql);

	}

}
