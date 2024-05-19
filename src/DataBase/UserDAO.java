package DataBase;

import Entities.User;
import Entities.Instructor;
import Entities.Student;

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
        return dbLink.executeUpdateAndGetGeneratedKeys(query);
    }

    public void addStudent(User user) {
        int userId = addUser(user);
        user.setId(userId);
        String studentQuery = "INSERT INTO Students (userId) VALUES (" + userId + ")";
        dbLink.connect();
        dbLink.executeUpdate(studentQuery);
        dbLink.disconnect();
    }

    public void addInstructor(User user) {
        int userId = addUser(user);
        user.setId(userId);
        String instructorQuery = "INSERT INTO Instructors (userId, income) VALUES (" + userId + ", 0)";
        dbLink.connect();
        dbLink.executeUpdate(instructorQuery);
        dbLink.disconnect();
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


    private boolean isInstructor(int userId) {
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

    private boolean isStudent(int userId) {
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
}
