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
import java.io.File;
import java.io.IOException;

public class LoginUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel messageLabel;
    private JLabel titleLabel;

    public LoginUI() {
        setTitle("Course Registration Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500); // Set the frame size to 700 by 500
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
        loginButton = new JButton("Login");
        messageLabel = new JLabel("");

        // Set custom font for title, labels, buttons, and text fields
        Font customFont = new Font("04b03", Font.PLAIN, 30);

        titleLabel.setFont(customFont.deriveFont(30f)); // Larger font for the title
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        emailLabel.setFont(customFont);
        passwordLabel.setFont(customFont);
        loginButton.setFont(customFont);
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

        // Add title label with reduced top padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Top padding reduced to 10 pixels
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Add email label and field
        gbc.insets = new Insets(0, 0, 10, 0); // Padding between components
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
        gbc.anchor = GridBagConstraints.EAST; // Align labels to the right
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Align text fields to the left
        add(passwordField, gbc);

        // Add login button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center the login button
        loginButton.setPreferredSize(new Dimension(200, 50)); // Set button size
        add(loginButton, gbc);

        // Add message label
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 0, 0); // Padding above the message label
        add(messageLabel, gbc);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginUI();
        });
    }
}
