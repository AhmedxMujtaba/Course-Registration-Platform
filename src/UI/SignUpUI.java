package UI;

import DataBase.DataBaseLink;
import DataBase.UserDAO;
import Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpUI extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneNumberField;
    private JComboBox<String> userTypeComboBox;
    private JButton signUpButton;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel phoneNumberLabel;
    private JLabel userTypeLabel;
    private JLabel messageLabel;

    public SignUpUI() {
        setTitle("Sign Up");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set layout manager
        setLayout(new GridLayout(7, 1));

        // Create components
        nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        userTypeLabel = new JLabel("User Type:");
        userTypeComboBox = new JComboBox<>(new String[]{"Student", "Instructor"});
        signUpButton = new JButton("Sign Up");
        messageLabel = new JLabel("");

        // Add components to the frame
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(phoneNumberLabel);
        add(phoneNumberField);
        add(userTypeLabel);
        add(userTypeComboBox);
        add(signUpButton);
        add(messageLabel);

        // Add action listener to sign up button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                int phoneNumber = Integer.parseInt(phoneNumberField.getText());
                String userType = (String) userTypeComboBox.getSelectedItem();

                // Create a new user
                User newUser = new User(0,name, password, email, phoneNumber);

                // Add user to the database with the selected user type
                DataBaseLink dbLink = new DataBaseLink();
                UserDAO userDAO = new UserDAO(dbLink);
                if (userType.equals("Student")) {
                    userDAO.addStudent(newUser);
                } else if (userType.equals("Instructor")) {
                    userDAO.addInstructor(newUser);
                }

                messageLabel.setText("User signed up successfully as " + userType + "!");
            }
        });
    }
}
