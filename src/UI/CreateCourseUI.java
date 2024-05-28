package UI;

import DataBase.CourseDAO;
import DataBase.DataBaseLink;
import DataBase.InstructorDAO;
import DataBase.UserDAO;
import Entities.Course;
import Entities.Instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateCourseUI extends JFrame {
    private JTextField courseNameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JButton createCourseButton;
    private JButton backButton;
    private JLabel courseNameLabel;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JLabel messageLabel;
    private JLabel titleLabel;

    public CreateCourseUI(Instructor instructor) {
        setTitle("Course Registration Platform - Create Course");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        titleLabel = new JLabel("Create Course");
        courseNameLabel = new JLabel("Course Name:");
        courseNameField = new JTextField(20);
        descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(20);
        priceLabel = new JLabel("Price:");
        priceField = new JTextField(20);
        createCourseButton = new JButton("Create Course");
        messageLabel = new JLabel("");
        backButton = new JButton("Back");

        Font customFont = new Font("04b03", Font.PLAIN, 24);

        titleLabel.setFont(customFont.deriveFont(30f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        courseNameLabel.setFont(customFont);
        descriptionLabel.setFont(customFont);
        priceLabel.setFont(customFont);
        createCourseButton.setFont(customFont);
        messageLabel.setFont(customFont.deriveFont(14f));
        backButton.setFont(customFont.deriveFont(18f));
        courseNameField.setFont(customFont.deriveFont(18f));
        descriptionField.setFont(customFont.deriveFont(18f));
        priceField.setFont(customFont.deriveFont(18f));

        createCourseButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        createCourseButton.setBackground(Color.WHITE);
        createCourseButton.setOpaque(true);

        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.setBackground(Color.WHITE);
        backButton.setOpaque(true);

        createCourseButton.setFocusPainted(false);
        backButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(courseNameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(courseNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 10, 0);
        createCourseButton.setPreferredSize(new Dimension(200, 40));
        formPanel.add(createCourseButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        backButton.setPreferredSize(new Dimension(100, 40));
        formPanel.add(backButton, gbc);

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(Color.WHITE);
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.CENTER);

        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameField.getText();
                String description = descriptionField.getText();
                double price = Double.parseDouble(priceField.getText());

                if (courseName.isEmpty() || description.isEmpty()) {
                    displayErrorMessage("All fields must be filled out.");
                    return;
                }

                CourseDAO courseDAO = new CourseDAO(new DataBaseLink());
                Course newCourse = new Course(courseName, description, instructor.getId(), price);
                boolean success = courseDAO.addCourse(newCourse);

                if (success) {
                    displaySuccessMessage("Course created successfully!");
                } else {
                    displayErrorMessage("There was an error creating the course. Please try again.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //now we need modified instrictor so,
                InstructorDAO instructorDAO = new InstructorDAO();

                Instructor updatedInstructor = instructorDAO.getInstructorById(instructor.getId());
                new InstructorPortalUI(updatedInstructor).setVisible(true);
            }
        });

        setVisible(true);
    }

    private void displayErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(Color.RED);
    }

    private void displaySuccessMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(Color.BLACK);
    }
}
