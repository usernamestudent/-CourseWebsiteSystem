package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class uploadFileServlet
 */
@WebServlet("/uploadFileServlet")
public class uploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
						Date now = new Date();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		String image = (String) request.getAttribute("image");
		String name = (String) request.getAttribute("name");
		System.out.println(image);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
