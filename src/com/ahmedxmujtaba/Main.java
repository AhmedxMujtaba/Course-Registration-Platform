package com.ahmedxmujtaba;//import DataBase.CourseDAO;
//import DataBase.DataBaseLink;
//import DataBase.StudentDAO;
//import Entities.Course;
//import Entities.Student;
//import UI.LoginUI;
//import UI.StartingUI;
//
//import javax.swing.*;
//
//public class Main {
//    public static void main(String[] args) {
//
//        // Display login UI
//        SwingUtilities.invokeLater(() -> {
//            StartingUI loginUI = new StartingUI();
//            loginUI.setVisible(true);
//        });
//
//    }
//}
import com.ahmedxmujtaba.DataBase.CourseDAO;
import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.LectureDAO;
import com.ahmedxmujtaba.DataBase.UserDAO;
import com.ahmedxmujtaba.Entities.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Create a database connection
        DataBaseLink dbLink = new DataBaseLink();

        // Initialize DAOs
        CourseDAO courseDAO = new CourseDAO(dbLink);
        LectureDAO lectureDAO = new LectureDAO(dbLink);
        UserDAO userDAO = new UserDAO(dbLink);
        User niga = new User("Ahmed Ali","12345678","abc1@1abc.com",0121212);
        userDAO.addInstructor(niga);
        int nigaID = userDAO.getUserIdByEmail(niga.getEmail());

        // Add a new course
        Course course = new Course("Balls", "I teach to play with balls",nigaID,1023200);
        courseDAO.addCourse(course);

        // Retrieve the course by name
        Course retrievedCourse = courseDAO.getCourseByName("Balls");
        if (retrievedCourse != null) {
            // Get the course ID
            int courseId = retrievedCourse.getId();

            // Add a new lecture for the course
            Lecture lecture = new Lecture(courseId, "Introduction to Over World");
            int lectureId = lectureDAO.addLecture(lecture);

            // Add notes to the lecture
            lectureDAO.addNoteToLecture(lectureId, "Craft Item","Crafting is all about how to make the items and shit you know");
            lectureDAO.addNoteToLecture(lectureId, "Mob 101","All About Mobs");

            // Add a video to the lecture
            lectureDAO.addVideoToLecture(lectureId, "https://www.youtube.com/java_video", "Crafting tutorial", "Learn to kill mobs and be a hero!");

            // Retrieve lectures by course ID
            List<Lecture> lectures = lectureDAO.getLecturesByCourseId(courseId);
            for (Lecture l : lectures)
            {
                System.out.println("Lecture ID: " + l.getId());
                System.out.println("Lecture Title: " + l.getTitle());
                System.out.println("Course ID: " + l.getCourseId());

                // Get notes for the lecture
                List<Notes> notes = lectureDAO.getNotesByLectureId(l.getId());
                System.out.println("Notes:");
                for (Notes note : notes) {
                    System.out.println("- " + note.getTitle() + ": " + note.getNotes());
                }

// Get videos for the lecture
                List<Video> videos = lectureDAO.getVideosByLectureId(l.getId());
                System.out.println("Videos:");
                for (Video video : videos) {
                    System.out.println("- " + video.getTitle() + ": " + video.getLink());
                }


                System.out.println();
            }
        } else {
            System.out.println("Course not found.");
        }
    }
}





