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

    public int getTotalStudents(String courseId, DataBaseLink dbLink) {
        int totalStudents = 0;
        String query = "SELECT COUNT(*) FROM studentcourses WHERE courseId = ?";
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            // Connect to the database
            dbLink.connect();

            // Create the PreparedStatement
            pstmt = dbLink.getConnection().prepareStatement(query);

            // Set the courseId parameter
            pstmt.setString(1, courseId);

            // Execute the query
            rs = pstmt.executeQuery();

            // Process the result
            if (rs.next()) {
                totalStudents = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            // Close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // Disconnect from the database
            dbLink.disconnect();
        }
        return totalStudents;
    }

    public double getCoursePrice(String courseId) {
        Course course = getCourseById(courseId);
        if (course != null) {
            return course.getPrice();
        } else {
            return 0;
        }
    }
    public double calculateIncome(Instructor instructor) {
        double income = 0;
dbLink.connect();
        try {
            // Iterate through each course ID taught by the instructor
            for (String currentCourseId : instructor.getCourseIds()) {
                // Get the total students enrolled in the current course
                int studentCount = getTotalStudents(currentCourseId, dbLink);

                // Get the course price
                double coursePrice = getCoursePrice(currentCourseId);

                // Calculate income for the current course
                income += coursePrice * studentCount;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        dbLink.disconnect();
        return income;
    }
}
