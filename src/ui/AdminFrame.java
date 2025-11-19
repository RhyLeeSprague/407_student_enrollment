package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminFrame extends JFrame {
    private Connection conn;
    private JTextField deptField, instructorField, instructorEmail, courseField;
    private JComboBox<String> deptList;

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:university.db");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "DB Connection Failed: " + e.getMessage());
        }

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Departments", buildDeptTab());
        tabs.add("Instructors", buildInstructorTab());
        tabs.add("Courses", buildCourseTab());
        add(tabs);

        setVisible(true);
    }

    private JPanel buildDeptTab() {
        deptField = new JTextField(15);
        JButton addBtn = new JButton("Add Department");
        addBtn.addActionListener(e -> addDepartment());

        JPanel p = new JPanel();
        p.add(new JLabel("Department Name:"));
        p.add(deptField);
        p.add(addBtn);
        return p;
    }

    private JPanel buildInstructorTab() {
        instructorField = new JTextField(10);
        instructorEmail = new JTextField(10);
        deptList = new JComboBox<>();
        loadDepartments();
        JButton addBtn = new JButton("Add Instructor");
        addBtn.addActionListener(e -> addInstructor());

        JPanel p = new JPanel();
        p.add(new JLabel("Name:"));
        p.add(instructorField);
        p.add(new JLabel("Email:"));
        p.add(instructorEmail);
        p.add(new JLabel("Dept:"));
        p.add(deptList);
        p.add(addBtn);
        return p;
    }

    private JPanel buildCourseTab() {
        courseField = new JTextField(10);
        JButton addBtn = new JButton("Add Course");
        addBtn.addActionListener(e -> addCourse());
        JPanel p = new JPanel();
        p.add(new JLabel("Course Name:"));
        p.add(courseField);
        p.add(addBtn);
        return p;
    }

    private void loadDepartments() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM departments")) {
            while (rs.next()) deptList.addItem(rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDepartment() {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO departments (name) VALUES (?)")) {
            ps.setString(1, deptField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Department added.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void addInstructor() {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO instructors (name, email, department_id) VALUES (?, ?, (SELECT department_id FROM departments WHERE name=?))")) {
            ps.setString(1, instructorField.getText());
            ps.setString(2, instructorEmail.getText());
            ps.setString(3, (String) deptList.getSelectedItem());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Instructor added.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void addCourse() {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO courses (name, department_id, instructor_id) VALUES (?, 1, 1)")) {
            ps.setString(1, courseField.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Course added.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
