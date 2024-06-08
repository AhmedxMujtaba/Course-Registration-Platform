package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.LectureDAO;
import com.ahmedxmujtaba.Entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;



public class StudentLectureUI extends JFrame {
    private Course course;
    private Lecture lecture;
    private Student student;

    public StudentLectureUI(Course course, Lecture lecture, Student student) {

        this.course = course;
        this.lecture = lecture;
        this.student = student;

        // Set the frame properties
        ImageIcon icon = new ImageIcon(("src/com/ahmedxmujtaba/UI/Icons/icon1.png"));
        setIconImage(icon.getImage());

        setTitle("Lecture Details");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set font
        Font customFont = new Font("04b03", Font.PLAIN, 24);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Create title label
        JLabel titleLabel = new JLabel(lecture.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(customFont);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create notes panel
        JPanel notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS));
        notesPanel.setBorder(BorderFactory.createTitledBorder("Notes"));
        notesPanel.setBackground(Color.WHITE);

        // Create videos panel
        JPanel videosPanel = new JPanel();
        videosPanel.setLayout(new BoxLayout(videosPanel, BoxLayout.Y_AXIS));
        videosPanel.setBorder(BorderFactory.createTitledBorder("Videos"));
        videosPanel.setBackground(Color.WHITE);

        LectureDAO lectureDAO = new LectureDAO(new DataBaseLink());
        List<Notes> notes = lectureDAO.getNotesByLectureId(lecture.getId());
        List<Video> videos = lectureDAO.getVideosByLectureId(lecture.getId());

        // Add notes to notes panel
        int number = 0;
        for (Notes note : notes) {
            number++;
            JPanel notePanel = new JPanel(new BorderLayout());
            notePanel.setBackground(Color.WHITE);

            JLabel noteTitleLabel = new JLabel(number + "- " + note.getTitle() + ":");
            noteTitleLabel.setFont(customFont);

            JTextArea noteContentArea = new JTextArea(note.getNotes());
            noteContentArea.setFont(customFont);
            noteContentArea.setEditable(false);
            noteContentArea.setLineWrap(true);
            noteContentArea.setWrapStyleWord(true);
            noteContentArea.setBackground(Color.WHITE);

            notePanel.add(noteTitleLabel, BorderLayout.NORTH);
            notePanel.add(noteContentArea, BorderLayout.CENTER);

            notesPanel.add(notePanel);
            notesPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add some space between notes
        }

        // Add videos to videos panel
        for (Video video : videos) {
            JPanel videoPanel = new JPanel();
            videoPanel.setLayout(new BorderLayout());
            videoPanel.setBackground(Color.WHITE);

            JButton videoButton = new JButton(video.getTitle());
            videoButton.setFont(customFont);
            videoButton.setBackground(Color.WHITE);
            videoButton.setFocusPainted(false);
            videoButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            videoButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // Align button to center horizontally
            videoPanel.add(videoButton, BorderLayout.NORTH);

            JTextArea videoDescriptionArea = new JTextArea(video.getDescription());
            videoDescriptionArea.setFont(customFont);
            videoDescriptionArea.setEditable(false);
            videoDescriptionArea.setLineWrap(true);
            videoDescriptionArea.setWrapStyleWord(true);
            videoDescriptionArea.setBackground(Color.WHITE);

            // Add a small space between the button and the description
            videoDescriptionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            videoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(video.getLink()));
                    } catch (URISyntaxException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            videoPanel.add(videoDescriptionArea, BorderLayout.CENTER); // Add description to center of panel
            videosPanel.add(videoPanel);
            videosPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add some space between videos
        }

        JScrollPane notesScrollPane = new JScrollPane(notesPanel);
        notesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JScrollPane videosScrollPane = new JScrollPane(videosPanel);
        videosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel scrollablePanel = new JPanel();
        scrollablePanel.setLayout(new GridLayout(1, 2));
        scrollablePanel.add(notesScrollPane);
        scrollablePanel.add(videosScrollPane);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(customFont);
        backButton.setBackground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // Back button action listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StudentCourseUI(course, student.getId()).setVisible(true);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(backButton, BorderLayout.CENTER);

        // Add components to the frame
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.NORTH);
        add(scrollablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
