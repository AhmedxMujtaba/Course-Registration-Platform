package com.ahmedxmujtaba.Entities;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Course> registeredCourses; // To store registered courses

    // Constructor
    public Student(int id,String name, String password, String email, int phoneNumber) {
        super(id, name, password, email, phoneNumber);
        this.registeredCourses = new ArrayList<>();
    }

    // Getter and setter for registeredCourses
    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(ArrayList<Course> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    // Method to register for a course
    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    // Method to unregister from a course
    public void unregisterCourse(Course course) {
        registeredCourses.remove(course);
    }

    // Method to get list of registered courses
    public ArrayList<Course> getCourses() {
        return registeredCourses;
    }
}
