package UI;

import DataBase.DataBaseLink;
import DataBase.UserDAO;
import Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

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
    private JLabel titleLabel;
    private JCheckBox showPasswordCheckBox;

    public SignUpUI() {
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
        titleLabel = new JLabel("Sign Up");
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(20);
        userTypeLabel = new JLabel("User Type:");
        userTypeComboBox = new JComboBox<>(new String[]{"Student", "Instructor"});
        signUpButton = new JButton("Sign Up");
        messageLabel = new JLabel("");
        showPasswordCheckBox = new JCheckBox("Show Password");

        // Set custom font for title, labels, buttons, and text fields
        Font customFont = new Font("04b03", Font.PLAIN, 24);

        titleLabel.setFont(customFont.deriveFont(30f)); // Larger font for the title
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameLabel.setFont(customFont);
        emailLabel.setFont(customFont);
        passwordLabel.setFont(customFont);
        phoneNumberLabel.setFont(customFont);
        userTypeLabel.setFont(customFont);
        signUpButton.setFont(customFont);
        messageLabel.setFont(customFont);
        showPasswordCheckBox.setFont(customFont.deriveFont(18f)); // Font size 18 for the check box
        nameField.setFont(customFont.deriveFont(18f)); // Font size 18 for the text fields
        emailField.setFont(customFont.deriveFont(18f));
        passwordField.setFont(customFont.deriveFont(18f));
        phoneNumberField.setFont(customFont.deriveFont(18f));
        userTypeComboBox.setFont(customFont.deriveFont(18f));

        // Set button border and background for high contrast
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border with 2 pixels thickness
        signUpButton.setBackground(Color.WHITE); // White background
        signUpButton.setOpaque(true); // Make sure the background color is applied

        // Remove the focus (selection) grid from the button
        signUpButton.setFocusPainted(false);

        // Style the combo box with a white background and black border
        userTypeComboBox.setBackground(Color.WHITE);
        userTypeComboBox.setBorder(new LineBorder(Color.BLACK, 2));

        // Style the check box with a black border
        showPasswordCheckBox.setBorder(new LineBorder(Color.BLACK, 2));
        showPasswordCheckBox.setBackground(Color.WHITE);

        // Add title label with reduced top padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Top padding reduced to 20 pixels
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // Add name label and field
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 10); // Padding between components
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST; // Align labels to the right
        add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Align text fields to the left
        add(nameField, gbc);

        // Add email label and field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // Add password label and field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        // Add show password checkbox
        gbc.gridy = 4;
        add(showPasswordCheckBox, gbc);

        // Add phone number label and field
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(phoneNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(phoneNumberField, gbc);

        // Add user type label and combo box
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        add(userTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(userTypeComboBox, gbc);

        // Add sign up button
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center the sign up button
        gbc.insets = new Insets(20, 0, 10, 0); // Padding around the button
        signUpButton.setPreferredSize(new Dimension(150, 40)); // Set button size
        add(signUpButton, gbc);

        // Add message label
        gbc.gridy = 8;
        gbc.insets = new Insets(10, 0, 0, 0); // Padding above the message label
        add(messageLabel, gbc);

        // Add action listener to show password checkbox
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Show the password
                } else {
                    passwordField.setEchoChar('*'); // Hide the password
                }
            }
        });

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
                User newUser = new User(0, name, password, email, phoneNumber);

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

        setVisible(true); // Make the sign up UI visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignUpUI().setVisible(true);
        });
    }
}
