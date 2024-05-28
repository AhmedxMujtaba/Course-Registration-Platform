package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.InstructorDAO;
import com.ahmedxmujtaba.DataBase.StudentDAO;
import com.ahmedxmujtaba.DataBase.UserDAO;
import com.ahmedxmujtaba.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class AccountSettingsUI extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneNumberField;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton backButton;
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel phoneNumberLabel;
    private JLabel messageLabel;
    private JLabel titleLabel;
    private JCheckBox showPasswordCheckBox;
    private User user;
    private DataBaseLink dbLink;

    public AccountSettingsUI(User user) {
        this.user = user;
        this.dbLink = new DataBaseLink();
        setTitle("Account Settings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Set the entire frame background color to white
        setBackground(Color.WHITE);

        // Use BorderLayout for overall layout
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Set background color to white
        GridBagConstraints gbc = new GridBagConstraints();

        titleLabel = new JLabel("Account Settings");
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setEchoChar('*');
        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(20);
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete Account");
        backButton = new JButton("Back");
        messageLabel = new JLabel("");
        showPasswordCheckBox = new JCheckBox("Show Password");

        Font customFont = new Font("04b03", Font.PLAIN, 24);

        // Font settings
        titleLabel.setFont(customFont.deriveFont(30f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameLabel.setFont(customFont);
        emailLabel.setFont(customFont);
        passwordLabel.setFont(customFont);
        phoneNumberLabel.setFont(customFont);
        saveButton.setFont(customFont);
        deleteButton.setFont(customFont);
        backButton.setFont(customFont);
        messageLabel.setFont(customFont.deriveFont(14f));
        showPasswordCheckBox.setFont(customFont.deriveFont(18f));
        nameField.setFont(customFont.deriveFont(18f));
        emailField.setFont(customFont.deriveFont(18f));
        passwordField.setFont(customFont.deriveFont(18f));
        phoneNumberField.setFont(customFont.deriveFont(18f));

        // Button settings
        saveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        saveButton.setBackground(Color.WHITE);
        saveButton.setOpaque(true);

        deleteButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setOpaque(true);

        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.setBackground(Color.WHITE);
        backButton.setOpaque(true);

        saveButton.setFocusPainted(false);
        deleteButton.setFocusPainted(false);
        backButton.setFocusPainted(false);

        showPasswordCheckBox.setBackground(Color.WHITE);

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
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        emailField.setEditable(false); // Make email field non-editable
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        gbc.gridy = 4;
        formPanel.add(showPasswordCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(phoneNumberLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(phoneNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 10, 0);
        saveButton.setPreferredSize(new Dimension(150, 40));
        formPanel.add(saveButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        deleteButton.setPreferredSize(new Dimension(200, 40));
        formPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        backButton.setPreferredSize(new Dimension(100, 40));
        formPanel.add(backButton, gbc);

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(Color.WHITE); // Set background color to white
        messagePanel.add(messageLabel);
        add(messagePanel, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.CENTER);


        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPasswordCheckBox.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the updated user information from the UI fields
                String newName = nameField.getText().trim();
                String newPassword = new String(passwordField.getPassword());
                String newPhoneNumber = phoneNumberField.getText().trim();

                // Perform validation checks
                if (newName.isEmpty()) {
                    displayErrorMessage("Username cannot be empty.");
                    return;
                }

                if (newPassword.length() < 8) {
                    displayErrorMessage("Password must be at least 8 characters long.");
                    return;
                }

                // Validate phone number format
                if (!isValidPhoneNumber(newPhoneNumber)) {
                    displayErrorMessage("Invalid phone number format.");
                    return;
                }

                // Update the user object with the new information
                user.setName(newName);
                user.setPassword(newPassword);
                user.setPhoneNumber(Integer.parseInt(newPhoneNumber));

                // Update the user information in the database
                UserDAO userDAO = new UserDAO(dbLink);
                boolean updated = userDAO.updateUserName(user) && userDAO.updateUserPassword(user) && userDAO.updateUserPhoneNumber(user);

                // Display success or error message based on the update result
                if (updated) {
                    displaySuccessMessage("User information updated successfully.");
                } else {
                    displayErrorMessage("Failed to update user information.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirm with the user before deleting the account
                int choice = JOptionPane.showConfirmDialog(AccountSettingsUI.this,
                        "Are you sure you want to delete your account?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    // Attempt to delete the user from the database
                    UserDAO userDAO = new UserDAO(dbLink);
                    boolean deleted = userDAO.deleteUser(user);

                    // Display success or error message based on the delete result
                    if (deleted) {
                        displaySuccessMessage("Account deleted successfully.");
                        dispose(); // Close the account settings UI after deletion
                    } else {
                        displayErrorMessage("Failed to delete account.");
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UserDAO us = new UserDAO(new DataBaseLink());
                //todo fix this and keep consistency throughout code
                InstructorDAO is = new InstructorDAO();
                StudentDAO su = new StudentDAO(new DataBaseLink());
                int currentUserId = user.getId();
                //check to see if user is an instructor or student and open their respective portals
                if (us.isInstructor(currentUserId)){
                    dispose();
                    InstructorPortalUI instructorPortalUI = new InstructorPortalUI(is.getInstructorById(currentUserId));
                    instructorPortalUI.setVisible(true);
                }
                else if (us.isStudent(currentUserId)){
                    dispose();
                    //todo impliment for student portal UI
                }
            }
        });

        // Populate user data in the fields
        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        phoneNumberField.setText(String.valueOf(user.getPhoneNumber()));

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
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "\\d+"; // Regular expression to match digits
        return phoneNumber.matches(phoneRegex);
    }
}

