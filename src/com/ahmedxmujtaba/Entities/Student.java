package com.ahmedxmujtaba.Entities;

import java.util.ArrayList;

public class Student extends User {
    private ArrayList<Integer> registeredCoursesIds;

    // Constructor
    public Student(int id,String name, String password, String email, int phoneNumber) {
        super(id, name, password, email, phoneNumber);

    }
    public void setRegisteredCourses(ArrayList<Integer> registeredCourses) {
        this.registeredCoursesIds = registeredCourses;
    }

    // Method to get list of registered courses
    public ArrayList<Integer> getCourses() {
        return registeredCoursesIds;
    }
}
