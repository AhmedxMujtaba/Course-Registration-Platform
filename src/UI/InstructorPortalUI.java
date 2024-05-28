package UI;

import DataBase.CourseDAO;
import DataBase.InstructorDAO;
import Entities.Course;
import Entities.Instructor;
import UI.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorPortalUI extends JFrame {
    private JLabel welcomeLabel;
    private JLabel userNameLabel;
    private JLabel coursesLabel;
    private JLabel incomeLabel;
    private JButton accountSettingsButton;
    private JButton createCourseButton;
    private JButton searchCoursesButton;
    private JButton logoutButton;
    private JPanel coursesPanel;
    private JScrollPane scrollPane;

    private Instructor instructor;
    private InstructorDAO instructorDAO;

    public InstructorPortalUI(Instructor instructor) {
        this.instructor = instructor;
        this.instructorDAO = new InstructorDAO();

        setTitle("Course Registration Platform");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set font
        Font customFont = new Font("04b03", Font.PLAIN, 24);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(Color.WHITE);

        // Create buttons
        accountSettingsButton = new JButton("Account Settings");
        createCourseButton = new JButton("Create Course");
        searchCoursesButton = new JButton("Search Courses");
        logoutButton = new JButton("Log out");

        // Set font and border for buttons
        accountSettingsButton.setFont(customFont);
        createCourseButton.setFont(customFont);
        searchCoursesButton.setFont(customFont);
        logoutButton.setFont(customFont);
        accountSettingsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        createCourseButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        searchCoursesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        logoutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Set buttons to white and remove selection boxes
        accountSettingsButton.setBackground(Color.WHITE);
        createCourseButton.setBackground(Color.WHITE);
        searchCoursesButton.setBackground(Color.WHITE);
        logoutButton.setBackground(Color.WHITE);
        accountSettingsButton.setFocusPainted(false);
        createCourseButton.setFocusPainted(false);
        searchCoursesButton.setFocusPainted(false);
        logoutButton.setFocusPainted(false);

        // Add buttons to buttons panel
        buttonsPanel.add(accountSettingsButton);
        buttonsPanel.add(createCourseButton);
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
        welcomeLabel = new JLabel("Welcome " + instructor.getName() + "!", SwingConstants.CENTER);
        userNameLabel = new JLabel("User Name: " + instructor.getName(), SwingConstants.CENTER);
        coursesLabel = new JLabel("Courses", SwingConstants.CENTER);
        incomeLabel = new JLabel("Income: " + instructor.getIncome(), SwingConstants.CENTER);
        welcomeLabel.setFont(customFont.deriveFont(30f));
        userNameLabel.setFont(customFont);
        coursesLabel.setFont(customFont.deriveFont(24f));
        incomeLabel.setFont(customFont);
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
        gbc.gridy = 3;
        rightPanel.add(incomeLabel, gbc);

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
                AccountSettingsUI accountSettingsUI = new AccountSettingsUI(instructor);
                accountSettingsUI.setVisible(true);
                // Close the current window
                dispose();
            }
        });

        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open create course UI
                CreateCourseUI createCourseUI = new CreateCourseUI(instructor);
                createCourseUI.setVisible(true);
                // Close the current window
                dispose();
            }
        });

        searchCoursesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open search courses UI
                SearchCoursesUI searchCoursesUI = new SearchCoursesUI(instructor);
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

    private void addCourse(String courseName) {
        instructor.addCourseID(courseName);
        updateCourses();
    }

    private void updateCourses() {
        Font customFont = new Font("04b03", Font.PLAIN, 24);
        int buttonPadding = 10; // Padding between buttons

        coursesPanel.removeAll();
        if (instructor.getCourseIds().isEmpty()) {
            JLabel noCoursesLabel = new JLabel("No Courses", SwingConstants.CENTER);
            noCoursesLabel.setFont(customFont);
            coursesPanel.add(noCoursesLabel);
        } else {
            for (String courseId : instructor.getCourseIds()) {
                Course course = instructorDAO.getCourseById(courseId);
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
                            // Pass the course to CourseUI
                            CourseUI courseUI = new CourseUI(course);
                            courseUI.setVisible(true);
                            // Close the current window
                            dispose();
                        }
                    });
                    coursesPanel.add(courseButton);
                }
            }
        }

        coursesLabel.setText("Courses: " + instructor.getCourseIds().size());
        incomeLabel.setText("Income: " + instructor.getIncome());
        coursesPanel.revalidate();
        coursesPanel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InstructorDAO instructorDAO = new InstructorDAO();
            Instructor instructor = instructorDAO.getInstructorById(5); // Fetch instructor with ID 12 from database
            InstructorPortalUI instructorPage = new InstructorPortalUI(instructor);
            instructorPage.setVisible(true);
        });
    }
}

