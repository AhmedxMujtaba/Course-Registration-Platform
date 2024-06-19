package com.ahmedxmujtaba.DataBase;

import com.ahmedxmujtaba.Entities.Lecture;
import com.ahmedxmujtaba.Entities.User;
import com.ahmedxmujtaba.Entities.Instructor;
import com.ahmedxmujtaba.Entities.Student;
import com.ahmedxmujtaba.Security.PasswordHasher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO {
    private DataBaseLink dbLink;

    public UserDAO(DataBaseLink dbLink) {
        this.dbLink = dbLink;
    }

    public int addUser(User user) {
        String hashedPassword = PasswordHasher.hashPassword(user.getPasswordHash());
        String query = "INSERT INTO Users (name, password_hash, email, phoneNumber) VALUES (?, ?, ?, ?)";
        try {
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, hashedPassword);
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getPhoneNumber());
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            dbLink.disconnect();
        }
    }

    public boolean addStudent(User user) {
        if (emailExists(user.getEmail())) {
            System.out.println("Email already exists.");
            return false;
        }

        try {
            int userId = addUser(user);
            if (userId == -1) {
                return false;
            }
            user.setId(userId);
            String studentQuery = "INSERT INTO Students (userId) VALUES (?)";
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(studentQuery);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            dbLink.disconnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addInstructor(User user) {
        if (emailExists(user.getEmail())) {
            System.out.println("Email already exists.");
            return false;
        }

        try {
            int userId = addUser(user);
            if (userId == -1) {
                return false;
            }
            user.setId(userId);
            String instructorQuery = "INSERT INTO Instructors (userId, income) VALUES (?, 0)";
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(instructorQuery);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
            dbLink.disconnect();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM Users WHERE email = ?";
        try {
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            dbLink.disconnect();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            dbLink.disconnect();
            return false;
        }
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        dbLink.connect();
        User user = null;
        try {
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password_hash"),
                        rs.getString("email"),
                        rs.getInt("phoneNumber")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbLink.disconnect();
        }
        return user;
    }

    public User getUserDetails(User user) {
        if (isInstructor(user.getId())) {
            String query = "SELECT * FROM Instructors INNER JOIN Users ON Instructors.userId = Users.id WHERE Instructors.userId = ?";
            dbLink.connect();
            try {
                PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
                pstmt.setInt(1, user.getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return new Instructor(user.getId(),
                            rs.getString("name"),
                            rs.getString("password_hash"),
                            rs.getString("email"),
                            rs.getInt("phoneNumber"),
                            rs.getDouble("income"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                dbLink.disconnect();
            }
        } else if (isStudent(user.getId())) {
            return new Student(user.getId(), user.getName(), user.getPasswordHash(), user.getEmail(), user.getPhoneNumber());
        }
        return user;
    }

    public boolean isInstructor(int userId) {
        String query = "SELECT * FROM Instructors WHERE userId = ?";
        dbLink.connect();
        boolean isInstructor = false;
        try {
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            isInstructor = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbLink.disconnect();
        }
        return isInstructor;
    }

    public boolean isStudent(int userId) {
        String query = "SELECT * FROM Students WHERE userId = ?";
        dbLink.connect();
        boolean isStudent = false;
        try {
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            isStudent = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbLink.disconnect();
        }
        return isStudent;
    }

    public int getUserIdByEmail(String email) {
        String query = "SELECT id FROM Users WHERE email = ?";
        dbLink.connect();
        int userId = -1;
        try {
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbLink.disconnect();
        }
        return userId;
    }

    public boolean updateUserName(User user) {
        String query = "UPDATE Users SET name = ? WHERE id = ?";
        try {
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getId());
            int rowsAffected = pstmt.executeUpdate();
            dbLink.disconnect();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserPassword(User user) {
        String hashedPassword = PasswordHasher.hashPassword(user.getPasswordHash());
        String query = "UPDATE Users SET password_hash = ? WHERE id = ?";
        try {
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setString(1, hashedPassword);
            pstmt.setInt(2, user.getId());
            int rowsAffected = pstmt.executeUpdate();
            dbLink.disconnect();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserPhoneNumber(User user) {
        String query = "UPDATE Users SET phoneNumber = ? WHERE id = ?";
        try {
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setInt(1, user.getPhoneNumber());
            pstmt.setInt(2, user.getId());
            int rowsAffected = pstmt.executeUpdate();
            dbLink.disconnect();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(User user) {
        try {
            LectureDAO lectureDAO = new LectureDAO(dbLink);
            List<Lecture> lectures = lectureDAO.getLecturesByCourseId(user.getId());
            for (Lecture lecture : lectures) {
                boolean notesDeleted = lectureDAO.deleteNotesByLectureId(lecture.getId());
                boolean videosDeleted = lectureDAO.deleteVideosByLectureId(lecture.getId());
                if (!notesDeleted || !videosDeleted) {
                    System.out.println("Failed to delete notes or videos for lecture ID: " + lecture.getId());
                    return false;
                }
                boolean lectureDeleted = lectureDAO.deleteLectureById(lecture.getId());
                if (!lectureDeleted) {
                    System.out.println("Failed to delete lecture ID: " + lecture.getId());
                    return false;
                }
            }

            String query = "DELETE FROM Users WHERE id = ?";
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setInt(1, user.getId());
            int rowsAffected = pstmt.executeUpdate();
            dbLink.disconnect();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
