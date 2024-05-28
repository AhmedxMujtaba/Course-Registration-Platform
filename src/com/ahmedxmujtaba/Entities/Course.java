package com.ahmedxmujtaba.Entities;

import java.util.ArrayList;

public class Course {
    private int id;
    private String name;
    private String description;
    private int instructorId;
    private double price;
    private ArrayList<Integer> registeredStudentIDs;

    // Constructor
    public Course(int id, String name, String description, int instructorId, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.instructorId = instructorId;
        this.price = price;
        this.registeredStudentIDs = new ArrayList<>();
    }
    public Course(String name, String description, int instructorId, double price) {

        this.name = name;
        this.description = description;
        this.instructorId = instructorId;
        this.price = price;
        this.registeredStudentIDs = new ArrayList<>();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Integer> getRegisteredStudentIDs() {
        return registeredStudentIDs;
    }
    public int getNoOfStudents(){
        return registeredStudentIDs.size();
    }

    public void addRegisteredStudentID(int studentID) {
        registeredStudentIDs.add(studentID);
    }

    public void removeRegisteredStudentID(int studentID) {
        registeredStudentIDs.remove((Integer) studentID);
    }

}
