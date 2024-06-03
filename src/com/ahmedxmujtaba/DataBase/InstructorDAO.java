package com.ahmedxmujtaba.DataBase;

import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Instructor;

import java.sql.*;
import java.util.ArrayList;

public class InstructorDAO {
    private DataBaseLink dbLink;

    public InstructorDAO() {
        dbLink = new DataBaseLink();
    }

    public Instructor getInstructorById(int userId) {
        Instructor instructor = null;
        try {
            dbLink.connect();
            String query = "SELECT u.id, u.name, u.password, u.email, u.phoneNumber, i.income FROM users u JOIN instructors i ON u.id = i.userId WHERE u.id = ?";
            try (Connection connection = dbLink.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    instructor = new Instructor(rs.getInt("id"), rs.getString("name"), rs.getString("password"),
                            rs.getString("email"), rs.getInt("phoneNumber"), rs.getDouble("income"));
                    ArrayList<String> courseIds = getCoursesByInstructorId(userId);
                    for (String courseId : courseIds) {
                        instructor.addCourseID(courseId);
                    }
                    double income = calculateIncome(instructor);
                    instructor.setIncome(income);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbLink.disconnect();
        }
        return instructor;
    }

    public ArrayList<String> getCoursesByInstructorId(int instructorId) {
        dbLink.connect();
        ArrayList<String> courseIds = new ArrayList<>();
        String query = "SELECT id FROM courses WHERE instructorId = ?";
        try (Connection connection = dbLink.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, instructorId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courseIds.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return courseIds;
    }

    public Course getCourseById(String courseId) {
        dbLink.connect();
        Course course = null;
        String query = "SELECT * FROM courses WHERE id = ?";
        try (Connection connection = dbLink.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                course = new Course(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                        rs.getInt("instructorId"), rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return course;
    }

    public double calculateIncome(Instructor instructor) {
        // Connect to the database
        dbLink.connect();
        double income = 0;
        String query = "SELECT COUNT(*) FROM StudentCourses WHERE courseId = ?";

        try (Connection connection = dbLink.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Iterate through each course ID taught by the instructor
            for (String currentCourseId : instructor.getCourseIds()) {
                // Set the course ID parameter in the query
                pstmt.setInt(1, Integer.parseInt(currentCourseId));
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Get the number of students enrolled in the current course
                        int studentCount = rs.getInt(1);

                        // Fetch the course details to get the price
                        Course course = getCourseById(currentCourseId);
                        if (course != null) {
                            // Calculate income for the current course
                            income += course.getPrice() * studentCount;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            dbLink.disconnect();
        }

        return income;
    }

}
