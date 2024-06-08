package com.ahmedxmujtaba.UI;

import com.ahmedxmujtaba.DataBase.CourseDAO;
import com.ahmedxmujtaba.DataBase.DataBaseLink;
import com.ahmedxmujtaba.DataBase.LectureDAO;
import com.ahmedxmujtaba.Entities.Course;
import com.ahmedxmujtaba.Entities.Instructor;
import com.ahmedxmujtaba.Entities.Lecture;
import com.ahmedxmujtaba.Entities.Notes;
import com.ahmedxmujtaba.Entities.Video;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LectureEditUI extends JFrame {
    private Course course;
    private Instructor instructor;
    private Lecture lecture;
    private final LectureDAO lectureDAO = new LectureDAO(new DataBaseLink());
    private JTextField lectureTitleField;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel notesPanel;
    private JPanel videosPanel;
    private ArrayList<JTextField> notesTitleFields;
    private ArrayList<JTextArea> notesContentFields;
    private ArrayList<JTextField> videoLinkFields;
    private ArrayList<JTextField> videoTitleFields;
    private ArrayList<JTextArea> videoDescriptionFields;

    public LectureEditUI(Course course, Instructor instructor, Lecture lecture) {
        this.course = course;
        this.instructor = instructor;
        this.lecture = lecture;

        ImageIcon icon = new ImageIcon(("src/com/ahmedxmujtaba/UI/Icons/icon1.png"));
        setIconImage(icon.getImage());

        setTitle("Edit Lecture");
        setSize(900, 600); // Increased size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set font
        Font customFont = new Font("04b03", Font.PLAIN, 24);

        // Create components
        JLabel titleLabel = new JLabel("Lecture Title:");
        titleLabel.setFont(customFont);
        lectureTitleField = new JTextField(lecture.getTitle());
        lectureTitleField.setFont(customFont);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        // Set button properties
        saveButton.setFont(customFont);
        saveButton.setBackground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        saveButton.setFocusPainted(false);

        cancelButton.setFont(customFont);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setFocusPainted(false);

        // Add action listener to save button
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
            }
        });

        // Add action listener to cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CoursePanelInstructorUI(course,instructor).setVisible(true);
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

        // Add existing notes and videos fields
        addExistingNotes();
        addExistingVideos();

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

        JButton deleteButton = new JButton("Delete Lecture");
        deleteButton.setFont(customFont);
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                lectureDAO.deleteLectureById(lecture.getId());
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
        add(saveButton, gbc);

        gbc.gridx = 1;
        add(cancelButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(deleteButton, gbc);
    }

    private void addExistingNotes() {
        List<Notes> existingNotes = lectureDAO.getNotesByLectureId(lecture.getId());
        for (Notes note : existingNotes) {
            JPanel notePanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            JLabel noteTitleLabel = new JLabel("Note Title:");
            noteTitleLabel.setFont(new Font("04b03", Font.PLAIN, 20));
            JTextField noteTitleField = new JTextField(note.getTitle(), 15);
            noteTitleField.setFont(new Font("04b03", Font.PLAIN, 20));
            noteTitleField.setMinimumSize(new Dimension(300, noteTitleField.getPreferredSize().height)); // Set minimum width
            noteTitleField.setPreferredSize(new Dimension(300, noteTitleField.getPreferredSize().height)); // Set preferred width
            noteTitleField.setMaximumSize(new Dimension(500, noteTitleField.getPreferredSize().height)); // Set maximum width
            notesTitleFields.add(noteTitleField);

            JLabel noteContentLabel = new JLabel("Note Content:");
            noteContentLabel.setFont(new Font("04b03", Font.PLAIN, 20));
            JTextArea noteContentArea = new JTextArea(note.getNotes(), 5, 15); // Increased size
            noteContentArea.setFont(new Font("04b03", Font.PLAIN, 20));
            noteContentArea.setMinimumSize(new Dimension(300, noteContentArea.getPreferredSize().height)); // Set minimum width
            noteContentArea.setPreferredSize(new Dimension(300, noteContentArea.getPreferredSize().height)); // Set preferred width
            noteContentArea.setMaximumSize(new Dimension(500, noteContentArea.getPreferredSize().height)); // Set maximum width
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
    }

    private void addExistingVideos() {
        List<Video> existingVideos = lectureDAO.getVideosByLectureId(lecture.getId());
        for (Video video : existingVideos) {
            JPanel videoPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            JLabel videoLinkLabel = new JLabel("Video Link:");
            videoLinkLabel.setFont(new Font("04b03", Font.PLAIN, 20));
            JTextField videoLinkField = new JTextField(video.getLink(), 15);
            videoLinkField.setFont(new Font("04b03", Font.PLAIN, 20));
            videoLinkField.setMinimumSize(new Dimension(300, videoLinkField.getPreferredSize().height)); // Set minimum width
            videoLinkField.setPreferredSize(new Dimension(300, videoLinkField.getPreferredSize().height)); // Set preferred width
            videoLinkField.setMaximumSize(new Dimension(500, videoLinkField.getPreferredSize().height)); // Set maximum width
            videoLinkFields.add(videoLinkField);

            JLabel videoTitleLabel = new JLabel("Video Title:");
            videoTitleLabel.setFont(new Font("04b03", Font.PLAIN, 20));
            JTextField videoTitleField = new JTextField(video.getTitle(), 15);
            videoTitleField.setFont(new Font("04b03", Font.PLAIN, 20));
            videoTitleField.setMinimumSize(new Dimension(300, videoTitleField.getPreferredSize().height)); // Set minimum width
            videoTitleField.setPreferredSize(new Dimension(300, videoTitleField.getPreferredSize().height)); // Set preferred width
            videoTitleField.setMaximumSize(new Dimension(500, videoTitleField.getPreferredSize().height)); // Set maximum width
            videoTitleFields.add(videoTitleField);

            JLabel videoDescriptionLabel = new JLabel("Video Description:");
            videoDescriptionLabel.setFont(new Font("04b03", Font.PLAIN, 20));
            JTextArea videoDescriptionField = new JTextArea(video.getDescription(), 5, 15); // Increased size
            videoDescriptionField.setFont(new Font("04b03", Font.PLAIN, 20));
            videoDescriptionField.setMinimumSize(new Dimension(300, videoDescriptionField.getPreferredSize().height)); // Set minimum width
            videoDescriptionField.setPreferredSize(new Dimension(300, videoDescriptionField.getPreferredSize().height)); // Set preferred width
            videoDescriptionField.setMaximumSize(new Dimension(500, videoDescriptionField.getPreferredSize().height)); // Set maximum width
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

    private void saveChanges() {
        String lectureTitle = lectureTitleField.getText().trim();
        if (lectureTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lecture title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        lecture.setTitle(lectureTitle);

        // Update notes
        for (int i = 0; i < notesTitleFields.size(); i++) {
            String noteTitle = notesTitleFields.get(i).getText().trim();
            String noteContent = notesContentFields.get(i).getText().trim();
            if (!noteTitle.isEmpty() && !noteContent.isEmpty()) {
                List<Notes> notes = lectureDAO.getNotesByLectureId(lecture.getId());
                notes.get(i).setTitle(noteTitle);
                notes.get(i).setNotes(noteContent);
                //update note by its id using a function in lecture dao
                lectureDAO.saveNoteToLecture(notes.get(i));
            }
        }

        // Update videos
        for (int i = 0; i < videoLinkFields.size(); i++) {
            String videoLink = videoLinkFields.get(i).getText().trim();
            String videoTitle = videoTitleFields.get(i).getText().trim();
            String videoDescription = videoDescriptionFields.get(i).getText().trim();
            if (!videoLink.isEmpty() && !videoTitle.isEmpty() && !videoDescription.isEmpty()) {
                List<Video> videos = lectureDAO.getVideosByLectureId(lecture.getId());
                videos.get(i).setLink(videoLink);
                videos.get(i).setTitle(videoTitle);
                videos.get(i).setDescription(videoDescription);
                lectureDAO.saveVideoToLecture(videos.get(i));
            }
        }

        // Update lecture in the database
        LectureDAO lectureDAO = new LectureDAO(new DataBaseLink());
        lectureDAO.saveLecture(lecture);

        JOptionPane.showMessageDialog(this, "Lecture saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        //get updated data from the database and load it in the UI now.
        CourseDAO courseDAO = new CourseDAO(new DataBaseLink());
        course = courseDAO.getCourseById(course.getId());
        dispose();
        //call the preivious protal
        new CoursePanelInstructorUI(course,instructor).setVisible(true);
    }
}
//todo needs changing..
// ..................