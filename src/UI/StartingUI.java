package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartingUI extends JFrame {
    private JButton loginButton;
    private JButton signupButton;

    public StartingUI() {
        setTitle("Course Registration Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with BorderLayout to center the buttons
        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        // Create a panel to hold the buttons with adjusted size
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Use FlowLayout for centered buttons
        Dimension buttonSize = new Dimension(200, 75); // Adjust size to your preference
        buttonPanel.setSize(buttonSize);
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Create components
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        // No need to set button size explicitly now (using preferred size)
        loginButton.setPreferredSize(buttonSize);
        signupButton.setPreferredSize(buttonSize);

        // Add components to the button panel
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        // Add action listeners to login and signup buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                LoginUI loginUI = new LoginUI();
                loginUI.setVisible(true);// Show the login page (implement this method to open LoginUI)
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                SignUpUI signUpUI = new SignUpUI();
                signUpUI.setVisible(true);// Show the signup page (implement this method to open SignUpUI)
            }
        });

        // Add label "Course Registration Platform"
        JLabel titleLabel = new JLabel("Course Registration Platform");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Pack the frame to adjust its size based on the components
        pack();
        // Center the frame on the screen
        setLocationRelativeTo(null);
    }

    // Implement methods to show LoginUI and SignUpUI based on your implementation

    public static void main(String[] args) {
        // Display login or signup page
        SwingUtilities.invokeLater(() -> {
            StartingUI loginPage = new StartingUI();
            loginPage.setVisible(true);
        });
    }
}
