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
import org.apache.commons.fileupload.FileUploadException;
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
			if (type == null) {
				hashMap = getPath(request);
				type = hashMap.get("type");
			} 
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
			out.append(list.toString());
			break;
		}
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
						System.out.println("获取上传文件的总共的容量: " + item.getSize());
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
