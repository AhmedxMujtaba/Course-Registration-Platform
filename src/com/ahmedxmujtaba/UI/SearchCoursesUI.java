package com.ahmedxmujtaba.UI;
import com.ahmedxmujtaba.DataBase.*;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Instructor;
import com.ahmedxmujtaba.Entities.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchCoursesUI extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton backButton;
    private JPanel resultsPanel;
    private JScrollPane scrollPane;
    private int userId;
    private Instructor instructor;
    private Student student;
    private final UserDAO userDAO = new UserDAO(new DataBaseLink());
    private final CourseDAO courseDAO = new CourseDAO(new DataBaseLink());
    private final InstructorDAO instructorDAO = new InstructorDAO();
    private final StudentDAO studentDAO = new StudentDAO(new DataBaseLink());

    public SearchCoursesUI(int id ) {
        this.userId = id;

        ImageIcon icon = new ImageIcon(("src/com/ahmedxmujtaba/UI/Icons/icon1.png"));
        setIconImage(icon.getImage());

        //find the user
        findUserType();
        setTitle("Course Registration Platform - Search Courses");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Search Courses:");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        backButton = new JButton("Back");

        Font customFont = new Font("04b03", Font.PLAIN, 24);
        searchLabel.setFont(customFont);
        searchField.setFont(customFont.deriveFont(18f));
        searchButton.setFont(customFont);
        backButton.setFont(customFont);

        styleButton(searchButton);
        styleButton(backButton);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(backButton);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridBagLayout());
        resultsPanel.setBackground(Color.WHITE);
        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(null); // Remove border
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                if (!query.isEmpty()) {
                    List<Course> matchingCourses = courseDAO.searchCoursesByName(query);
                    displayResults(matchingCourses);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //check if the user is a student or instructor and sent them back to their respective portal
                if (isStudent()){
                    new StudentUI(student).setVisible(true);
                }
                else
                    new InstructorPortalUI(instructor).setVisible(true);
            }
        });

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusPainted(false);
    }

    private void displayResults(List<Course> courses) {
        resultsPanel.removeAll();

        Font customFont = new Font("04b03", Font.PLAIN, 24);
        Font descriptionFont = customFont.deriveFont(14f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST; // Start from top-left
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.gridy = 0;

        if (courses.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No matching courses found.", SwingConstants.CENTER);
            noResultsLabel.setFont(customFont);
            noResultsLabel.setBackground(Color.WHITE);
            resultsPanel.add(noResultsLabel, gbc);
        } else {
            for (Course course : courses) {
                JPanel coursePanel = new JPanel(new GridLayout(1, 4));
                coursePanel.setBackground(Color.WHITE); // Set background to white
                coursePanel.setBorder(null); // Remove border

                JLabel courseNameLabel = new JLabel(course.getName());
                JTextArea courseDescriptionArea = new JTextArea(course.getDescription());
                JLabel coursePriceLabel = new JLabel(String.valueOf(course.getPrice()) + " Rs");
                JButton courseButton = new JButton("View Details");

                courseNameLabel.setFont(customFont);
                courseDescriptionArea.setFont(descriptionFont);
                coursePriceLabel.setFont(customFont.deriveFont(18f));
                courseButton.setFont(customFont.deriveFont(18f));

                courseDescriptionArea.setLineWrap(true);
                courseDescriptionArea.setWrapStyleWord(true);
                courseDescriptionArea.setEditable(false);
                courseDescriptionArea.setBackground(Color.WHITE);
                courseDescriptionArea.setBorder(null);

                styleButton(courseButton);
                courseButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        if (isStudent()) {
                            new CourseDetailsUI(course, student.getId()).setVisible(true);
                        }
                        else {
                            new CourseDetailsUI(course, instructor.getId()).setVisible(true);
                        }
                    }
                });

                coursePanel.add(courseNameLabel);
                coursePanel.add(courseDescriptionArea);
                coursePanel.add(coursePriceLabel);
                coursePanel.add(courseButton);

                resultsPanel.add(coursePanel, gbc);
                gbc.gridy++;
            }
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
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
