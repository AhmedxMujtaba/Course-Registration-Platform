package com.ahmedxmujtaba.DataBase;

import com.ahmedxmujtaba.Entities.Lecture;
import com.ahmedxmujtaba.Entities.Video;
import com.ahmedxmujtaba.Entities.Notes;
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
        dbLink.connect();
        Connection connection = dbLink.getConnection();
        try {
            // Update lecture
            String updateLectureQuery = "UPDATE lectures SET title = ? WHERE id = ?";
            try (PreparedStatement lectureStmt = connection.prepareStatement(updateLectureQuery)) {
                lectureStmt.setString(1, lecture.getTitle());
                lectureStmt.setInt(2, lecture.getId());
                lectureStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbLink.disconnect();
        }
    }


    public ArrayList<Lecture> getLecturesByCourseId(int courseId) throws SQLException {
        ArrayList<Lecture> lectures = new ArrayList<>();
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
        try {
            // Open the database connection
            dbLink.connect();

            Connection connection = dbLink.getConnection();
            String query = "INSERT INTO video (lecture_id, link, title, description) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, lectureId);
                stmt.setString(2, videoLink);
                stmt.setString(3, title);
                stmt.setString(4, description);
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
    public boolean deleteNotesByLectureId(int lectureId) {
        String query = "DELETE FROM notes WHERE lecture_id = ?";
        dbLink.connect();
        try (PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, lectureId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbLink.disconnect();
        }
    }

    public boolean deleteVideosByLectureId(int lectureId) {
        String query = "DELETE FROM video WHERE lecture_id = ?";
        dbLink.connect();
        try (PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, lectureId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbLink.disconnect();
        }
    }

    public boolean deleteLectureById(int lectureId) {
        // First, delete associated notes and videos
        boolean notesDeleted = deleteNotesByLectureId(lectureId);
        boolean videosDeleted = deleteVideosByLectureId(lectureId);

        if (!notesDeleted || !videosDeleted) {
            System.out.println("Failed to delete associated notes or videos for lecture ID: " + lectureId);
            return false;
        }

        // Then, delete the lecture
        String query = "DELETE FROM lectures WHERE id = ?";
        dbLink.connect();
        try (PreparedStatement pstmt = dbLink.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, lectureId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbLink.disconnect();
        }
    }
    public void saveNoteToLecture(Notes note) {
        try {
            // Open the database connection
            dbLink.connect();
            Connection connection = dbLink.getConnection();

            // Prepare the update query
            String query = "UPDATE notes SET title = ?, notes = ? WHERE id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                // Set the parameters for the update query
                stmt.setString(1, note.getTitle());
                stmt.setString(2, note.getNotes());
                stmt.setInt(3, note.getId());

                // Execute the update query
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

    public void saveVideoToLecture(Video video) {
        try {
            // Open the database connection
            dbLink.connect();
            Connection connection = dbLink.getConnection();

            // Prepare the update query
            String query = "UPDATE video SET link = ?, title = ?, description = ? WHERE id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                // Set the parameters for the update query
                stmt.setString(1, video.getLink());
                stmt.setString(2, video.getTitle());
                stmt.setString(3, video.getDescription());
                stmt.setInt(4, video.getId());

                // Execute the update query
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


}
