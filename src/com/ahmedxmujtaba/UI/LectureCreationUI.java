package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.CourseDAO;
import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.LectureDAO;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Instructor;
import com.ahmedxmujtaba.Entities.Lecture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LectureCreationUI extends JFrame {
    private Course course;
    private Instructor instructor;
    private JTextField lectureTitleField;
    private JButton submitButton;
    private JButton cancelButton;
    private JPanel notesPanel;
    private JPanel videosPanel;
    private ArrayList<JTextField> notesTitleFields;
    private ArrayList<JTextArea> notesContentFields;
    private ArrayList<JTextField> videoLinkFields;
    private ArrayList<JTextField> videoTitleFields;
    private ArrayList<JTextArea> videoDescriptionFields;

    public LectureCreationUI(Course course, Instructor instructor) {
        this.course = course;
        this.instructor = instructor;

        ImageIcon icon = new ImageIcon(("src/com/ahmedxmujtaba/UI/Icons/icon1.png"));
        setIconImage(icon.getImage());

        setTitle("Create Lecture");
        setSize(900, 600); // Increased size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set font
        Font customFont = new Font("04b03", Font.PLAIN, 24);

        // Create components
        JLabel titleLabel = new JLabel("Lecture Title:");
        titleLabel.setFont(customFont);
        lectureTitleField = new JTextField();
        lectureTitleField.setFont(customFont);
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        // Set button properties
        submitButton.setFont(customFont);
        submitButton.setBackground(Color.WHITE);
        submitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        submitButton.setFocusPainted(false);

        cancelButton.setFont(customFont);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setFocusPainted(false);

        // Add action listener to submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createLecture();
            }
        });

        // Add action listener to cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Initialize notes and videos panels
        notesPanel = new JPanel();
        notesPanel.setLayout(new BoxLayout(notesPanel, BoxLayout.Y_AXIS));
        videosPanel = new JPanel();
        videosPanel.setLayout(new BoxLayout(videosPanel, BoxLayout.Y_AXIS));
        notesTitleFields = new ArrayList<>();
        notesContentFields = new ArrayList<>();
        videoLinkFields = new ArrayList<>();
        videoTitleFields = new ArrayList<>();
        videoDescriptionFields = new ArrayList<>();

        // Add initial note and video fields
        addNoteFields();
        addVideoFields();

        // Button to add more notes fields
        JButton addNoteButton = new JButton("Add Note");
        addNoteButton.setFont(customFont);
        addNoteButton.setBackground(Color.WHITE);
        addNoteButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addNoteButton.setFocusPainted(false);
        addNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNoteFields();
            }
        });

        // Button to add more video fields
        JButton addVideoButton = new JButton("Add Video");
        addVideoButton.setFont(customFont);
        addVideoButton.setBackground(Color.WHITE);
        addVideoButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addVideoButton.setFocusPainted(false);
        addVideoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVideoFields();
            }
        });

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(customFont);
        backButton.setBackground(Color.WHITE);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                CoursePanelInstructorUI coursePanelInstructorUI = new CoursePanelInstructorUI(course,instructor);
                coursePanelInstructorUI.setVisible(true);
            }
        });

        // Set layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Add components to the frame using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(lectureTitleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(addNoteButton, gbc);

        gbc.gridy++;
        add(addVideoButton, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(notesPanel), gbc);

        gbc.gridy++;
        add(new JScrollPane(videosPanel), gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(submitButton, gbc);

        gbc.gridx = 1;
        add(cancelButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(backButton, gbc);
    }

    private void addNoteFields() {
        JPanel notePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel noteTitleLabel = new JLabel("Note Title:");
        noteTitleLabel.setFont(new Font("04b03", Font.PLAIN, 20));
        JTextField noteTitleField = new JTextField(15);
        noteTitleField.setFont(new Font("04b03", Font.PLAIN, 20));
        notesTitleFields.add(noteTitleField);

        JLabel noteContentLabel = new JLabel("Note Content:");
        noteContentLabel.setFont(new Font("04b03", Font.PLAIN, 20));
        JTextArea noteContentArea = new JTextArea(5, 15); // Increased size
        noteContentArea.setFont(new Font("04b03", Font.PLAIN, 20));
        noteContentArea.setLineWrap(true);
        noteContentArea.setWrapStyleWord(true);
        notesContentFields.add(noteContentArea);

        gbc.gridx = 0;
        gbc.gridy = 0;
        notePanel.add(noteTitleLabel, gbc);
        gbc.gridx = 1;
        notePanel.add(noteTitleField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        notePanel.add(noteContentLabel, gbc);
        gbc.gridx = 1;
        notePanel.add(new JScrollPane(noteContentArea), gbc);

        notesPanel.add(notePanel);
        notesPanel.revalidate();
        notesPanel.repaint();
    }

    private void addVideoFields() {
        JPanel videoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel videoLinkLabel = new JLabel("Video Link:");
        videoLinkLabel.setFont(new Font("04b03", Font.PLAIN, 20));
        JTextField videoLinkField = new JTextField(15);
        videoLinkField.setFont(new Font("04b03", Font.PLAIN, 20));
        videoLinkFields.add(videoLinkField);

        JLabel videoTitleLabel = new JLabel("Video Title:");
        videoTitleLabel.setFont(new Font("04b03", Font.PLAIN, 20));
        JTextField videoTitleField = new JTextField(15);
        videoTitleField.setFont(new Font("04b03", Font.PLAIN, 20));
        videoTitleFields.add(videoTitleField);

        JLabel videoDescriptionLabel = new JLabel("Video Description:");
        videoDescriptionLabel.setFont(new Font("04b03", Font.PLAIN, 20));
        JTextArea videoDescriptionField = new JTextArea(5, 15); // Increased size
        videoDescriptionField.setFont(new Font("04b03", Font.PLAIN, 20));
        videoDescriptionField.setLineWrap(true);
        videoDescriptionField.setWrapStyleWord(true);
        videoDescriptionFields.add(videoDescriptionField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        videoPanel.add(videoLinkLabel, gbc);
        gbc.gridx = 1;
        videoPanel.add(videoLinkField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        videoPanel.add(videoTitleLabel, gbc);
        gbc.gridx = 1;
        videoPanel.add(videoTitleField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        videoPanel.add(videoDescriptionLabel, gbc);
        gbc.gridx = 1;
        videoPanel.add(new JScrollPane(videoDescriptionField), gbc);

        videosPanel.add(videoPanel);
        videosPanel.revalidate();
        videosPanel.repaint();
    }

    private void createLecture() {
        String lectureTitle = lectureTitleField.getText().trim();
        if (lectureTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lecture title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Lecture lecture = new Lecture(course.getId(), lectureTitle);
        LectureDAO lectureDAO = new LectureDAO(new DataBaseLink());

        int generatedId = lectureDAO.addLecture(lecture);

        // Save notes
        for (int i = 0; i < notesTitleFields.size(); i++) {
            String noteTitle = notesTitleFields.get(i).getText().trim();
            String noteContent = notesContentFields.get(i).getText().trim();
            if (!noteTitle.isEmpty() && !noteContent.isEmpty()) {
                lectureDAO.addNoteToLecture(generatedId, noteTitle, noteContent);
            }
        }

        // Save videos
        for (int i = 0; i < videoLinkFields.size(); i++) {
            String videoLink = videoLinkFields.get(i).getText().trim();
            String videoTitle = videoTitleFields.get(i).getText().trim();
            String videoDescription = videoDescriptionFields.get(i).getText().trim();
            if (!videoLink.isEmpty() && !videoTitle.isEmpty() && !videoDescription.isEmpty()) {
                lectureDAO.addVideoToLecture(generatedId, videoLink, videoTitle, videoDescription);
            }
        }

        JOptionPane.showMessageDialog(this, "Lecture created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        CourseDAO courseDAO = new CourseDAO(new DataBaseLink());
        //we need to update the course details from database
        CoursePanelInstructorUI coursePanelInstructorUI = new CoursePanelInstructorUI(courseDAO.getCourseById(course.getId()),instructor);
        coursePanelInstructorUI.dispose();

    }
}
