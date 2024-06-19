package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.CourseDAO;
import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.StudentDAO;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Billing extends JFrame {

    private JLabel billingLabel;
    private JLabel expLabel;
    private JLabel cvvLabel;
    private JLabel creditCardNoLabel;
    private JTextField creditCardNoField;
    private JTextField cvvField;
    private JTextField expField;
    private JButton backButton;
    private JButton processButton;
    private JLabel errorLabel;
    private Student student;
    private Course course;

    public Billing(Course course, Student student) {

        this.course = course;
        this.student = student;

        ImageIcon icon = new ImageIcon("src/com/ahmedxmujtaba/UI/Icons/icon1.png");
        setIconImage(icon.getImage());

        setTitle("Billing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600); // Set the frame size to 900 by 600
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set the background color of the frame to white
        getContentPane().setBackground(Color.WHITE);

        // Initialize components
        billingLabel = new JLabel("Billing");
        expLabel = new JLabel("Expiry Date (MM/YY):");
        cvvLabel = new JLabel("CVV:");
        creditCardNoLabel = new JLabel("Credit Card Number:");
        creditCardNoField = new JTextField(20);
        cvvField = new JTextField(4);
        expField = new JTextField(5);
        backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backButton.setBackground(Color.white);
        backButton.setPreferredSize(new Dimension(250, 60));
        processButton = new JButton("Process Payment");
        processButton.setFocusable(false);
        processButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        processButton.setBackground(Color.white);
        processButton.setPreferredSize(new Dimension(300, 60));

        errorLabel = new JLabel(); // Initialize the error label
        errorLabel.setForeground(Color.RED); // Set error text color to red

        // Set fonts
        Font labelFont = new Font("04b03", Font.PLAIN, 40);
        Font fieldFont = new Font("04b03", Font.PLAIN, 30);
        billingLabel.setFont(labelFont);
        expLabel.setFont(fieldFont);
        cvvLabel.setFont(fieldFont);
        creditCardNoLabel.setFont(fieldFont);
        creditCardNoField.setFont(fieldFont);
        cvvField.setFont(fieldFont);
        expField.setFont(fieldFont);
        backButton.setFont(fieldFont);
        processButton.setFont(fieldFont);
        errorLabel.setFont(fieldFont.deriveFont(20f)); // Set error label font

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(billingLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(creditCardNoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(creditCardNoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(expLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(expField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(cvvLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(cvvField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(backButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(processButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(errorLabel, gbc); // Add error label to the frame

        // Add action listeners
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the back button
                dispose();
                new CourseDetailsUI(course, student.getId()).setVisible(true);
            }
        });

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the process payment button
                if (validateInput()) {
                    String creditCardNumber = creditCardNoField.getText();
                    String cvv = cvvField.getText();
                    String expiryDate = expField.getText();

                    // Show OTP dialog
                    showOtpDialog();
                }
            }
        });
    }

    private boolean validateInput() {
        String creditCardNumber = creditCardNoField.getText();
        String cvv = cvvField.getText();
        String expiryDate = expField.getText();

        if (creditCardNumber.isEmpty() || cvv.isEmpty() || expiryDate.isEmpty()) {
            errorLabel.setText("All fields must be filled out.");
            return false;
        }

        if (!creditCardNumber.matches("\\d{16}")) {
            errorLabel.setText("Credit card number must be 16 digits.");
            return false;
        }

        if (!cvv.matches("\\d{3,4}")) {
            errorLabel.setText("CVV must be 3 or 4 digits.");
            return false;
        }

        if (!expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            errorLabel.setText("Expiry date must be in the format MM/YY.");
            return false;
        }

        errorLabel.setText(""); // Clear error message if validation passes
        return true;
    }

    private void showOtpDialog() {
        // Create a dialog for entering OTP
        JDialog otpDialog = new JDialog(this, "Enter OTP", true);
        otpDialog.setSize(300, 200);
        otpDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel otpLabel = new JLabel("Enter OTP:");
        otpLabel.setFont(new Font("04b03", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        otpDialog.add(otpLabel, gbc);

        JTextField otpField = new JTextField(6);
        otpField.setFont(new Font("04b03", Font.PLAIN, 20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        otpDialog.add(otpField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("04b03", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        otpDialog.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String otp = otpField.getText();
                if (otp.matches("\\d{6}")) {
                    // OTP is valid, proceed with payment processing
                    JOptionPane.showMessageDialog(otpDialog, "Payment processed successfully!");
                    StudentDAO studentDAO = new StudentDAO(new DataBaseLink());
                    studentDAO.registerCourse(student,course);
                    otpDialog.dispose();
                    dispose();
                    new StudentUI(student).setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(otpDialog, "Invalid OTP. Please enter a 6-digit number.");
                }
            }
        });

        otpDialog.setLocationRelativeTo(this);
        otpDialog.setVisible(true);
    }

}
