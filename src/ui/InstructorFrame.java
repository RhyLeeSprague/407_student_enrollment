package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InstructorFrame extends JFrame {
    private JComboBox<String> courseList;
    private JTextArea studentArea;
    private JTextField gradeField;
    private Connection conn;

    public InstructorFrame() {
        setTitle("Instructor Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:university.db");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "DB Connection Failed: " + e.getMessage());
        }

        courseList = new JComboBox<>();
        JButton viewBtn = new JButton("View Enrolled Students");
        JButton gradeBtn = new JButton("Submit Grade");
        gradeField = new JTextField(5);
        studentArea = new JTextArea(10, 40);

        loadCourses();

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Your Courses:"));
        topPanel.add(courseList);
        topPanel.add(viewBtn);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Grade:"));
        bottomPanel.add(gradeField);
        bottomPanel.add(gradeBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(studentArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        viewBtn.addActionListener(e -> viewStudents());
        gradeBtn.addActionListener(e -> submitGrades());

        setVisible(true);
    }

    private void loadCourses() {
        try (PreparedStatement ps = conn.prepareStatement("SELECT name FROM courses WHERE instructor_id=?")) {
            ps.setInt(1, 1); // demo instructor_id = 1
            ResultSet rs = ps.executeQuery();
            while (rs.next()) courseList.addItem(rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewStudents() {
        studentArea.setText("");
        String selected = (String) courseList.getSelectedItem();
        if (selected == null) return;
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT s.name FROM students s JOIN enrollments e ON s.student_id=e.student_id " +
                        "WHERE e.course_id=(SELECT course_id FROM courses WHERE name=?)")) {
            ps.setString(1, selected);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) studentArea.append(rs.getString("name") + "\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void submitGrades() {
        String selected = (String) courseList.getSelectedItem();
        String grade = gradeField.getText().trim();
        if (selected == null || grade.isEmpty()) return;

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO grades (enrollment_id, grade) " +
                        "SELECT e.enrollment_id, ? FROM enrollments e " +
                        "JOIN courses c ON e.course_id = c.course_id WHERE c.name=?")) {
            ps.setString(1, grade);
            ps.setString(2, selected);
            int inserted = ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Grades submitted for " + inserted + " student(s).");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error submitting grades: " + e.getMessage());
        }
    }
}