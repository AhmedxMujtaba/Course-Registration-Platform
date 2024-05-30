package com.ahmedxmujtaba.UI;
import com.ahmedxmujtaba.DataBase.*;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Instructor;
import com.ahmedxmujtaba.Entities.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseDetailsUI extends JFrame {
    private Course course;
    private int userId;
    private Student student;
    private Instructor instructor;
    private final UserDAO userDAO = new UserDAO(new DataBaseLink());
    private final StudentDAO studentDAO = new StudentDAO(new DataBaseLink());

    private final InstructorDAO instructorDAO = new InstructorDAO();
    private final CourseDAO courseDAO = new CourseDAO(new DataBaseLink());

    public CourseDetailsUI(Course course, int userId) {
        this.course = course;
        this.userId = userId;
        //call method to get the user or instructor
        findUserType();

        setTitle("Course Details");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set font
        Font customFont = new Font("04b03", Font.PLAIN, 24);

        // Panel for course name
        JPanel courseNamePanel = new JPanel();
        JLabel courseNameLabel = new JLabel(course.getName());
        courseNameLabel.setFont(customFont);
        courseNamePanel.add(courseNameLabel);

        // Panel for course details
        JPanel courseDetailsPanel = new JPanel();
        courseDetailsPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 0 rows means unlimited rows, 2 columns, gap of 10 between components
        courseDetailsPanel.setBackground(Color.WHITE); // Set background color to white

        // Labels and fields for course details
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(customFont);
        JTextArea descriptionArea = new JTextArea(course.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setFont(customFont);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setBackground(Color.WHITE); // Set background color to white

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(customFont);
        JTextField priceField = new JTextField("Rs " + course.getPrice());
        priceField.setEditable(false);
        priceField.setFont(customFont);
        priceField.setBackground(Color.WHITE); // Set background color to white

        JLabel instructorLabel = new JLabel("Instructor:");
        instructorLabel.setFont(customFont);
        JTextField instructorField = new JTextField(getInstructorName(course.getInstructorId()));
        instructorField.setEditable(false);
        instructorField.setFont(customFont);
        instructorField.setBackground(Color.WHITE); // Set background color to white

        // Add labels and fields to course details panel
        courseDetailsPanel.add(descriptionLabel);
        courseDetailsPanel.add(descriptionScrollPane);
        courseDetailsPanel.add(priceLabel);
        courseDetailsPanel.add(priceField);
        courseDetailsPanel.add(instructorLabel);
        courseDetailsPanel.add(instructorField);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE); // Set background color to white
        JButton backButton = new JButton("Back");
        backButton.setFont(customFont);
        backButton.addActionListener(new ActionListener() {
            //todo add functionality here
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (isStudent()){

                }
                else {

                }
            }
        });
        buttonPanel.add(backButton);

        // Add panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE); // Set background color to white
        getContentPane().add(courseNamePanel, BorderLayout.NORTH);
        getContentPane().add(courseDetailsPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private String getInstructorName(int instructorId) {
        if (instructor != null) {
            return instructor.getName();
        } else {
            return "Unknown";
        }
    }

    private boolean isStudent() {
        return student != null;
    }

    private void findUserType(){
        if (userDAO.isStudent(userId)){
            this.student = studentDAO.getStudentById(userId);
            instructor = null;
        }
        else if (userDAO.isInstructor(userId)){
            this.instructor = instructorDAO.getInstructorById(userId);
            student = null;
        }
    }
}
