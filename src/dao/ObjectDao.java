package dao;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import annotations.Column;
import annotations.Table;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.DBUtil;


public class ObjectDao {

	public static boolean create(Object object) {
		Class<?> clazz = object.getClass();
		Table entity = clazz.getAnnotation(Table.class);
		String sql = "insert into " + entity.name() + " (";
		StringBuilder area = new StringBuilder("");
		StringBuilder value = new StringBuilder("");
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Column column = fields[i].getAnnotation(Column.class);
			if(column == null) continue;
			if(column.isId() == true) {
				continue;
			}
			area.append(column.name());
			value.append("?");
			if (i < fields.length - 1) {
				area.append(",");
				value.append(",");
			}
		}
		sql = sql + area.toString() + ")values(" + value.toString() + ")";
		return DBUtil.executeUpdate(sql, object, fields);
	}

	public static boolean delete(Object object){
		Class<?> clazz = object.getClass();
		Table entity = clazz.getAnnotation(Table.class);
		Field[] fields = clazz.getDeclaredFields();
		String sql="delete from "+ entity.name() + " where ";

		StringBuffer where = new StringBuffer("");
		ArrayList<Object> args = getFiledString(object, fields, " and ", where);
		sql = sql + where.toString();
		return DBUtil.executeList(sql, args);
	}


	public static boolean update(Object oldObject, Object newObject) {
		Class<?> clazz = newObject.getClass();
		Table entity = clazz.getAnnotation(Table.class);
		String sql = "update " + entity.name() + " set ";
		Field[] newfields = clazz.getDeclaredFields();
		Field[] oldfields = oldObject.getClass().getDeclaredFields();
		StringBuffer set = new StringBuffer("");
		StringBuffer where = new StringBuffer("");

		ArrayList<Object> oldList = getFiledString(newObject, newfields, ",", set);
		ArrayList<Object> newList = getFiledString(oldObject, oldfields, " and ", where);
		oldList.addAll(newList);
		sql = sql + set.toString() + " where " + where.toString();
		return DBUtil.executeList(sql, oldList);
	}

	public static JSONArray Select(Object object) {
		Class<?> clazz = object.getClass();
		Table entity = clazz.getAnnotation(Table.class);
		String sql = "Select * from " + entity.name() + " where 1 = 1";
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer condition = new StringBuffer("");
		ArrayList<Object> list = getFiledString(object, fields, " and ", condition);
		Connection conn = DBUtil.getConnection();
		ResultSet resultSet = null;
		if (list.size() != 0) {
			sql = sql + " and " + condition.toString();
			try {
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				for (int i = 0; i < list.size(); i++) {
					preparedStatement.setObject(i + 1, list.get(i));
				}
				resultSet = preparedStatement.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			try {
				resultSet = conn.createStatement().executeQuery(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		JSONArray result = new JSONArray();
		try {
			while(resultSet.next()) {
				Object obj = clazz.getConstructor().newInstance();
				Field[] resultField = obj.getClass().getDeclaredFields();
				JSONObject jsonObject = new JSONObject();
				for (Field field : resultField) {
					field.setAccessible(true);
					String value = resultSet.getString(field.getName());
				    jsonObject.put(field.getName(), value.toString());
				}
				result.put(jsonObject);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static ArrayList<Object> getFiledString(Object object, Field[] fields, String type, StringBuffer str) {
		ArrayList<Object> list = new ArrayList<Object>();
		for (Field field : fields) {
			field.setAccessible(true);
			Column column = field.getAnnotation(Column.class);
			try {
				if (field.get(object) != null) {
					str.append(column.name() + " = ?").append(type);
					list.add(field.get(object));
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} 

		}
		if (list.size() != 0) {
			str = str.delete(str.length() - type.length(), str.length());
		}
		return list;
	}
}
