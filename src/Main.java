import DataBase.CourseDAO;
import DataBase.DataBaseLink;
import DataBase.StudentDAO;
import Entities.Course;
import Entities.Student;
import UI.LoginUI;
import UI.StartingUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Display login UI
        SwingUtilities.invokeLater(() -> {
            StartingUI loginUI = new StartingUI();
            loginUI.setVisible(true);
        });

    }
}
