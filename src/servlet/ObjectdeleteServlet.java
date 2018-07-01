package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;

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

/**
 * Servlet implementation class ObjectdeleteServlet
 */
@WebServlet("/ObjectdeleteServlet")
public class ObjectdeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ObjectdeleteServlet() {
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
		String method = request.getParameter("method");
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		boolean falg = false;
		for(int i = 0; i < ids.length; i++) {
			switch(method){
			case "part":
				Part part = new Part();
				falg = delete(ids, part);
				break;
			case "courseGroup":
				CourseGroup courseGroup = new CourseGroup();
				falg = delete(ids, courseGroup);
				break;
			case "course":
				Course course = new Course();
				falg = delete(ids, course);
				break;
			case "article":
				Article article = new Article();
				falg = delete(ids, article);
				break;
			case "user":
				User user = new User();
				falg = delete(ids, user);
				break;
			case "message":
				Message message = new Message();
				falg = delete(ids, message);
				break;
			}
		}
		
		out.append(String.valueOf(falg));
	}

	private boolean delete(String[]  ids, Object obj) {
		int index = 0;
		boolean falg = false;
		Class<?> clz = obj.getClass();
		Field[] fields = clz.getDeclaredFields();
		for(index = 0; index < fields.length; index++) {
			fields[index].setAccessible(true);
			Column column = fields[index].getAnnotation(Column.class);
			if(column.name().equals("id")) {
				break;
			}
		}
		for(int i = 0; i < ids.length; i++) {
			try {
				fields[index].set(obj, Integer.valueOf(ids[i]));
				falg = ObjectDao.delete(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return falg;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
