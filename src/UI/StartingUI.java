package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartingUI extends JFrame {
    private JButton loginButton;
    private JButton signupButton;

    public StartingUI() {
        setTitle("Course Registration Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500); // Set the frame size to 700 by 500

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

        Font buttonPixelFont = new Font("04b03",Font.PLAIN,23);
        loginButton.setFont(buttonPixelFont);
        signupButton.setFont(buttonPixelFont);

        // Set button border and background for high contrast
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border with 2 pixels thickness
        signupButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Black border with 2 pixels thickness
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

        // Add login button
        gbc.gridx = 0;
        gbc.gridy = 1; // Move down to accommodate label padding
        loginButton.setPreferredSize(new Dimension(100, 50)); // Set button size
        buttonPanel.add(loginButton, gbc);

        // Add signup button
        gbc.gridy = 2;
        signupButton.setPreferredSize(new Dimension(100, 50)); // Set button size
        buttonPanel.add(signupButton, gbc);

        // Add label "Course Registration Platform" with reduced top padding
        JLabel titleLabel = new JLabel("Course Registration Platform");
//        try {
//            // Load the custom font from a file
//            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("Pixel Digivolve.otf")).deriveFont(24f);
//
//        } catch (FontFormatException | IOException e) {
//            e.printStackTrace();
//            // Fallback to a default font if there's an error
//            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        }
        Font pixelFont = new Font("04b03",Font.PLAIN,30);
        titleLabel.setFont(pixelFont);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(20, 0, 50, 0); // Reduce top padding to 20 pixels
        gbc.gridy = 0;
        buttonPanel.add(titleLabel, gbc);

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
