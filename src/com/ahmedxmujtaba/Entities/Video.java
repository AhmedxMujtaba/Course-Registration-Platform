package com.ahmedxmujtaba.Entities;

public class Video {
    private int id;
    private String link;
    private String title;
    private String description;
    private int lectureId;

    // Constructor
    public Video(String link, String title, String description, int lectureId) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.lectureId = lectureId;
    }
    public Video(int id,String link, String title, String description, int lectureId) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
        this.lectureId = lectureId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }
}
