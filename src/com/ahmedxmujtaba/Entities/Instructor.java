package com.ahmedxmujtaba.Entities;

import java.util.ArrayList;

public class Instructor extends User {
    // Attributes
    private ArrayList<String> courseIds;
    private double income;

    // Constructor
    public Instructor(int id, String name, String password, String email, int phoneNumber, double income) {
        super(id, name, password, email, phoneNumber);
        this.courseIds = new ArrayList<>();
        this.income = income;
    }

    // Add course IDs when a new course is made.
    public void addCourseID(String id) {
        courseIds.add(id);
    }

    // Method to delete a course
    public void deleteCourse(String id) {
        courseIds.remove(id);
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

    public void setIncome(double income) {
        this.income = income;
    }

    public double getIncome() {
        return income;
    }

    public ArrayList<String> getCourseIds() {
        return courseIds;
    }

    public void setCourseIds(ArrayList<String> courseIds) {
        this.courseIds = courseIds;
    }
}
