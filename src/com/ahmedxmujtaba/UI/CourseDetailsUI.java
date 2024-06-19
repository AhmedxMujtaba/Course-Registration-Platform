package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.*;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Instructor;
import com.ahmedxmujtaba.Entities.Lecture;
import com.ahmedxmujtaba.Entities.Student;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CourseDetailsUI extends JFrame {
    private Course course;
    private int userId;
    private Student student;
    private Instructor instructor;
    private final UserDAO userDAO = new UserDAO(new DataBaseLink());
    private final StudentDAO studentDAO = new StudentDAO(new DataBaseLink());
    private final InstructorDAO instructorDAO = new InstructorDAO();
    private final LectureDAO lectureDAO = new LectureDAO(new DataBaseLink());

    public CourseDetailsUI(Course course, int userId) {
        this.course = course;
        this.userId = userId;

        ImageIcon icon = new ImageIcon(("src/com/ahmedxmujtaba/UI/Icons/icon1.png"));
        setIconImage(icon.getImage());

        // Call method to get the user or instructor
        findUserType();

        setTitle(course.getName());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set font
        Font customFont = new Font("04b03", Font.PLAIN, 24);
        Font titleFont = new Font("04b03", Font.BOLD, 32);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BorderLayout());

        // Course name label
        JLabel courseNameLabel = new JLabel(course.getName());
        courseNameLabel.setFont(titleFont);
        courseNameLabel.setHorizontalAlignment(JLabel.CENTER);

        // Instructor and price labels
        JLabel courseInstructorLabel = new JLabel("Instructor: " + getCourseInstructorName(course.getInstructorId()));
        courseInstructorLabel.setFont(customFont);
        courseInstructorLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel coursePriceLabel = new JLabel("Price: Rs " + course.getPrice());
        coursePriceLabel.setFont(customFont);
        coursePriceLabel.setHorizontalAlignment(JLabel.CENTER);

        // Description panel
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setBackground(Color.WHITE);
        descriptionPanel.setLayout(new BorderLayout());

        // Description label
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(customFont);

        // Description text area
        JTextArea descriptionArea = new JTextArea(course.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("04b03", Font.PLAIN, 18));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(Color.WHITE);

        // Add description label and text area to description panel
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        descriptionScrollPane.setPreferredSize(new Dimension(300, 300));
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        // Lectures panel
        JPanel lecturesPanel = new JPanel();
        lecturesPanel.setLayout(new BoxLayout(lecturesPanel, BoxLayout.Y_AXIS));
        lecturesPanel.setBackground(Color.WHITE);
        JScrollPane lecturesScrollPane = new JScrollPane(lecturesPanel);
        lecturesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lecturesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        lecturesScrollPane.setPreferredSize(new Dimension(300, 300));

        // Lectures label
        JLabel lecturesLabel = new JLabel("Lectures:");
        lecturesLabel.setFont(customFont);

        // Fetch and add lectures to lectures panel
        List<Lecture> lectures;
        try {
            lectures = lectureDAO.getLecturesByCourseId(course.getId());
            for (Lecture lecture : lectures) {
                String lectureTitle = lecture.getTitle();
                JTextArea lectureLabel = new JTextArea(lectureTitle);
                lectureLabel.setEditable(false);
                lectureLabel.setLineWrap(true);
                lectureLabel.setWrapStyleWord(true);
                lectureLabel.setFont(new Font("04b03", Font.PLAIN, 18));
                lectureLabel.setBackground(Color.WHITE);
                lectureLabel.setBorder(new LineBorder(Color.white, 3));
                lecturesPanel.add(lectureLabel);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(customFont);
        backButton.setFocusable(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (isStudent()) {
                    new SearchCoursesUI(student.getId()).setVisible(true);
                } else {
                    new SearchCoursesUI(instructor.getId()).setVisible(true);
                }
            }
        });
        buttonPanel.add(backButton);

        // Register button
        if (isStudent()) {
            JButton registerButton = new JButton("Register");
            registerButton.setBackground(Color.WHITE);
            registerButton.setFont(customFont);
            registerButton.setPreferredSize(new Dimension(150, 50));

            if (studentDAO.isRegisteredForCourse(student, course)) {
                {
                    registerButton.setText("Registered");
                    Font registerFont = new Font("04b03", Font.PLAIN, 20);
                    registerButton.setFont(registerFont);
                }
                registerButton.setEnabled(false);
            } else {
                registerButton.setFocusable(false);
                registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new Billing(course,student).setVisible(true);
                    }
                });
            }
            buttonPanel.add(registerButton);
        }

        // Layout manager for details
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Horizontal group layout for main panel
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(courseNameLabel)
                .addComponent(courseInstructorLabel)
                .addComponent(coursePriceLabel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(descriptionPanel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(lecturesLabel)
                                .addComponent(lecturesScrollPane))
                )
                .addComponent(buttonPanel)
        );

        // Vertical group layout for main panel
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(courseNameLabel)
                .addComponent(courseInstructorLabel)
                .addComponent(coursePriceLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(descriptionPanel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lecturesLabel)
                                .addComponent(lecturesScrollPane))
                )
                .addComponent(buttonPanel)
        );

        getContentPane().add(mainPanel);
    }

    private String getCourseInstructorName(int instructorId) {
        return instructorDAO.getInstructorById(instructorId).getName();
    }

    private boolean isStudent() {
        return student != null;
    }

    private void findUserType() {
        if (userDAO.isStudent(userId)) {
            this.student = studentDAO.getStudentById(userId);
            instructor = null;
        } else if (userDAO.isInstructor(userId)) {
            this.instructor = instructorDAO.getInstructorById(userId);
            student = null;
        }
    }
}
