import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EnrollmentGUI extends JFrame {

    public EnrollmentGUI() {
        setTitle("Student Enrollment System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Select User Role", SwingConstants.CENTER);
        JButton studentBtn = new JButton("Student");
        JButton instructorBtn = new JButton("Instructor");
        JButton adminBtn = new JButton("Admin");

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(label);
        panel.add(studentBtn);
        panel.add(instructorBtn);
        panel.add(adminBtn);

        add(panel);

        // Event Listeners
        studentBtn.addActionListener(e -> new StudentFrame());
        instructorBtn.addActionListener(e -> new InstructorFrame());
        adminBtn.addActionListener(e -> new AdminFrame());
    }

    public static void main(String[] args) {
        DatabaseSetup.initializeDatabase();
        SwingUtilities.invokeLater(() -> new EnrollmentGUI().setVisible(true));
    }
}

// required student GUI implementation for jswing(subject to change)
class StudentFrame extends JFrame {
    private JComboBox<String> courseList;
    private JTextArea scheduleArea;
    private Connection conn;

    public StudentFrame() {
        setTitle("Student Dashboard");
        setSize(500, 400);
        setLocationRelativeTo(null);

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:university.db"); // or MySQL URL
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Connection Failed: " + e.getMessage());
        }

        JLabel label = new JLabel("Available Courses:");
        courseList = new JComboBox<>();
        JButton registerBtn = new JButton("Register");
        JButton viewBtn = new JButton("View Schedule");
        scheduleArea = new JTextArea(10, 30);

        loadCourses();

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(courseList);
        panel.add(registerBtn);
        panel.add(viewBtn);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(scheduleArea), BorderLayout.CENTER);

        registerBtn.addActionListener(e -> registerCourse());
        viewBtn.addActionListener(e -> viewSchedule());

        setVisible(true);
    }

    private void loadCourses() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM courses")) {
            while (rs.next()) {
                courseList.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerCourse() {
        String selected = (String) courseList.getSelectedItem();
        if (selected != null) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO enrollments (student_id, course_id) VALUES (?, " +
                            "(SELECT course_id FROM courses WHERE name=?))")) {
                ps.setInt(1, 1); // Demo student_id = 1
                ps.setString(2, selected);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Registered for " + selected);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void viewSchedule() {
        scheduleArea.setText("");
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT c.name FROM courses c " +
                "JOIN enrollments e ON c.course_id = e.course_id WHERE e.student_id=?")) {
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                scheduleArea.append(rs.getString("name") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// creates the instructor side of the GUI
class InstructorFrame extends JFrame {
    public InstructorFrame() {
        setTitle("Instructor Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        JTextArea area = new JTextArea("Instructor tools go here...");
        add(new JScrollPane(area));
        setVisible(true);
    }
}

class AdminFrame extends JFrame {  //creates the admin specification for th Gui
    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        JTextArea area = new JTextArea("Admin controls go here...");
        add(new JScrollPane(area));
        setVisible(true);
    }
}
