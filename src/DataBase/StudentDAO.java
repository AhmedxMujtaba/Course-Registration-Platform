package DataBase;

import Entities.Student;
import Entities.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {
    private DataBaseLink dbLink;

    public StudentDAO(DataBaseLink dbLink) {
        this.dbLink = dbLink;
    }

    public void addStudent(Student student) {
        // First, add the user to the Users table
        String query = "INSERT INTO Users (name, password, email, phoneNumber) VALUES ('"
                + student.getName() + "', '"
                + student.getPassword() + "', '"
                + student.getEmail() + "', "
                + student.getPhoneNumber() + ")";
        dbLink.connect();
        int userId = dbLink.executeUpdateAndGetGeneratedKeys(query);

        // Add the student to the Students table
        query = "INSERT INTO Students (userId) VALUES (" + userId + ")";
        dbLink.executeUpdate(query);
        dbLink.disconnect();
    }

    public Student getStudentById(int userId) {
        String query = "SELECT * FROM Users WHERE id = " + userId;
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);
        Student student = null;
        try {
            if (rs.next()) {
                student = new Student(
                        userId,
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("phoneNumber")
                );
                // Load registered courses for the student
                loadRegisteredCourses(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return student;
    }


    private void loadRegisteredCourses(Student student) {
        String query = "SELECT courseId FROM StudentCourses WHERE studentId = " + student.getId();
        ResultSet rs = dbLink.executeQuery(query);
        ArrayList<Course> courses = new ArrayList<>();
        try {
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                // Assuming you have a CourseDAO to fetch course details
                CourseDAO courseDAO = new CourseDAO(dbLink);
                Course course = courseDAO.getCourseById(courseId);
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        student.setRegisteredCourses(courses);
    }

    public void registerCourse(Student student, Course course) {
        String query = "INSERT INTO StudentCourses (studentId, courseId) VALUES ("
                + student.getId() + ", "
                + course.getId() + ")";
        dbLink.connect();
        dbLink.executeUpdate(query);
        dbLink.disconnect();
    }

    public void unregisterCourse(Student student, Course course) {
        String query = "DELETE FROM StudentCourses WHERE studentId = "
                + student.getId() + " AND courseId = " + course.getId();
        dbLink.connect();
        dbLink.executeUpdate(query);
        dbLink.disconnect();
    }
}
