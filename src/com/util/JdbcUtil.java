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
			throw new RuntimeException("MySQL������ʾ����..");
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
	 * ִ��DDL���
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
	 * ʹ��Statement����ִ��DML��䡣
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
	 * ʹ��PreparedStatement����ִ��DML��䡣
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
			// ���ò���
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
	 * ʹ������ķ�ʽ��ִ�ж���SQL���.
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
			// ��ȡ�������Ԫ����
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> rowMap = new HashMap<String, Object>();
				// ��ȡ�ֶ������ֶε�ֵ��
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
					// ���ֶ������ֶ�ֵ���ŵ�Map�С�
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
			// ��ȡ�������Ԫ����
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				// ʵ��������
				Object rowObject = classz.newInstance();
				// CaseInsensitiveMap���ǲ����ִ�Сд��Map
				Map<String, Object> rowMap = new CaseInsensitiveMap();
				// ��ȡ�ֶ������ֶε�ֵ��
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
					// ���ֶ������ֶ�ֵ���ŵ�Map�С�
					rowMap.put(columnName, columnValue);
				}
				// ��Map�е�ֵ����������[Map�е�keyҪ��Bean�е���������һ��]
				// ���������ߵ�jar����
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
			// ��ȡ�������Ԫ����
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				// ʵ��������
				E e = classz.newInstance();
				// CaseInsensitiveMap���ǲ����ִ�Сд��Map
				Map<String, Object> rowMap = new CaseInsensitiveMap();
				// ��ȡ�ֶ������ֶε�ֵ��
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
					// ���ֶ������ֶ�ֵ���ŵ�Map�С�
					rowMap.put(columnName, columnValue);
				}
				// ��Map�е�ֵ����������[Map�е�keyҪ��Bean�е���������һ��]
				// ���������ߵ�jar����
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
			// ���ò���
			if (paramList != null && paramList.size() > 0) {
				for (int i = 0; i < paramList.size(); i++) {
					pstmt.setObject(i + 1, paramList.get(i));
				}
			}
			rs = pstmt.executeQuery();
			// ��ȡ�������Ԫ����
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				// ʵ��������
				E e = classz.newInstance();
				// CaseInsensitiveMap���ǲ����ִ�Сд��Map
				Map<String, Object> rowMap = new CaseInsensitiveMap();
				// ��ȡ�ֶ������ֶε�ֵ��
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
					// ���ֶ������ֶ�ֵ���ŵ�Map�С�
					rowMap.put(columnName, columnValue);
				}
				// ��Map�е�ֵ����������[Map�е�keyҪ��Bean�е���������һ��]
				// ���������ߵ�jar����
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
	 * �ڿ������̣�SQL����п���д������ܰ�����ʱ����� SQL ���ֱ�Ӵ�ӡ�������Ƕ��Ŵ�ǳ����㣬��Ϊ�����ֱ�ӿ��������ݿ�ͻ��˽��е��ԡ�
	 * 
	 * @param sql    SQL ��䣬���Դ��� ? ��ռλ��
	 * @param params ���뵽 SQL �еĲ������ɵ����ɶ���ɲ���
	 * @return ʵ�� sql ���
	 */
	public static String printRealSql(String sql, Object[] params) {
		if (params == null || params.length == 0) {
			System.out.println("The SQL is------------>\n" + sql);
			return sql;
		}

		if (!match(sql, params)) {
			System.out.println("SQL ����е�ռλ�������������ƥ�䡣SQL��" + sql);

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
	 * ? �Ͳ�����ʵ�ʸ����Ƿ�ƥ��
	 * 
	 * @param sql    SQL ��䣬���Դ��� ? ��ռλ��
	 * @param params ���뵽 SQL �еĲ������ɵ����ɶ���ɲ���
	 * @return true ��ʾΪ ? �Ͳ�����ʵ�ʸ���ƥ��
	 */
	private static boolean match(String sql, Object[] params) {
		if (params == null || params.length == 0)
			return true; // û�в������������

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
