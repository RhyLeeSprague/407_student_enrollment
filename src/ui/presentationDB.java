package ui;

import java.util.*;

public class presentationDB {

    public static class Course {
        public String id;
        public String name;
        public String instructor;
        public String schedule;

        public Course(String id, String name, String instructor, String schedule) {
            this.id = id;
            this.name = name;
            this.instructor = instructor;
            this.schedule = schedule;
        }
    }

    public static class Enrollment {
        public String studentId;
        public String courseId;

        public Enrollment(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }
    }

    public static Map<String, Course> courses = new HashMap<>();
    public static List<Enrollment> enrollments = new ArrayList<>();

    static {
        courses.put("1", new Course("1", "Intro to Java", "Dr. Smith", "MWF 10AM"));
    }

    public static void addCourse(String id, String name, String instructor, String schedule) {
        courses.put(id, new Course(id, name, instructor, schedule));
    }

    public static void enroll(String studentId, String courseId) {
        enrollments.add(new Enrollment(studentId, courseId));
    }

    public static List<Course> getInstructorCourses(String instructor) {
        List<Course> list = new ArrayList<>();
        for (Course c : courses.values()) {
            if (c.instructor.equalsIgnoreCase(instructor)) {
                list.add(c);
            }
        }
        return list;
    }

    public static List<Course> getStudentCourses(String studentId) {
        List<Course> list = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.studentId.equals(studentId)) {
                if (courses.containsKey(e.courseId)) {
                    list.add(courses.get(e.courseId));
                }
            }
        }
        return list;
    }
}

