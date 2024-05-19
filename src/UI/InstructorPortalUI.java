package UI;

import DataBase.CourseDAO;
import DataBase.DataBaseLink;
import Entities.Course;
import Entities.Instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorPortalUI extends JFrame {
    private JTextField courseNameField;
    private JTextField courseDescriptionField;
    private JTextField coursePriceField;
    private JButton createCourseButton;
    private JLabel messageLabel;

    private Instructor instructor;

    public InstructorPortalUI(Instructor instructor) {
        this.instructor = instructor;

        setTitle("Instructor Portal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use GridBagLayout with spacing
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 5, 10); // Adjust spacing as needed

        // Add components with constraints
        JLabel titleLabel = new JLabel("Create a New Course");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2; // Span two columns
        panel.add(titleLabel, c);

        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridy = 1;
        c.gridwidth = 1; // Back to single column
        panel.add(courseNameLabel, c);

        courseNameField = new JTextField();
        courseNameField.setPreferredSize(new Dimension(300, 25));
        c.gridx = 1;
        panel.add(courseNameField, c);

        JLabel courseDescriptionLabel = new JLabel("Course Description:");
        courseDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridy = 2;
        panel.add(courseDescriptionLabel, c);

        courseDescriptionField = new JTextField();
        courseDescriptionField.setPreferredSize(new Dimension(300, 25));
        c.gridx = 1;
        panel.add(courseDescriptionField, c);

        JLabel coursePriceLabel = new JLabel("Course Price:");
        coursePriceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        c.gridy = 3;
        panel.add(coursePriceLabel, c);

        coursePriceField = new JTextField();
        coursePriceField.setPreferredSize(new Dimension(300, 25));
        c.gridx = 1;
        panel.add(coursePriceField, c);

        createCourseButton = new JButton("Create Course");
        createCourseButton.setFont(new Font("Arial", Font.BOLD, 14));
        // Consider using custom button appearance (refer to Java Swing documentation)
        c.gridy = 4;
        c.gridwidth = 2; // Span two columns for button
        panel.add(createCourseButton, c);

        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(Color.RED);  // Error message color
        c.gridy = 5;
        c.gridwidth = 1;
        panel.add(messageLabel, c);

        add(panel);  // Add panel to the frame

        createCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCourse();
            }
        });

        setVisible(true);
    }

    private void createCourse() {
        String name = courseNameField.getText();
        String description = courseDescriptionField.getText();

        // Validate input (optional)
        if (name.isEmpty() || description.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }
        double price;
        try {
            price = Double.parseDouble(coursePriceField.getText());
            if (price <= 0) {
                messageLabel.setText("Price must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid price.");
        }
    }
}