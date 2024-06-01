package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.CourseDAO;
import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.StudentDAO;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentUI extends JFrame {
    private JLabel welcomeLabel;
    private JLabel userNameLabel;
    private JLabel coursesLabel;
    private JButton accountSettingsButton;
    private JButton searchCoursesButton;
    private JButton logoutButton;
    private JPanel coursesPanel;
    private JScrollPane scrollPane;

    private Student student;
    private final StudentDAO studentDAO = new StudentDAO(new DataBaseLink());
    private final CourseDAO courseDAO = new CourseDAO(new DataBaseLink());

    public StudentUI(Student student) {

        this.student = studentDAO.getStudentById(student.getId());

        //set file icon
        ImageIcon icon = new ImageIcon(("src/com/ahmedxmujtaba/UI/Icons/icon1.png"));
        setIconImage(icon.getImage());

        setTitle("Student Portal");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set font
        Font customFont = new Font("04b03", Font.PLAIN, 24);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(Color.WHITE);

        // Create buttons
        accountSettingsButton = new JButton("Account Settings");
        searchCoursesButton = new JButton("Search Courses");
        logoutButton = new JButton("Log out");

        // Set font and border for buttons
        accountSettingsButton.setFont(customFont);
        searchCoursesButton.setFont(customFont);
        logoutButton.setFont(customFont);
        accountSettingsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        searchCoursesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        logoutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Set buttons to white and remove selection boxes
        accountSettingsButton.setBackground(Color.WHITE);
        searchCoursesButton.setBackground(Color.WHITE);
        logoutButton.setBackground(Color.WHITE);
        accountSettingsButton.setFocusPainted(false);
        searchCoursesButton.setFocusPainted(false);
        logoutButton.setFocusPainted(false);

        // Add buttons to buttons panel
        buttonsPanel.add(accountSettingsButton);
        buttonsPanel.add(searchCoursesButton);
        buttonsPanel.add(logoutButton);

        // Create split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300); // Set initial divider location

        // Left panel for courses
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        coursesPanel.setBorder(BorderFactory.createTitledBorder("Courses"));
        coursesPanel.setBackground(Color.WHITE);
        scrollPane = new JScrollPane(coursesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        // Right panel for user information
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        welcomeLabel = new JLabel("Welcome " + student.getName() + "!", SwingConstants.CENTER);
        userNameLabel = new JLabel("User Name: " + student.getName(), SwingConstants.CENTER);
        coursesLabel = new JLabel("Courses", SwingConstants.CENTER);
        welcomeLabel.setFont(customFont.deriveFont(30f));
        userNameLabel.setFont(customFont);
        coursesLabel.setFont(customFont.deriveFont(24f));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 20, 0);
        rightPanel.add(welcomeLabel, gbc);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(userNameLabel, gbc);
        gbc.gridy = 2;
        rightPanel.add(coursesLabel, gbc);

        // Add panels to split pane
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        // Add panels to frame
        add(buttonsPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        // Load courses
        updateCourses();

        // Add action listeners to buttons
        accountSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open account settings UI
                AccountSettingsUI accountSettingsUI = new AccountSettingsUI(student);
                accountSettingsUI.setVisible(true);
                // Close the current window
                dispose();
            }
        });

        searchCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open search courses UI
                SearchCoursesUI searchCoursesUI = new SearchCoursesUI(student.getId());
                searchCoursesUI.setVisible(true);
                // Close the current window
                dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the starting UI
                StartingUI startingUI = new StartingUI();
                startingUI.setVisible(true);
                // Close the current window
                dispose();
            }
        });
    }

    private void updateCourses() {
        Font customFont = new Font("04b03", Font.PLAIN, 24);
        int buttonPadding = 10; // Padding between buttons
        coursesPanel.removeAll();
        if (student.getCourses().isEmpty()) {
            JLabel noCoursesLabel = new JLabel("No Courses", SwingConstants.CENTER);
            noCoursesLabel.setFont(customFont);
            coursesPanel.add(noCoursesLabel);
        } else {
            for (int courseId : student.getCourses()) {
                Course course = courseDAO.getCourseById(courseId);
                if (course != null) {
                    JButton courseButton = new JButton(course.getName());

                    courseButton.setFont(customFont);
                    int buttonPaddingX = 10; // Horizontal padding between buttons
                    int buttonPaddingY = 15; // Vertical padding between buttons
                    courseButton.setBorder(BorderFactory.createEmptyBorder(buttonPaddingY, buttonPaddingX, buttonPaddingY, buttonPaddingX)); // Add padding

                    courseButton.setBackground(Color.WHITE);
                    courseButton.setFocusPainted(false);
                    courseButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Pass the course to CourseDetailsUI
                            StudentCourseUI studentCourseUI = new StudentCourseUI(course, student.getId());
                            studentCourseUI.setVisible(true);
                            // Close the current window
                            dispose();
                        }
                    });
                    coursesPanel.add(courseButton);
                }
            }
        }

        coursesLabel.setText("Courses: " + student.getCourses().size());
        coursesPanel.revalidate();
        coursesPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentDAO studentDAO = new StudentDAO(new DataBaseLink());
            Student student = studentDAO.getStudentById(8);
            StudentUI studentPage = new StudentUI(student);
            studentPage.setVisible(true);
        });
    }
}
