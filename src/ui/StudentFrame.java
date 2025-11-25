package ui;

import javax.swing.*;
import java.awt.*;
// import java.sql.*;  // original import

public class StudentFrame extends JFrame {

    private JTextField studentIDField;
    private JTextArea resultArea;

    public StudentFrame() {
        setTitle("Student Portal");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Student ID:"));

        studentIDField = new JTextField();
        inputPanel.add(studentIDField);

        JButton viewCoursesButton = new JButton("View Enrolled Courses");
        viewCoursesButton.addActionListener(e -> loadCourses());

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(viewCoursesButton, BorderLayout.CENTER);
        bottomPanel.add(new BackButtonPanel(this), BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /*
    // original version

    private void loadCourses() {
        String studentID = studentIDField.getText().trim();
        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a Student ID");
            return;
        }

        Connection conn = DatabaseSetup.getConnection();
        if (conn == null) {
            resultArea.setText("Database connection failed.");
            return;
        }

        try {
            String sql = """
                SELECT courses.name
                FROM enrollments
                JOIN courses ON enrollments.course_id = courses.course_id
                WHERE enrollments.student_id = ?
            """;

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentID);

            ResultSet rs = pstmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getString("name")).append("\n");
            }

            resultArea.setText(sb.length() > 0 ? sb.toString() : "No courses found.");

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException ex) {
            resultArea.setText("Error loading courses.");
        }
    }
    */

    // demo version
    private void loadCourses() {
        String studentID = studentIDField.getText().trim();

        if (studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a Student ID");
            return;
        }

        var list = presentationDB.getStudentCourses(studentID);

        StringBuilder sb = new StringBuilder();
        for (var c : list) {
            sb.append(c.name)
              .append(" - ")
              .append(c.schedule)
              .append("\n");
        }

        resultArea.setText(sb.length() > 0 ? sb.toString() : "No courses found.");
    }
}


