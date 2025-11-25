package ui;

import javax.swing.*;
import java.awt.*;
// import java.sql.*;  /original import

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

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(viewCoursesButton, BorderLayout.CENTER);

        bottom.add(new BackButtonPanel(this), BorderLayout.WEST);

        add(bottom, BorderLayout.SOUTH);
    }

    /*
    // original version

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
    */

    // demo version
    private void loadInstructorCourses() {
        String instructor = instructorIDField.getText().trim();

        if (instructor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter an Instructor ID");
            return;
        }

        var list = presentationDB.getInstructorCourses(instructor);

        StringBuilder sb = new StringBuilder();
        for (var c : list) {
            sb.append(c.name)
              .append(" (")
              .append(c.id)
              .append(")\n");
        }

        courseListArea.setText(sb.length() > 0 ? sb.toString() : "No courses found.");
    }
}


