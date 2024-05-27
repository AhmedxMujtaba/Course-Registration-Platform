package UI;

import Entities.Instructor;
import Entities.Course;
import DataBase.InstructorDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 4));

        accountSettingsButton = new JButton("Account Settings");
        createCourseButton = new JButton("Create Course");
        searchCoursesButton = new JButton("Search Courses");
        logoutButton = new JButton("Log out");

        topPanel.add(accountSettingsButton);
        topPanel.add(createCourseButton);
        topPanel.add(searchCoursesButton);
        topPanel.add(logoutButton);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1));

        welcomeLabel = new JLabel("Welcome " + instructor.getName() + "!", SwingConstants.CENTER);
        userNameLabel = new JLabel("User Name: " + instructor.getName(), SwingConstants.CENTER);
        coursesLabel = new JLabel("Courses: " + instructor.getCourseIds().size(), SwingConstants.CENTER);
        incomeLabel = new JLabel("Income: " + instructor.getIncome(), SwingConstants.CENTER);

        centerPanel.add(welcomeLabel);
        centerPanel.add(userNameLabel);
        centerPanel.add(coursesLabel);
        centerPanel.add(incomeLabel);

        add(centerPanel, BorderLayout.CENTER);

        coursesPanel = new JPanel();
        coursesPanel.setLayout(new BoxLayout(coursesPanel, BoxLayout.Y_AXIS));
        coursesPanel.setBorder(BorderFactory.createTitledBorder("Courses"));

        scrollPane = new JScrollPane(coursesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.EAST);

        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // For demonstration, add a new course with a unique name
                addCourse("Course " + (instructor.getCourseIds().size() + 1));
            }
        });

        updateCourses();
    }

    private void addCourse(String courseName) {
        instructor.addCourseID(courseName);
        updateCourses();
    }

    private void updateCourses() {
        coursesPanel.removeAll();
        for (String courseId : instructor.getCourseIds()) {
            Course course = instructorDAO.getCourseById(courseId);
            if (course != null) {
                JButton courseButton = new JButton(course.getName());
                coursesPanel.add(courseButton);
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
            Instructor instructor = instructorDAO.getInstructorById(12); // Fetch instructor with ID 1 from database
            InstructorPortalUI instructorPage = new InstructorPortalUI(instructor);
            instructorPage.setVisible(true);
        });
    }
}
