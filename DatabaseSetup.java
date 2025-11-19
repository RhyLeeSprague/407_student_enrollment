import java.sql.*;

public class DatabaseSetup {

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:university.db");
             Statement stmt = conn.createStatement()) {

            // --- Create tables ---
            stmt.execute("CREATE TABLE IF NOT EXISTS departments (department_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS students (student_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, department_id INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS instructors (instructor_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, department_id INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS courses (course_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, department_id INTEGER, instructor_id INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS enrollments (enrollment_id INTEGER PRIMARY KEY AUTOINCREMENT, student_id INTEGER, course_id INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS grades (grade_id INTEGER PRIMARY KEY AUTOINCREMENT, enrollment_id INTEGER, grade TEXT)");

            // --- Seed demo data ---
            stmt.executeUpdate("INSERT OR IGNORE INTO departments (department_id, name) VALUES (1, 'Computer Science')");
            stmt.executeUpdate("INSERT OR IGNORE INTO students (student_id, name, email, department_id) VALUES (1, 'John Doe', 'john@uni.edu', 1)");
            stmt.executeUpdate("INSERT OR IGNORE INTO instructors (instructor_id, name, email, department_id) VALUES (1, 'Dr. Smith', 'smith@uni.edu', 1)");
            stmt.executeUpdate("INSERT OR IGNORE INTO courses (course_id, name, department_id, instructor_id) VALUES (1, 'Intro to Java', 1, 1)");

            System.out.println("✅ Database setup completed.");

        } catch (SQLException e) {
            System.err.println("❌ Database setup failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
