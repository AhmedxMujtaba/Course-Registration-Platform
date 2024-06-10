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

    // Method to check the number of students enrolled in a course
    public void setIncome(double income) {
        this.income = income;
    }

    public double getIncome() {
        return income;
    }

    public ArrayList<String> getCourseIds() {
        return courseIds;
    }

}
