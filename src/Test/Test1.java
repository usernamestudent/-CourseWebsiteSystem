package Test;

import dao.ObjectDao;
import entity.Course;
import entity.CourseGroup;
import entity.Part;

public class Test1 {
	public static void main(String[] args) {
		Part user = new Part();
		ObjectDao dao = new ObjectDao();
		user.setId(1);
		Part user1 = new Part();
		user1.setId(2);
		System.out.println(dao.Select(user));
	}
}
