package DataBase;

import Entities.Lecture;
import Entities.Video;
import Entities.Notes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LectureDAO {
    private final DataBaseLink dbLink;

    public LectureDAO(DataBaseLink dbLink) {
        this.dbLink = dbLink;
    }

    public int addLecture(Lecture lecture) {
        dbLink.connect();
        String query = String.format("INSERT INTO lectures (courseId, title) VALUES (%d, '%s')",
                lecture.getCourseId(), lecture.getTitle());

        int generatedKey = dbLink.executeUpdateAndGetGeneratedKeys(query);
        dbLink.disconnect();
        return generatedKey;
    }

    public void saveLecture(Lecture lecture) {
        Connection connection = dbLink.getConnection();
        try {
            // Insert lecture
            String insertLectureQuery = "INSERT INTO lectures (courseId, title) VALUES (?, ?)";
            try (PreparedStatement lectureStmt = connection.prepareStatement(insertLectureQuery, Statement.RETURN_GENERATED_KEYS)) {
                lectureStmt.setInt(1, lecture.getCourseId());
                lectureStmt.setString(2, lecture.getTitle());
                lectureStmt.executeUpdate();

                // Get generated lecture ID
                try (ResultSet rs = lectureStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int lectureId = rs.getInt(1);
                        lecture.setId(lectureId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Lecture> getLecturesByCourseId(int courseId) throws SQLException {
        List<Lecture> lectures = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            dbLink.connect();
            connection = dbLink.getConnection(); // Assuming dbLink provides a connection
            String query = "SELECT id, title FROM lectures WHERE courseId = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, courseId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                lectures.add(new Lecture(id, courseId, title));
            }
        } catch (SQLException e) {
            throw e; // Re-throw the SQLException for handling at the calling code
        } finally {
            // Close resources in reverse order of their creation
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        dbLink.disconnect();
        return lectures;
    }



    public List<Notes> getNotesByLectureId(int lectureId) {
        List<Notes> notes = new ArrayList<>();
        dbLink.connect();
        Connection connection = dbLink.getConnection();
        String query = "SELECT id, title, notes FROM notes WHERE lecture_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, lectureId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int noteId = rs.getInt("id");
                    String title = rs.getString("title");
                    String content = rs.getString("notes");
                    notes.add(new Notes(noteId, title, content,lectureId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return notes;
    }

    public List<Video> getVideosByLectureId(int lectureId) {
        List<Video> videos = new ArrayList<>();
        dbLink.connect();
        Connection connection = dbLink.getConnection();
        String query = "SELECT id, link, title, description FROM video WHERE lecture_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, lectureId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int videoId = rs.getInt("id");
                    String link = rs.getString("link");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    videos.add(new Video(videoId, link, title, description,lectureId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbLink.disconnect();
        return videos;
    }

    public void addNoteToLecture(int lectureId, String title, String notes) {
        try {
            // Open the database connection
            dbLink.connect();

            Connection connection = dbLink.getConnection();
            String query = "INSERT INTO notes (title, notes, lecture_id) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, title);
                stmt.setString(2, notes);
                stmt.setInt(3, lectureId);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the database connection
                dbLink.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void addVideoToLecture(int lectureId, String videoLink, String title, String description) {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            dbLink.connect();
            connection = dbLink.getConnection();
            String query = "INSERT INTO video (lecture_id, link, title, description) VALUES (?, ?, ?, ?)";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, lectureId);
            stmt.setString(2, videoLink);
            stmt.setString(3, title);
            stmt.setString(4, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        dbLink.disconnect();
    }
}
