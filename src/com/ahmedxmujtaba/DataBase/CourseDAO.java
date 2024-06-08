package com.ahmedxmujtaba.DataBase;

import com.ahmedxmujtaba.Entities.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private DataBaseLink dbLink;

    public CourseDAO(DataBaseLink dbLink) {
        this.dbLink = dbLink;
    }

    public boolean addCourse(Course course) {
        String query = "INSERT INTO Courses (name, description, instructorId, price) VALUES ('"
                + course.getName() + "', '"
                + course.getDescription() + "', "
                + course.getInstructorId() + ", "
                + course.getPrice() + ")";

        boolean isSuccess = false;
        dbLink.connect();

        try {
            int rowsAffected = dbLink.executeUpdate(query);
            isSuccess = rowsAffected > 0;
        } finally {
            dbLink.disconnect();
        }

        return isSuccess;
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

    public Course getCourseByName(String name) {
        String query = "SELECT * FROM Courses WHERE name = '" + name + "'";
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

    // New method to search courses by name
    public List<Course> searchCoursesByName(String name) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Courses WHERE name LIKE '%" + name + "%'";
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);

        try {
            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("instructorId"),
                        rs.getDouble("price")
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return courses;
    }
    public boolean updateCourse(Course course) {
        String query = "UPDATE Courses SET name = '" + course.getName() + "', " +
                "description = '" + course.getDescription() + "', " +
                "instructorId = " + course.getInstructorId() + ", " +
                "price = " + course.getPrice() + " WHERE id = " + course.getId();
        dbLink.connect();
        boolean isSuccess = false;
        try {
            int rowsAffected = dbLink.executeUpdate(query);
            isSuccess = rowsAffected > 0;
        } finally {
            dbLink.disconnect();
        }
        return isSuccess;
    }

    public boolean deleteCourse(int courseId) {
        String query = "DELETE FROM Courses WHERE id = " + courseId;
        dbLink.connect();
        boolean isSuccess = false;
        try {
            int rowsAffected = dbLink.executeUpdate(query);
            isSuccess = rowsAffected > 0;
        } finally {
            dbLink.disconnect();
        }
        return isSuccess;
    }

}
