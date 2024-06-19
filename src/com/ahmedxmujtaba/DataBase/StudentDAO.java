package com.ahmedxmujtaba.DataBase;

import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Student;

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
        String query = "INSERT INTO Users (name, password_hash, email, phoneNumber) VALUES ('"
                + student.getName() + "', '"
                + student.getPasswordHash() + "', '"
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
                        rs.getString("password_hash"),
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
        dbLink.connect();
        String query = "SELECT courseId FROM StudentCourses WHERE studentId = " + student.getId();
        ResultSet rs = dbLink.executeQuery(query);
        ArrayList<Integer> courseIds = new ArrayList<>();
        try {
            while (rs.next()) {
                int courseId = rs.getInt("courseId");
                courseIds.add(courseId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close(); // Close the ResultSet here
                }
                dbLink.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        student.setRegisteredCourses(courseIds);
    }


    public boolean registerCourse(Student student, Course course) {
        // Check if the student is already registered for the course
        String checkQuery = "SELECT * FROM StudentCourses WHERE studentId = " + student.getId() + " AND courseId = " + course.getId();
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(checkQuery);
        try {
            if (rs.next()) {
                dbLink.disconnect();
                return false; // Student is already registered
            }
        } catch (SQLException e) {
            e.printStackTrace();
            dbLink.disconnect();
            return false;
        }

        // Register the student for the course
        String query = "INSERT INTO StudentCourses (studentId, courseId) VALUES ("
                + student.getId() + ", "
                + course.getId() + ")";
        dbLink.executeUpdate(query);
        dbLink.disconnect();
        return true;
    }

    public void unregisterCourse(Student student, Course course) {
        String query = "DELETE FROM StudentCourses WHERE studentId = "
                + student.getId() + " AND courseId = " + course.getId();
        dbLink.connect();
        dbLink.executeUpdate(query);
        dbLink.disconnect();
    }
    public boolean isRegisteredForCourse(Student student, Course course) {
        String query = "SELECT * FROM StudentCourses WHERE studentId = " + student.getId() + " AND courseId = " + course.getId();
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);
        boolean isRegistered = false;
        try {
            if (rs.next()) {
                isRegistered = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return isRegistered;
    }
}
