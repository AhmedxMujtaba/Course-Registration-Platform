package com.ahmedxmujtaba.Entities;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Integer> registeredCoursesIds;

    // Constructor
    public Student(int id,String name, String password, String email, int phoneNumber) {
        super(id, name, password, email, phoneNumber);

    }

    // Getter and setter for registeredCourses
    public ArrayList<Integer> getRegisteredCourses() {
        return registeredCoursesIds;
    }

    public void setRegisteredCourses(ArrayList<Integer> registeredCourses) {
        this.registeredCoursesIds = registeredCourses;
    }

    // Method to register for a course
    public void registerCourse(Course course) {
        registeredCoursesIds.add(course.getId());
    }

    // Method to unregister from a course
    public void unregisterCourse(Course course) {
        registeredCoursesIds.remove(course.getId());
    }

    // Method to get list of registered courses
    public ArrayList<Integer> getCourses() {
        return registeredCoursesIds;
    }
}
