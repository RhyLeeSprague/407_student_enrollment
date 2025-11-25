package ui;

import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            String[] options = {"Admin", "Student", "Instructor", "Enrollment"};

            int choice = JOptionPane.showOptionDialog(
                null,
                "Select a panel to open:",
                "Course Enrollment System",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );

            switch (choice) {
                case 0 -> new AdminFrame().setVisible(true);
                case 1 -> new StudentFrame().setVisible(true);
                case 2 -> new InstructorFrame().setVisible(true);
                case 3 -> new EnrollmentGUI().setVisible(true);
                default -> System.exit(0);
            }
        });
    }
}

