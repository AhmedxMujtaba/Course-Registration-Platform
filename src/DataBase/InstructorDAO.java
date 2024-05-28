package DataBase;

import Entities.Course;
import Entities.Instructor;

import java.sql.*;
import java.util.ArrayList;

public class InstructorDAO {
    private DataBaseLink dbLink;

    public InstructorDAO() {
        dbLink = new DataBaseLink();
    }

    public void addInstructor(Instructor instructor) {
        String query = "INSERT INTO instructors (userId, income) VALUES (?, ?)";
        try (Connection connection = dbLink.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, instructor.getId());
            pstmt.setDouble(2, instructor.getIncome());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        // For now, return 0 as requested
        return 0.0;
    }
}
