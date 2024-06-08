package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.CourseDAO;
import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.InstructorDAO;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseEditUI extends JFrame {
    private JTextField courseNameField;
    private JTextArea descriptionArea;
    private JTextField priceField;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton backButton;
    private JLabel courseNameLabel;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JLabel messageLabel;
    private JLabel titleLabel;
    private Course course;
    private Instructor instructor;

    public CourseEditUI(Course course,Instructor instructor) {
        this.course = course;

        ImageIcon icon = new ImageIcon(("src/com/ahmedxmujtaba/UI/Icons/icon1.png"));
        setIconImage(icon.getImage());

        setTitle("Course Registration Platform - Edit Course");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600); // Increased size
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        titleLabel = new JLabel("Edit Course");
        courseNameLabel = new JLabel("Course Name:");
        courseNameField = new JTextField(20);
        descriptionLabel = new JLabel("Description:");
        descriptionArea = new JTextArea(10, 30); // Increased size for better visibility
        priceLabel = new JLabel("Price:");
        priceField = new JTextField(20);
        saveButton = new JButton("Save Changes");
        deleteButton = new JButton("Delete Course");
        messageLabel = new JLabel("");
        backButton = new JButton("Back");

        Font customFont = new Font("04b03", Font.PLAIN, 24);

        titleLabel.setFont(customFont.deriveFont(30f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        courseNameLabel.setFont(customFont);
        descriptionLabel.setFont(customFont);
        priceLabel.setFont(customFont);
        saveButton.setFont(customFont);
        deleteButton.setFont(customFont);
        messageLabel.setFont(customFont.deriveFont(14f));
        backButton.setFont(customFont.deriveFont(18f));
        courseNameField.setFont(customFont.deriveFont(18f));
        descriptionArea.setFont(customFont.deriveFont(16f)); // Smaller text size for description area
        priceField.setFont(customFont.deriveFont(18f));

        courseNameField.setText(course.getName());
        descriptionArea.setText(course.getDescription());
        priceField.setText(String.valueOf(course.getPrice()));

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        saveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        saveButton.setBackground(Color.WHITE);
        saveButton.setOpaque(true);

        deleteButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        deleteButton.setBackground(Color.white);
        deleteButton.setOpaque(true);

        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.setBackground(Color.WHITE);
        backButton.setOpaque(true);

        saveButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
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
        gbc.anchor = GridBagConstraints.NORTHEAST; // Align label with the top of the description area
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(descriptionScrollPane, gbc);

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
        saveButton.setPreferredSize(new Dimension(200, 40));
        formPanel.add(saveButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        deleteButton.setPreferredSize(new Dimension(200, 40));
        formPanel.add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        backButton.setPreferredSize(new Dimension(100, 40));
        formPanel.add(backButton, gbc);

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(Color.WHITE);
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = courseNameField.getText();
                String description = descriptionArea.getText();
                double price;

                try {
                    price = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    displayErrorMessage("Price must be a number.");
                    return;
                }

                if (courseName.isEmpty() || description.isEmpty()) {
                    displayErrorMessage("All fields must be filled out.");
                    return;
                }

                course.setName(courseName);
                course.setDescription(description);
                course.setPrice(price);

                CourseDAO courseDAO = new CourseDAO(new DataBaseLink());
                boolean success = courseDAO.updateCourse(course);

                if (success) {
                    displaySuccessMessage("Course updated successfully!");
                } else {
                    displayErrorMessage("There was an error updating the course. Please try again.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this course?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    CourseDAO courseDAO = new CourseDAO(new DataBaseLink());
                    boolean success = courseDAO.deleteCourse(course.getId());

                    if (success) {
                        displaySuccessMessage("Course deleted successfully!");
                        dispose();
                        new InstructorPortalUI(instructor).setVisible(true);
                    }
                    else
                        displayErrorMessage("There was an error deleting the course. Please try again.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Now we need modified instructor, so
                InstructorDAO instructorDAO = new InstructorDAO();
                Instructor instructor = instructorDAO.getInstructorById(course.getInstructorId());
                new CoursePanelInstructorUI(course, instructor).setVisible(true);
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

