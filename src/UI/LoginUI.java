package UI;

import DataBase.DataBaseLink;
import DataBase.UserDAO;
import Entities.User;
import Entities.Instructor;
import Entities.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel messageLabel;

    public LoginUI() {
        setTitle("Login");
        // Set desired size (adjust as needed)
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set layout manager
        setLayout(new GridLayout(4, 2, 5, 10)); // Adjust spacing between components

        // Create components
        emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        messageLabel = new JLabel("");

        // Add components to the frame
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(messageLabel);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Perform login check
                DataBaseLink dbLink = new DataBaseLink();
                UserDAO userDAO = new UserDAO(dbLink);
                User user = userDAO.getUserByEmail(email);

                if (user != null && user.getPassword().equals(password)) {
                    User fullUser = userDAO.getUserDetails(user);
                    messageLabel.setText("Login successful!");

                    if (fullUser instanceof Instructor) {
                        dispose();  // Close the login window
                        new InstructorPortalUI((Instructor) fullUser).setVisible(true);
                    } else if (fullUser instanceof Student) {
                        dispose();
                        new StudentUI((Student) fullUser).setVisible(true);
                    }
                } else {
                    messageLabel.setText("Invalid email or password.");
                }
            }
        });

        setVisible(true); // Make the login UI visible
    }
}
