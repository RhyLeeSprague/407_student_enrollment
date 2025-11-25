package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import java.sql.*;  //original import

public class AdminFrame extends JFrame {

    private JTextField courseIDField;
    private JTextField courseNameField;
    private JTextField instructorField;
    private JTextField scheduleField;

    public AdminFrame() {
        setTitle("Admin Control Panel");
        setSize(400, 300);
        setLayout(new GridLayout(7, 2));

        JLabel courseIDLabel = new JLabel("Course ID:");
        JLabel courseNameLabel = new JLabel("Course Name:");
        JLabel instructorLabel = new JLabel("Instructor:");
        JLabel scheduleLabel = new JLabel("Schedule:");

        courseIDField = new JTextField();
        courseNameField = new JTextField();
        instructorField = new JTextField();
        scheduleField = new JTextField();

        JButton addButton = new JButton("Add Course");

        add(courseIDLabel);
        add(courseIDField);
        add(courseNameLabel);
        add(courseNameField);
        add(instructorLabel);
        add(instructorField);
        add(scheduleLabel);
        add(scheduleField);
        add(new JLabel());
        add(addButton);
        add(new BackButtonPanel(this));
        addButton.addActionListener(e -> addCourse());
    }

    /*
    // original version
    private void addCourse() {
        String courseID = courseIDField.getText();
        String courseName = courseNameField.getText();
        String instructor = instructorField.getText();
        String schedule = scheduleField.getText();

        if (courseID.isEmpty() || courseName.isEmpty() || instructor.isEmpty() || schedule.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
            return;
        }

        Connection conn = DatabaseSetup.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed.");
            return;
        }

        try {
            String sql = "INSERT INTO Courses (CourseID, CourseName, Instructor, Schedule) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, courseID);
            pstmt.setString(2, courseName);
            pstmt.setString(3, instructor);
            pstmt.setString(4, schedule);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();

            JOptionPane.showMessageDialog(this, "Course added successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding course.");
        }
    }
    */

    // demo version (for now)
    private void addCourse() {
        String courseID = courseIDField.getText();
        String courseName = courseNameField.getText();
        String instructor = instructorField.getText();
        String schedule = scheduleField.getText();

        if (courseID.isEmpty() || courseName.isEmpty() || instructor.isEmpty() || schedule.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!");
            return;
        }

        presentationDB.addCourse(courseID, courseName, instructor, schedule);

        JOptionPane.showMessageDialog(this, "Course added successfully!");
    }
}



