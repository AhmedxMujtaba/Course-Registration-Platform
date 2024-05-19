package UI;

import Entities.Student;

import javax.swing.*;
import java.awt.*;

public class StudentUI extends JFrame {
    private Student student;

    public StudentUI(Student student) {
        this.student = student;

        setTitle("Student Portal");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add UI components for the student portal here
        // For now, we'll just display a welcome message

        JLabel welcomeLabel = new JLabel("Welcome, " + student.getName() + "!");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        add(welcomeLabel, BorderLayout.CENTER);

        setVisible(true);
    }
}
