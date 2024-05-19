package DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Entities.Course;

public class CourseDAO {
    private DataBaseLink dbLink;

    public CourseDAO(DataBaseLink dbLink) {
        this.dbLink = dbLink;
    }

    public void addCourse(Course course) {
        String query = "INSERT INTO Courses (name, description, instructorId, price) VALUES ('"
                + course.getName() + "', '"
                + course.getDescription() + "', "
                + course.getInstructorId() + ", "
                + course.getPrice() + ")";
        dbLink.connect();
        dbLink.executeUpdate(query);
        dbLink.disconnect();
    }

    public Course getCourseById(int id) {
        String query = "SELECT * FROM Courses WHERE id = " + id;
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);
        Course course = null;
        try {
            if (rs.next()) {
                course = new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("instructorId"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return course;
    }

    public ArrayList<Integer> getRegisteredStudentsForCourse(int courseId) {
        ArrayList<Integer> registeredStudents = new ArrayList<>();
        String query = "SELECT studentId FROM StudentCourses WHERE courseId = " + courseId;
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);
        try {
            while (rs.next()) {
                int studentId = rs.getInt("studentId");
                registeredStudents.add(studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return registeredStudents;
    }
    public void registerStudentForCourse(int courseId, int studentId) {
        String query = "INSERT INTO StudentCourses (courseId, studentId) VALUES (" + courseId + ", " + studentId + ")";
        dbLink.connect();
        dbLink.executeUpdate(query);
        dbLink.disconnect();
    }

    public void unregisterStudentFromCourse(int courseId, int studentId) {
        String query = "DELETE FROM StudentCourses WHERE courseId = " + courseId + " AND studentId = " + studentId;
        dbLink.connect();
        dbLink.executeUpdate(query);
        dbLink.disconnect();
    }
}




