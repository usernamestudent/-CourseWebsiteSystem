package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import annotations.Column;
import dao.ObjectDao;
import entity.Article;
import entity.Course;
import entity.CourseGroup;
import entity.Message;
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
		String method = request.getParameter("method");
		HashMap<String, String> hashMap = getHashMap(request);
		Integer id = Integer.valueOf(hashMap.get("id"));
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
			if(id != null) {
				hashMap = getPath(request);
				id = Integer.valueOf(hashMap.get("id"));
			}
			oldArticle.setId(id);
			falg = update(new Article(), oldArticle, hashMap);
			break;
		case "user":
			User oldUser = new User();
			oldUser.setId(String.valueOf(id));
			falg = update(new User(), oldUser, hashMap);
			break;
		case "message":
			Message oldMessage = new Message();
			oldMessage.setId(id);
			falg = update(new User(), oldMessage, hashMap);
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
		return ObjectDao.update(obj2, obj);
	}
	
	private HashMap<String, String> getPath(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		HashMap<String, String> hashMap = new HashMap<>();
		String fName = "";
		String suffix = "";

		DiskFileItemFactory factory = new DiskFileItemFactory();

		String path = "D://image";

		factory.setRepository(new File(path));

		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			for (FileItem item : list) {
				String name = item.getFieldName();
				if(!name.equals("image")) {
					String value = (String)item.getString("UTF-8");
					hashMap.put(name, value);
				}else {
					if (item.isFormField()) {
						String value = item.getString("UTF-8");
						request.setAttribute(name, value);
					} else {
						String value = item.getName();
						int start = value.lastIndexOf("\\");
						String filename = value.substring(start + 1);

						if (filename.indexOf(".") >= 0) {
							int indexdot = filename.indexOf(".");

							suffix = filename.substring(indexdot);

							fName = filename.substring(0, filename.lastIndexOf("."));
							java.util.Date now = new java.util.Date();
							fName = fName + "_" + now.getTime();
							fName = fName + suffix;
						}

						File picFileDir = new File(path);
						if (!picFileDir.exists()) {
							picFileDir.mkdir();
						}

						File file = new File(path, fName);
						OutputStream out = new FileOutputStream(file);
						if(fName != null){
							String image = "http://localhost:8080/img/"+fName;
							request.setAttribute("image", image);
						} else {
							request.setAttribute("image", "");
						}
						InputStream in = item.getInputStream();
						int length = 0;
						byte[] buf = new byte[1024];
						while ((length = in.read(buf)) != -1) {
							out.write(buf, 0, length);
						}

						in.close();
						out.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String picture = (String) request.getAttribute("image");
		hashMap.put("image", picture);
		return hashMap;
	}
}