package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InstructorFrame extends JFrame {

    private JTextField instructorIDField;
    private JTextArea courseListArea;

    public InstructorFrame() {
        setTitle("Instructor Portal");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(new JLabel("Instructor ID: "));

        instructorIDField = new JTextField();
        topPanel.add(instructorIDField);

        JButton viewCoursesButton = new JButton("View Courses");
        viewCoursesButton.addActionListener(e -> loadInstructorCourses());

        courseListArea = new JTextArea();
        courseListArea.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(courseListArea), BorderLayout.CENTER);
        add(viewCoursesButton, BorderLayout.SOUTH);
    }

    private void loadInstructorCourses() {
        String instructorID = instructorIDField.getText().trim();

        if (instructorID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter an Instructor ID");
            return;
        }

        Connection conn = DatabaseSetup.getConnection();
        if (conn == null) {
            courseListArea.setText("Database connection failed.");
            return;
        }

        try {
            String sql = "SELECT name FROM courses WHERE instructor_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, instructorID);

            ResultSet rs = pstmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getString("name")).append("\n");
            }

            courseListArea.setText(sb.length() > 0 ? sb.toString() : "No courses found.");

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException ex) {
            courseListArea.setText("Error loading courses.");
        }
    }
}

