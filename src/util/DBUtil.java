package util;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;


import annotations.Column;
public class DBUtil {
	private static Connection connection = null;
	private static String driverName = "";
	private static String url = "";
	private static String userName = "";
	private static String password = "";

	static {
		InputStream in = DBUtil.class.getResourceAsStream("../config/jdbc.properties");//静态块直接调用Object的class对象
		Properties properties = new Properties();
		try {
			properties.load(in);
			if (properties.containsKey("jdbc.driverName")) {
				driverName = properties.getProperty("jdbc.driverName");
			}
			if (properties.containsKey("jdbc.url")) {
				url = properties.getProperty("jdbc.url");
			}
			if (properties.containsKey("jdbc.userName")) {
				userName = properties.getProperty("jdbc.userName");
			}
			if (properties.containsKey("jdbc.password")) {
				password = properties.getProperty("jdbc.password");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static Connection getConnection() {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

//	public static boolean execute(String sql) {
//		Connection connection = getConnection();
//
//		Statement statement = null;
//		boolean flag = false;
//		try {
//			statement = connection.createStatement();
//			statement.execute(sql);	
//			flag = true;
//		} catch (SQLException e) {
//			if (e instanceof MySQLSyntaxErrorException) { //数据库表存在
//				System.out.println();
//			} else {
//				e.printStackTrace();
//			}
//		} finally {
//			try {
//				statement.close();
//				connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return flag;
//	}
	
	public static boolean executeUpdate(String sql, Object object, Field[] fields) {
		Connection connection = DBUtil.getConnection();	
		PreparedStatement preparedStatement = null;
		boolean flag = false;
		try{
			preparedStatement = connection.prepareStatement(sql);
			int index = 1;
			for(int i = 0;i < fields.length;i++){
				fields[i].setAccessible(true);
				Column column = fields[i].getAnnotation(Column.class);
				if(column == null) continue;
				if(column.isId() == true) {
					continue;
				}
				preparedStatement.setObject(index++, fields[i].get(object));
			}
			if(preparedStatement.executeUpdate()>0){
				flag=true;
			}
		}catch(SQLException | IllegalArgumentException | IllegalAccessException e){
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return flag;
	}
	
	public static boolean executeList(String sql, ArrayList<Object> list) {
		Connection conn = DBUtil.getConnection();	
		PreparedStatement preparedStatement = null;
		boolean flag = false;
		try {
			preparedStatement = conn.prepareStatement(sql);
			for(int i = 0;i < list.size();i++){
				preparedStatement.setObject(i+1, list.get(i));
			}
			flag = preparedStatement.execute();
		}catch(SQLException | IllegalArgumentException e){
			e.printStackTrace();
		}finally{
			try {
				preparedStatement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
}
