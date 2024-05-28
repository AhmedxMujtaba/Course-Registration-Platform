package UI;

import DataBase.DataBaseLink;
import DataBase.UserDAO;
import Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import java.util.regex.Pattern;

public class SignUpUI extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneNumberField;
    private JComboBox<String> userTypeComboBox;
    private JButton signUpButton;
    private JButton backButton;
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
        setSize(700, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        // Set the entire frame background color to white
        setBackground(Color.WHITE);

        // Use BorderLayout for overall layout
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Set background color to white
        GridBagConstraints gbc = new GridBagConstraints();

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
        backButton = new JButton("Back");

        Font customFont = new Font("04b03", Font.PLAIN, 24);

        titleLabel.setFont(customFont.deriveFont(30f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        nameLabel.setFont(customFont);
        emailLabel.setFont(customFont);
        passwordLabel.setFont(customFont);
        phoneNumberLabel.setFont(customFont);
        userTypeLabel.setFont(customFont);
        signUpButton.setFont(customFont);
        messageLabel.setFont(customFont.deriveFont(14f));
        showPasswordCheckBox.setFont(customFont.deriveFont(18f));
        backButton.setFont(customFont.deriveFont(18f));
        nameField.setFont(customFont.deriveFont(18f));
        emailField.setFont(customFont.deriveFont(18f));
        passwordField.setFont(customFont.deriveFont(18f));
        phoneNumberField.setFont(customFont.deriveFont(18f));
        userTypeComboBox.setFont(customFont.deriveFont(18f));

        signUpButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        signUpButton.setBackground(Color.WHITE);
        signUpButton.setOpaque(true);

        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.setBackground(Color.WHITE);
        backButton.setOpaque(true);

        signUpButton.setFocusPainted(false);
        backButton.setFocusPainted(false);

        userTypeComboBox.setBackground(Color.WHITE);
        userTypeComboBox.setBorder(new LineBorder(Color.BLACK, 2));

        showPasswordCheckBox.setBorder(new LineBorder(Color.BLACK, 2));
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
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        //make password field hidden at first
        passwordField.setEchoChar('*');
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
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(userTypeLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 10, 0);
        signUpButton.setPreferredSize(new Dimension(150, 40));
        formPanel.add(signUpButton, gbc);

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

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());
                    String phoneNumberStr = phoneNumberField.getText();

                    if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumberStr.isEmpty()) {
                        displayErrorMessage("All fields must be filled out.");
                        return;
                    }

                    if (!isValidEmail(email)) {
                        displayErrorMessage("Invalid email format.");
                        return;
                    }

                    if (password.length() < 8) {
                        displayErrorMessage("Password must be at least 8 characters long.");
                        return;
                    }

                    int phoneNumber;
                    try {
                        phoneNumber = Integer.parseInt(phoneNumberStr);
                    } catch (NumberFormatException ex) {
                        displayErrorMessage("Invalid phone number. Please enter a valid number.");
                        return;
                    }

                    String userType = (String) userTypeComboBox.getSelectedItem();

                    User newUser = new User(0, name, password, email, phoneNumber);
                    DataBaseLink dbLink = new DataBaseLink();
                    UserDAO userDAO = new UserDAO(dbLink);

                    if (userDAO.emailExists(email)) {
                        displayErrorMessage("Email already exists. Please use a different email.");
                        return;
                    }

                    boolean success;
                    if (userType.equals("Student")) {
                        success = userDAO.addStudent(newUser);
                    } else {
                        success = userDAO.addInstructor(newUser);
                    }

                    if (success) {
                        displaySuccessMessage("User signed up successfully as " + userType + "!");
                    } else {
                        displayErrorMessage("There was an error signing up. Please try again.");
                    }
                } catch (Exception ex) {
                    displayErrorMessage("An unexpected error occurred. Please try again.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StartingUI().setVisible(true);
            }
        });

        setVisible(true);
    }

    private void displayErrorMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(messageLabel.getFont().deriveFont(14f));
    }

    private void displaySuccessMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setFont(messageLabel.getFont().deriveFont(18f));
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUpUI().setVisible(true));
    }
}
