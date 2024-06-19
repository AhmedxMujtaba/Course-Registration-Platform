# Course Registration Platform

 <img src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/4df24b71-fe7e-4918-8701-60bf33f17c2e" alt="config.properties">

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Configuration](#configuration)
- [Diagrams](#diagrams)
- [UI](#ui)
- [Contributors](#contributors)
- [License](#license)
- [Contact](#contact)

## Introduction
A simple GUI Based Application for creatition of courses and helping data to be centralized on a single platform for students to study from easily
It uses Java as the base back-end and Java Swing components for the front-end. MySQL database is also integrated using DAO (Data Access Object) classes
are used to interact with it

## Features
- Sign Up as Instructor or Student
- Make courses or register for them
- UI using Java Swing
- Data Base using MySQL
- Log and config files
- Password Hashing
- SQL Injection Prevention
- Font Used: 04b03

## Installation

### Prerequisites
- [Java](https://www.java.com/en/) - Requires Java

### Clone the Repository
```sh
git clone https://github.com/AhmedxMujtaba/Course-Registration-Platform.git
cd Course-Registration-Platform
```
## Configuration

- Set up the `config.properties` file as per your database configuration.
  
 <img width="500" src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/07919885-54f3-4129-8619-69a0249d1dd1" width = "600">

- Make sure the database is running when using the application.

### SQL Code

```sql
CREATE DATABASE course_registration_platform;

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phoneNumber INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Students (
    userId INT PRIMARY KEY,
    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Instructors (
    userId INT PRIMARY KEY,
    income DOUBLE DEFAULT 0,
    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    instructorId INT,
    price DOUBLE,
    FOREIGN KEY (instructorId) REFERENCES Instructors(userId) ON DELETE CASCADE
);

CREATE TABLE StudentCourses (
    studentId INT,
    courseId INT,
    PRIMARY KEY (studentId, courseId),
    FOREIGN KEY (studentId) REFERENCES Students(userId) ON DELETE CASCADE,
    FOREIGN KEY (courseId) REFERENCES Courses(id) ON DELETE CASCADE
);

CREATE TABLE Lectures (
    id INT AUTO_INCREMENT PRIMARY KEY,
    courseId INT,
    title VARCHAR(255) NOT NULL,
    FOREIGN KEY (courseId) REFERENCES Courses(id) ON DELETE CASCADE
);

CREATE TABLE Notes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    notes TEXT,
    lecture_id INT,
    FOREIGN KEY (lecture_id) REFERENCES Lectures(id) ON DELETE CASCADE
);

CREATE TABLE Video (
    id INT AUTO_INCREMENT PRIMARY KEY,
    link VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    lecture_id INT,
    FOREIGN KEY (lecture_id) REFERENCES Lectures(id) ON DELETE CASCADE
);

```

## Diagrams

### Class Diagram

<img src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/35b4f62f-649a-4bad-85d1-af9007cad076">

### Entity Relationship Diagram

<img width="500" src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/1911e110-da9a-4bda-b149-a448bd16a675">

## UI

### Login

<img width="500" alt="image" src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/0f678aa1-62b2-47cb-8550-3b56abef6572">


### Sign Up

<img width="500" alt="image" src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/123f823c-2018-4ce6-a721-2b3726adb6e6">

### Instructor Home Page

<img width="500" alt="image" src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/f67a9946-65fe-4358-89be-0ab33d44e231">

### Course Creation 

<img width="500" alt="image" src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/4c727b4f-acaf-4d02-966a-6ce6c6a9f232">

### Lecture Creation

<img width="500" alt="image" src="https://github.com/AhmedxMujtaba/Course-Registration-Platform/assets/121884030/fb25f265-fe08-452e-b112-e9364b986c97">

## Contributors

- **Ahmed Mujtaba** - *Programmer* - [Ahmed Mujtaba](https://github.com/AhmedxMujtaba)
- **Chat GPT** - *AI Assistant* - [Chat GPT](https://chatgpt.com)

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/contribution`)
3. Make your changes
4. Commit your changes (`git commit -am 'Add some feature'`)
5. Push to the branch (`git push origin feature/contribution`)
6. Create a new Pull Request

## License

The code in this repository is licensed under the MIT License.

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for more details.

### Permissions

- Commercial use [X]
- Modification [O]
- Distribution [X]
- Private use [O]

## Contact

For any inquiries or feedback, feel free to reach out:

- Email: [ahmedmujtaba.ult@gmail.com](mailto:ahmedmujtaba.ult@gmail.com)
- LinkedIn: [ahmed-x-mujtaba](https://www.linkedin.com/in/ahmed-x-mujtaba/)


