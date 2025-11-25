package ui;

import javax.swing.*;
import java.awt.*;
// import java.sql.*;   // original import
public class EnrollmentGUI extends JFrame {

    private JTextField studentIDField;
    private JTextField courseIDField;

    public EnrollmentGUI() {
        setTitle("Course Enrollment");
        setSize(350, 200);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Student ID:"));
        studentIDField = new JTextField();
        add(studentIDField);

        add(new JLabel("Course ID:"));
        courseIDField = new JTextField();
        add(courseIDField);

        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(e -> enrollStudent());

        add(new JLabel());
        add(enrollButton);
        add(new BackButtonPanel(this));
    }

    /*
    // original version

    private void enrollStudent() {
        String studentID = studentIDField.getText().trim();
        String courseID = courseIDField.getText().trim();

        if (studentID.isEmpty() || courseID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        Connection conn = DatabaseSetup.getConnection();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed.");
            return;
        }

        try {
            String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, studentID);
            pstmt.setString(2, courseID);

            pstmt.executeUpdate();

            pstmt.close();
            conn.close();

            JOptionPane.showMessageDialog(this, "Enrollment successful!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error enrolling student.");
        }
    }
    */

    // demo version
    private void enrollStudent() {
        String studentID = studentIDField.getText().trim();
        String courseID = courseIDField.getText().trim();

        if (studentID.isEmpty() || courseID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        presentationDB.enroll(studentID, courseID);

        JOptionPane.showMessageDialog(this, "Enrollment successful!");
    }
}



