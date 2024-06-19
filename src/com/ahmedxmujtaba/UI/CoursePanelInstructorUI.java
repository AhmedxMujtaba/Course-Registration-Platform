package com.ahmedxmujtaba.UI;
import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.LectureDAO;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Instructor;
import com.ahmedxmujtaba.Entities.Lecture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoursePanelInstructorUI extends JFrame {
    private Course course;
    private Instructor instructor;

    public CoursePanelInstructorUI(Course course, Instructor instructor) {
        this.course = course;
        this.instructor = instructor;

        ImageIcon icon = new ImageIcon(("src/com/ahmedxmujtaba/UI/Icons/icon1.png"));
        setIconImage(icon.getImage());

        setTitle("Course Details");
        setSize(900, 600);
        setBackground(Color.darkGray);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set font
        Font customFont = new Font("04b03", Font.PLAIN, 24);

        // Create components
        JLabel nameLabel = new JLabel("Name: " + course.getName());
        JLabel descriptionLabel = new JLabel("Description:");
        JTextArea descriptionArea = new JTextArea(course.getDescription());
        descriptionArea.setEditable(false); // Make the description area non-editable
        descriptionArea.setLineWrap(true); // Enable automatic text wrapping
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        JLabel priceLabel = new JLabel("Price: Rs " + course.getPrice());
        JButton createLectureButton = new JButton("Create Lecture");
        JButton editCourseDetailsButton = new JButton("Edit Course Details");
        JButton backButton = new JButton("Back");

        // Set font for labels and buttons
        nameLabel.setFont(customFont);
        descriptionLabel.setFont(customFont);
        descriptionArea.setFont(customFont);
        priceLabel.setFont(customFont);
        createLectureButton.setFont(customFont);
        editCourseDetailsButton.setFont(customFont);
        backButton.setFont(customFont);

        // Set button properties
        createLectureButton.setBackground(Color.WHITE);
        createLectureButton.setFocusPainted(false);
        createLectureButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,3)); // Black border

        editCourseDetailsButton.setBackground(Color.WHITE);
        editCourseDetailsButton.setFocusPainted(false);
        editCourseDetailsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,3)); // Black border

        backButton.setBackground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,3)); // Black border

        // Set panel background color
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.WHITE);


        // Add action listener to create lecture button
        createLectureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LectureCreationUI lectureCreationUI = new LectureCreationUI(course, instructor);
                lectureCreationUI.setVisible(true);
            }
        });


        // Add action listener to edit course details button
        editCourseDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CourseEditUI(course,instructor).setVisible(true);
            }
        });

        // Add action listener to back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window and go back to Instructor UI
                dispose();
                InstructorPortalUI instructorPortalUI = new InstructorPortalUI(instructor);
                instructorPortalUI.setVisible(true);
            }
        });

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add components to the frame using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(nameLabel, gbc);

        gbc.gridy++;
        add(descriptionLabel, gbc);

        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(descriptionScrollPane, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(priceLabel, gbc);

        gbc.gridy++;
        add(createLectureButton, gbc);

        gbc.gridy++;
        add(editCourseDetailsButton, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(backButton, gbc);

        // Create a side panel to show lectures
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createTitledBorder("Lectures"));
        LectureDAO lectureDAO = new LectureDAO(new DataBaseLink());
        ArrayList<Lecture> lectures = null;
        try {
            lectures = lectureDAO.getLecturesByCourseId(course.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Lecture lecture : lectures) {
            JButton lectureButton = new JButton(lecture.getTitle());
            lectureButton.setFont(customFont);
            lectureButton.setBackground(Color.WHITE);
            lectureButton.setFocusPainted(false);
            lectureButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,2)); // Black border
            lectureButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            lectureButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Open the selected lecture UI
                    dispose();
                    LectureInstructorUI lectureUI = new LectureInstructorUI(course,instructor,lecture);
                    lectureUI.setVisible(true);
                }
            });
            sidePanel.add(lectureButton);
        }

        // Create scroll pane and add a side panel to it
        JScrollPane scrollPane = new JScrollPane(sidePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        // Add scroll pane using GridBagConstraints
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(scrollPane, gbc);
    }
}
