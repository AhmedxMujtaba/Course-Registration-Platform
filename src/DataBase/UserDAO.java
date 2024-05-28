package DataBase;

import Entities.User;
import Entities.Instructor;
import Entities.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private DataBaseLink dbLink;

    public UserDAO(DataBaseLink dbLink) {
        this.dbLink = dbLink;
    }

    public int addUser(User user) {
        String query = "INSERT INTO Users (name, password, email, phoneNumber) VALUES ('"
                + user.getName() + "', '"
                + user.getPassword() + "', '"
                + user.getEmail() + "', "
                + user.getPhoneNumber() + ")";
        try {
            return dbLink.executeUpdateAndGetGeneratedKeys(query);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return -1; // Return -1 to indicate failure
        }
    }

    public boolean addStudent(User user) {
        if (emailExists(user.getEmail())) {
            System.out.println("Email already exists.");
            return false; // Email already exists
        }

        try {
            int userId = addUser(user);
            if (userId == -1) {
                return false; // Adding user failed
            }
            user.setId(userId);
            String studentQuery = "INSERT INTO Students (userId) VALUES (" + userId + ")";
            dbLink.connect();
            dbLink.executeUpdate(studentQuery);
            dbLink.disconnect();
            return true; // Successfully added student
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return false; // Indicate failure
        }
    }


    public boolean addInstructor(User user) {
        if (emailExists(user.getEmail())) {
            System.out.println("Email already exists.");
            return false; // Email already exists
        }

        try {
            int userId = addUser(user);
            if (userId == -1) {
                return false; // Adding user failed
            }
            user.setId(userId);
            String instructorQuery = "INSERT INTO Instructors (userId, income) VALUES (" + userId + ", 0)";
            dbLink.connect();
            dbLink.executeUpdate(instructorQuery);
            dbLink.disconnect();
            return true; // Successfully added instructor
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return false; // Indicate failure
        }
    }

    public boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM Users WHERE email = '" + email + "'";
        try {
            dbLink.connect();
            ResultSet rs = dbLink.executeQuery(query);
            rs.next();
            int count = rs.getInt(1);
            dbLink.disconnect();
            return count > 0; // If count is greater than 0, email exists
        } catch (Exception e) {
            e.printStackTrace();
            dbLink.disconnect();
            return false; // In case of an error, treat it as email existing to avoid duplicates
        }
    }

    public User getUserByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = '" + email + "'";
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);
        User user = null;
        try {
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("phoneNumber")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return user;
    }

    public User getUserDetails(User user) {
        if (isInstructor(user.getId())) {
            String query = "SELECT * FROM Instructors INNER JOIN Users ON Instructors.userId = Users.id WHERE Instructors.userId = " + user.getId();
            dbLink.connect();
            ResultSet rs = dbLink.executeQuery(query);
            try {
                if (rs.next()) {
                    return new Instructor(user.getId(),
                            rs.getString("name"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getInt("phoneNumber"),
                            rs.getDouble("income"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (isStudent(user.getId())) {
            return new Student(user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getPhoneNumber());
        }
        dbLink.disconnect();
        return user;
    }


    public boolean isInstructor(int userId) {
        String query = "SELECT * FROM Instructors WHERE userId = " + userId;
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);
        boolean isInstructor = false;
        try {
            isInstructor = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return isInstructor;
    }

    public boolean isStudent(int userId) {
        String query = "SELECT * FROM Students WHERE userId = " + userId;
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);
        boolean isStudent = false;
        try {
            isStudent = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return isStudent;
    }

    public int getUserIdByEmail(String email) {
        String query = "SELECT id FROM Users WHERE email = '" + email + "'";
        dbLink.connect();
        ResultSet rs = dbLink.executeQuery(query);
        int userId = -1;
        try {
            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
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
        String query = "UPDATE Users SET password = ? WHERE id = ?";
        try {
            dbLink.connect();
            PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query);
            pstmt.setString(1, user.getPassword());
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
        String query = "DELETE FROM Users WHERE id = ?";
        try {
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


}
