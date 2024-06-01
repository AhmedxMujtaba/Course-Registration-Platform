package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.UserDAO;
import com.ahmedxmujtaba.Entities.User;
import com.ahmedxmujtaba.Entities.Instructor;
import com.ahmedxmujtaba.Entities.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton; // New back button
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel messageLabel;
    private JLabel titleLabel;
    private JCheckBox showPasswordCheckBox; // Checkbox to show/hide password

    public LoginUI() {

        ImageIcon icon = new ImageIcon("src/com/ahmedxmujtaba/UI/Icons/icon1.png");
        setIconImage(icon.getImage());

        setTitle("Course Registration Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600); // Set the frame size to 700 by 500
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set the background color of the frame to white
        getContentPane().setBackground(Color.WHITE);

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create components
        titleLabel = new JLabel("Course Registration Platform");
        emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        showPasswordCheckBox = new JCheckBox("Show Password"); // Create the checkbox
        loginButton = new JButton("Login");
        backButton = new JButton("Back"); // Create the back button
        messageLabel = new JLabel("");

        // Set custom font for title, labels, buttons, and text fields
        Font customFont = new Font("04b03", Font.PLAIN, 30);

        titleLabel.setFont(customFont.deriveFont(30f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        emailLabel.setFont(customFont);
        passwordLabel.setFont(customFont);
        showPasswordCheckBox.setFont(customFont.deriveFont(20f));
        showPasswordCheckBox.setFocusable(false);
        showPasswordCheckBox.setBackground(Color.white);
        loginButton.setFont(customFont);
        backButton.setFont(customFont); // Set font for the back button
        messageLabel.setFont(customFont);
        emailField.setFont(customFont.deriveFont(20f)); // Font size 20 for the text fields
        passwordField.setFont(customFont.deriveFont(20f)); // Font size 20 for the text fields

        // Set the echo character for the password field to '*'
        passwordField.setEchoChar('*');

        // Set button border and background for high contrast
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border with 2 pixels thickness
        loginButton.setBackground(Color.WHITE); // White background
        loginButton.setOpaque(true); // Make sure the background color is applied

        // Remove the focus (selection) grid from the button
        loginButton.setFocusPainted(false);

        // Set button border and background for the back button
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border with 2 pixels thickness
        backButton.setBackground(Color.WHITE); // White background
        backButton.setOpaque(true); // Make sure the background color is applied

        // Remove the focus (selection) grid from the back button
        backButton.setFocusPainted(false);

        // Add title label with reduced top padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0); // Top padding reduced to 10 pixels
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Add email label and field
        gbc.insets = new Insets(0, 0, 0, 10);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST; // Align labels to the right
        add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Align text fields to the left
        add(emailField, gbc);

        // Add password label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 0, 10);
        gbc.anchor = GridBagConstraints.EAST; // Align labels to the right
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Align text fields to the left
        add(passwordField, gbc);

        // Add the show password checkbox
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST; // Align checkbox to the left
        gbc.insets = new Insets(10, 0, 0, 0); // Padding above the checkbox
        add(showPasswordCheckBox, gbc);

        // Add login button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center the login button
        gbc.insets = new Insets(40, 0, 0, 0);
        loginButton.setPreferredSize(new Dimension(200, 50)); // Set button size
        add(loginButton, gbc);

        // Add message label
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 0, 0, 0); // Padding above the message label
        add(messageLabel, gbc);

        // Add back button in the bottom right corner
        gbc.gridy = 6;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Align the button to the right
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding to place the button in the bottom right
        backButton.setPreferredSize(new Dimension(100, 40)); // Set button size
        add(backButton, gbc);

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Perform login check
                DataBaseLink dbLink = new DataBaseLink();
                dbLink.connect();
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
                dbLink.disconnect();
            }
        });

        // Add action listener to back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the login window
                new StartingUI().setVisible(true); // Open the starting UI
            }
        });

        // Add action listener to show password checkbox
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Show password
                } else {
                    passwordField.setEchoChar('*'); // Hide password
                }
            }
        });

        setVisible(true); // Make the login UI visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginUI();
        });
    }
}
