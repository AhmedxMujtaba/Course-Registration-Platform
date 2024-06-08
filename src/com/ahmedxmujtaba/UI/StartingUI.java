package com.ahmedxmujtaba.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingUI extends JFrame {
    private JButton loginButton;
    private JButton signupButton;

    public StartingUI() {

        ImageIcon icon = new ImageIcon("src/com/ahmedxmujtaba/UI/Icons/icon1.png");
        setIconImage(icon.getImage());

        setTitle("Course Registration Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);

        // Set the background color of the frame to white
        getContentPane().setBackground(Color.WHITE);

        // Create a panel with BorderLayout to center the buttons
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE); // Set the background color of the panel to white
        add(panel);

        // Create a panel to hold the buttons with GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE); // Set the background color of the button panel to white
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Create components
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        Font buttonPixelFont = new Font("04b03", Font.PLAIN, 30);
        loginButton.setFont(buttonPixelFont);
        signupButton.setFont(buttonPixelFont);

        // Set button border and background for high contrast
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Black border with 2 pixels thickness
        signupButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Black border with 2 pixels thickness
        loginButton.setBackground(Color.WHITE); // White background
        signupButton.setBackground(Color.WHITE); // White background
        loginButton.setOpaque(true); // Make sure the background color is applied
        signupButton.setOpaque(true); // Make sure the background color is applied

        // Remove the focus (selection) grid from the buttons
        loginButton.setFocusPainted(false);
        signupButton.setFocusPainted(false);

        // Define GridBagConstraints for the buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 50, 0); // Set padding of 50 pixels between buttons

        // Load and resize the image
        ImageIcon imageIcon = new ImageIcon("src/com/ahmedxmujtaba/UI/Icons/icon1.png"); // Load the image
        Image image = imageIcon.getImage(); // Get the image
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale the image
        ImageIcon scaledIcon = new ImageIcon(scaledImage); // Create a new ImageIcon

        // Add the resized image above the label
        JLabel imageLabel = new JLabel(scaledIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Add some top padding
        buttonPanel.add(imageLabel, gbc);

        // Add label "Course Registration Platform" with reduced top padding
        JLabel titleLabel = new JLabel("Course Registration Platform");
        Font pixelFont = new Font("04b03", Font.PLAIN, 40);
        titleLabel.setFont(pixelFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 50, 0); // Reduce top padding to 0 pixels
        gbc.gridy = 1;
        buttonPanel.add(titleLabel, gbc);

        // Add login button
        gbc.insets = new Insets(0, 0, 50, 0); // Reset padding for buttons
        gbc.gridy = 2;
        loginButton.setPreferredSize(new Dimension(150, 60)); // Set button size
        buttonPanel.add(loginButton, gbc);

        // Add signup button
        gbc.gridy = 3;
        signupButton.setPreferredSize(new Dimension(150, 60)); // Set button size
        buttonPanel.add(signupButton, gbc);

        // Add action listeners to login and signup buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                LoginUI loginUI = new LoginUI();
                loginUI.setVisible(true); // Show the login page (implement this method to open LoginUI)
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                SignUpUI signUpUI = new SignUpUI();
                signUpUI.setVisible(true); // Show the signup page (implement this method to open SignUpUI)
            }
        });

        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    // Implement methods to show LoginUI and SignUpUI based on your implementation

    public static void main(String[] args) {
        // Display login or signup page
        SwingUtilities.invokeLater(() -> {
            StartingUI startingUI = new StartingUI();
            startingUI.setVisible(true);
        });
    }
}
