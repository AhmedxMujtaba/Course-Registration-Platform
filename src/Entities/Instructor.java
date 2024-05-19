package Entities;

import java.util.ArrayList;

public class Instructor extends User {
    // Attributes
    private ArrayList<String> courses;
    private double income;

    // Constructor
    public Instructor(int id, String name, String password, String email, int phoneNumber, double income) {
        super(id, name, password, email, phoneNumber);
        this.courses = new ArrayList<>();
        this.income = income;
    }

    // Add course IDs when a new course is made.
    public void addCourseID(String id) {
        courses.add(id);
    }

    // Method to delete a course
    public void deleteCourse(Course course) {
        courses.remove(course);
    }

    // Method to check the number of students enrolled in a course
    private int checkStudentsNo(Course course) {
        // Assuming Course class has a method getEnrolledStudents() returning a list of students
        return course.getNoOfStudents();
    }

    // Method to check the income generated from a course
    public double checkIncome(Course course) {
        // Assuming Course class has a method getPrice() returning the price of the course
        return course.getPrice() * checkStudentsNo(course);
    }

    public static class Video {
        private String link;
        private String title;
        private String description;

        public Video(String link, String title, String description) {
            this.link = link;
            this.title = title;
            this.description = description;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
