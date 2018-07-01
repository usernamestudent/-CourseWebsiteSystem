package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import annotations.Column;
import dao.ObjectDao;
import entity.Article;
import entity.Course;
import entity.CourseGroup;
import entity.Message;
import entity.Part;
import entity.User;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class ObjectServlet
 */
@WebServlet("/ObjectServlet")
public class ObjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ObjectServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HashMap<String, String> hashMap = getHashMap(request);
		String method = hashMap.get("method");
		String type = hashMap.get("type");
		switch(method){
		case "part":
			operation(type, new Part(), hashMap, out);
			break;
		case "courseGroup":
			operation(type, new CourseGroup(), hashMap, out);
			break;
		case "course":
			operation(type, new Course(), hashMap, out);
			break;
		case "article":
			operation(type, new Article(), hashMap, out);
			break;
		case "user":
			operation(type, new User(), hashMap, out);
			break;
		case "message":
			operation(type, new Message(), hashMap, out);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private HashMap<String, String> getHashMap(HttpServletRequest request){
		Enumeration<String> enu = request.getParameterNames();
		HashMap<String, String> hashMap = new HashMap<String, String>();
		while(enu.hasMoreElements()){  
			String paraName = (String)enu.nextElement(); 
			String paravalue = (String)request.getParameter(paraName);
			hashMap.put(paraName, paravalue);
		}
		return hashMap;
	}

	private void operation(String type, Object obj, HashMap<String, String> hashMap, PrintWriter out) {
		Class<?> clz = obj.getClass();
		Field[] fields = clz.getDeclaredFields();
		for(Field field: fields) {
			Column column = field.getAnnotation(Column.class);
			String name = column.name();
			if(hashMap.get(name) != null) {
				try {
					field.setAccessible(true);
					String value = hashMap.get(name);
					if(column.type().equals("Date")) {
						field.set(obj, Date.valueOf(value));
					}else if(column.type().equals("Integer")) {
						field.set(obj, Integer.valueOf(value));
					}else {
						field.set(obj, value);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		boolean falg = false;
		switch(type){
		case "add":
			falg= ObjectDao.create(obj);
			out.println(String.valueOf(falg));
			break;
		case "delete":
			falg = ObjectDao.delete(obj);
			out.println(String.valueOf(falg));
			break;
		case "select":
			JSONArray list = ObjectDao.Select(obj);
			System.out.println(list);
			out.append(list.toString());
			break;
		}
	}

}
