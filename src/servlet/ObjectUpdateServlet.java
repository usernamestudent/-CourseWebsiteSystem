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
import entity.Part;
import entity.User;

/**
 * Servlet implementation class ObjectUpdateServlet
 */
@WebServlet("/ObjectUpdateServlet")
public class ObjectUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObjectUpdateServlet() {
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
		String id = hashMap.get("id");
		boolean falg = false;
		switch(method){
		case "part":
			Part oldPart = new Part();
			oldPart.setId(id);
			falg = update(new Part(), oldPart, hashMap);
			break;
		case "courseGroup":
			CourseGroup oldCourseGroup = new CourseGroup();
			oldCourseGroup.setId(id);
			falg = update(new CourseGroup(), oldCourseGroup, hashMap);
			break;
		case "course":
			Course oldCourse = new Course();
			oldCourse.setId(id);
			falg = update(new Course(), oldCourse, hashMap);
			break;
		case "article":
			Article oldArticle = new Article();
			oldArticle.setId(id);
			falg = update(new Article(), oldArticle, hashMap);
			break;
		case "user":
			User oldUser = new User();
			oldUser.setId(id);
			falg = update(new User(), oldUser, hashMap);
			break;
		}
		
		out.append(String.valueOf(falg));
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
		HashMap<String, String> hashMap = new HashMap<>();
		while(enu.hasMoreElements()){  
			String paraName = (String)enu.nextElement(); 
			String paravalue = (String)request.getParameter(paraName);
			hashMap.put(paraName, paravalue);
		}
		return hashMap;
	}
	
	private boolean update(Object obj, Object obj2, HashMap<String, String> hashMap) {
		Class<?> clz = obj.getClass();
		Field[] fields = clz.getDeclaredFields();
		for(Field field: fields) {
			Column column = field.getAnnotation(Column.class);
			String name = column.name();
			if(hashMap.get(name) != null) {
				try {
					field.setAccessible(true);
					String value = hashMap.get(name);
					if(column.type() == "Date") {
						field.set(obj, Date.valueOf(value));
					}else if(column.type() == "Integer") {
						field.set(obj, Integer.valueOf(value));
					}else {
						field.set(obj, value);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return ObjectDao.update(obj2, obj);
	}

}
