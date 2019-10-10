package com.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.CaseInsensitiveMap;

public class JdbcUtil {
	private static final String url = "jdbc:mysql://127.0.0.1:3306/test1?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&zeroDateTimeBehavior=convertToNull";
	private static final String username = "root";
	private static final String password = "123456";

	private JdbcUtil() {

	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			throw new RuntimeException("MySQL的驱动示加载..");
		}
	}

	public static Connection getConn() throws Exception {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			throw e;
		}
		return conn;
	}

	/**
	 * 执行DDL语句
	 * 
	 * @param sql
	 * @throws Exception
	 */
	public static void executeUpdate_DDL(String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeResource(stmt, conn);
		}
	}

	/**
	 * 使用Statement对象，执行DML语句。
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int executeUpdate_DML(String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		int rowCount = 0;
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rowCount = stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeResource(stmt, conn);
		}
		return rowCount;
	}

	/**
	 * 使用PreparedStatement对象，执行DML语句。
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int executeUpdate_DML_Prepared(String sql, List<Object> paramList) throws Exception {
		return executeUpdate_DML_Prepared(sql, paramList, false);
	}

	public static int executeUpdate_DML_Prepared(String sql, List<Object> paramList, boolean isPrintSQL)
			throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			conn = JdbcUtil.getConn();
			pstmt = conn.prepareStatement(sql);
			// 设置参数
			for (int i = 0; i < paramList.size(); i++) {
				pstmt.setObject(i + 1, paramList.get(i));
			}
			if (isPrintSQL == true) {
				Object[] paramArray = paramList.toArray();
				String realSQL = JdbcUtil.printRealSql(sql, paramArray);
				System.out.println("realSQL = " + realSQL);
			}

			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeResource(pstmt, conn);
		}
		return rowCount;
	}

	/**
	 * 使用事务的方式来执行多条SQL语句.
	 * 
	 * @param sqlList
	 * @return
	 * @throws Exception
	 */
	public static boolean executeTx(List<String> sqlList) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		boolean is_succ = true;
		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (String sql : sqlList) {
				stmt.executeUpdate(sql);
			}
			conn.commit();
			is_succ = true;
		} catch (Exception e) {
			is_succ = false;
			e.printStackTrace();
			conn.rollback();
			throw e;
		} finally {
			JdbcUtil.closeResource(stmt, conn);
		}
		return is_succ;
	}

	public static List<Map<String, Object>> executeQuery(String sql) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// 获取结果集的元数据
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> rowMap = new HashMap<String, Object>();
				// 获取字段名与字段的值。
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					int columnType = metaData.getColumnType(i);
					Object columnValue = null;
					switch (columnType) {
					case java.sql.Types.INTEGER:
						columnValue = rs.getInt(columnName);
						break;
					case java.sql.Types.VARCHAR:
						columnValue = rs.getString(columnName);
						break;
					case java.sql.Types.DECIMAL:
						columnValue = rs.getBigDecimal(columnName);
						break;
					default:
						columnValue = rs.getObject(columnName);
						break;
					}
					// 将字段名与字段值，放到Map中。
					rowMap.put(columnName, columnValue);

					// System.out.println(rowMap);
				}
				dataList.add(rowMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return dataList;
	}

	public static List<Object> executeQuery(String sql, Class classz) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Object> dataList = new ArrayList<Object>();
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// 获取结果集的元数据
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				// 实例化对象
				Object rowObject = classz.newInstance();
				// CaseInsensitiveMap，是不区分大小写的Map
				Map<String, Object> rowMap = new CaseInsensitiveMap();
				// 获取字段名与字段的值。
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					int columnType = metaData.getColumnType(i);
					Object columnValue = null;
					switch (columnType) {
					case java.sql.Types.INTEGER:
						columnValue = rs.getInt(columnName);
						break;
					case java.sql.Types.VARCHAR:
						columnValue = rs.getString(columnName);
						break;
					case java.sql.Types.DECIMAL:
						columnValue = rs.getBigDecimal(columnName);
						break;
					default:
						columnValue = rs.getObject(columnName);
						break;
					}
					// 将字段名与字段值，放到Map中。
					rowMap.put(columnName, columnValue);
				}
				// 将Map中的值，赋给对象。[Map中的key要跟Bean中的属性名称一致]
				// 第三方工具的jar包。
				BeanUtils.copyProperties(rowObject, rowMap);

				// BeanUtils.populate(rowObject, rowMap);

				dataList.add(rowObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return dataList;

	}

	public static <E> List<E> executeQuery2(String sql, Class<E> classz) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<E> dataList = new ArrayList<E>();
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			// 获取结果集的元数据
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				// 实例化对象
				E e = classz.newInstance();
				// CaseInsensitiveMap，是不区分大小写的Map
				Map<String, Object> rowMap = new CaseInsensitiveMap();
				// 获取字段名与字段的值。
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					int columnType = metaData.getColumnType(i);
					Object columnValue = null;
					switch (columnType) {
					case java.sql.Types.INTEGER:
						columnValue = rs.getInt(columnName);
						break;
					case java.sql.Types.VARCHAR:
						columnValue = rs.getString(columnName);
						break;
					case java.sql.Types.DECIMAL:
						columnValue = rs.getBigDecimal(columnName);
						break;
					default:
						columnValue = rs.getObject(columnName);
						break;
					}
					// 将字段名与字段值，放到Map中。
					rowMap.put(columnName, columnValue);
				}
				// 将Map中的值，赋给对象。[Map中的key要跟Bean中的属性名称一致]
				// 第三方工具的jar包。
				BeanUtils.copyProperties(e, rowMap);
				dataList.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return dataList;
	}

	public static <E> List<E> executeQuery_Prepared(String sql, List<Object> paramList, Class<E> classz)
			throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<E> dataList = new ArrayList<E>();
		try {
			conn = JdbcUtil.getConn();
			pstmt = conn.prepareStatement(sql);
			// 设置参数
			if (paramList != null && paramList.size() > 0) {
				for (int i = 0; i < paramList.size(); i++) {
					pstmt.setObject(i + 1, paramList.get(i));
				}
			}
			rs = pstmt.executeQuery();
			// 获取结果集的元数据
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				// 实例化对象
				E e = classz.newInstance();
				// CaseInsensitiveMap，是不区分大小写的Map
				Map<String, Object> rowMap = new CaseInsensitiveMap();
				// 获取字段名与字段的值。
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnName(i);
					int columnType = metaData.getColumnType(i);
					Object columnValue = null;
					switch (columnType) {
					case java.sql.Types.INTEGER:
						columnValue = rs.getInt(columnName);
						break;
					case java.sql.Types.VARCHAR:
						columnValue = rs.getString(columnName);
						break;
					case java.sql.Types.DECIMAL:
						columnValue = rs.getBigDecimal(columnName);
						break;
					default:
						columnValue = rs.getObject(columnName);
						break;
					}
					// 将字段名与字段值，放到Map中。
					rowMap.put(columnName, columnValue);
				}
				// 将Map中的值，赋给对象。[Map中的key要跟Bean中的属性名称一致]
				// 第三方工具的jar包。
				BeanUtils.copyProperties(e, rowMap);
				dataList.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.closeResource(rs, pstmt, conn);
		}
		return dataList;
	}

	/**
	 * 在开发过程，SQL语句有可能写错，如果能把运行时出错的 SQL 语句直接打印出来，那对排错非常方便，因为其可以直接拷贝到数据库客户端进行调试。
	 * 
	 * @param sql    SQL 语句，可以带有 ? 的占位符
	 * @param params 插入到 SQL 中的参数，可单个可多个可不填
	 * @return 实际 sql 语句
	 */
	public static String printRealSql(String sql, Object[] params) {
		if (params == null || params.length == 0) {
			System.out.println("The SQL is------------>\n" + sql);
			return sql;
		}

		if (!match(sql, params)) {
			System.out.println("SQL 语句中的占位符与参数个数不匹配。SQL：" + sql);

			return null;
		}

		int cols = params.length;
		Object[] values = new Object[cols];
		System.arraycopy(params, 0, values, 0, cols);

		for (int i = 0; i < cols; i++) {
			Object value = values[i];
			if (value instanceof Date) {
				values[i] = "'" + value + "'";
			} else if (value instanceof String) {
				values[i] = "'" + value + "'";
			} else if (value instanceof Boolean) {
				values[i] = (Boolean) value ? 1 : 0;
			}
		}

		String realSQL = String.format(sql.replaceAll("\\?", "%s"), values);
		return realSQL;
	}

	/**
	 * ? 和参数的实际个数是否匹配
	 * 
	 * @param sql    SQL 语句，可以带有 ? 的占位符
	 * @param params 插入到 SQL 中的参数，可单个可多个可不填
	 * @return true 表示为 ? 和参数的实际个数匹配
	 */
	private static boolean match(String sql, Object[] params) {
		if (params == null || params.length == 0)
			return true; // 没有参数，完整输出

		Matcher m = Pattern.compile("(\\?)").matcher(sql);
		int count = 0;
		while (m.find()) {
			count++;
		}

		return count == params.length;
	}

	public static void closeResource(Statement stmt, Connection conn) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stmt = null;
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		conn = null;
	}

	public static void closeResource(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		rs = null;

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		stmt = null;
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		conn = null;
	}

	public static int getRsCount(String sql) {
		int rsCount = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				rsCount = rs.getInt(1);
			} else {
				rsCount = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return rsCount;
	}
}
