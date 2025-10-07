
import java.util.*;

// ---------------- Class: Course ----------------
class Course {
    String courseId;
    String courseName;
    int credits;

    Course(String id, String name, int credits) {
        this.courseId = id;
        this.courseName = name;
        this.credits = credits;
    }

    public String toString() {
        return courseId + " - " + courseName + " (" + credits + " credits)";
    }
}

// ---------------- Class: Student ----------------
class Student {
    String regNo;
    String name;
    HashMap<String, Double> marks = new HashMap<>();

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    public String toString() {
        return regNo + " - " + name;
    }
}

// ---------------- Class: Assessment ----------------
class Assessment {
    String type; // e.g., Internal, Assignment, Final
    double weightage;

    Assessment(String type, double weightage) {
        this.type = type;
        this.weightage = weightage;
    }

    public String toString() {
        return type + " (" + weightage + "%)";
    }
}

// ---------------- Class: ExamSchedule ----------------
class ExamSchedule {
    String courseId;
    String date;

    ExamSchedule(String courseId, String date) {
        this.courseId = courseId;
        this.date = date;
    }

    public String toString() {
        return "Exam for " + courseId + " on " + date;
    }
}

// ---------------- Class: MarkEntry ----------------
class MarkEntry {
    Student student;
    Course course;
    double totalMarks;

    MarkEntry(Student student, Course course, double marks) {
        this.student = student;
        this.course = course;
        this.totalMarks = marks;
    }
}

// ---------------- Class: Grade ----------------
class Grade {
    static String calculateGrade(double marks) {
        if (marks >= 90) return "O";
        else if (marks >= 80) return "A+";
        else if (marks >= 70) return "A";
        else if (marks >= 60) return "B";
        else if (marks >= 50) return "C";
        else return "F";
    }

    static double gradePoint(String grade) {
        switch (grade) {
            case "O": return 10;
            case "A+": return 9;
            case "A": return 8;
            case "B": return 7;
            case "C": return 6;
            default: return 0;
        }
    }
}

// ---------------- Class: Transcript ----------------
class Transcript {
    Student student;
    ArrayList<MarkEntry> markEntries = new ArrayList<>();

    Transcript(Student s) {
        this.student = s;
    }

    void addMark(MarkEntry m) {
        markEntries.add(m);
    }

    void generateTranscript() {
        double totalPoints = 0, totalCredits = 0;
        System.out.println("\nTranscript for: " + student.name);
        System.out.println("----------------------------------");
        for (MarkEntry me : markEntries) {
            String grade = Grade.calculateGrade(me.totalMarks);
            double gp = Grade.gradePoint(grade);
            totalPoints += gp * me.course.credits;
            totalCredits += me.course.credits;
            System.out.println(me.course.courseName + " - " + me.totalMarks + " - Grade: " + grade);
        }
        double cgpa = (totalCredits == 0) ? 0 : totalPoints / totalCredits;
        System.out.printf("CGPA: %.2f\n", cgpa);
    }
}

// ---------------- Main Menu-Driven System ----------------
public class UniversityExamsGradingSystem {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Course> courses = new ArrayList<>();
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Assessment> assessments = new ArrayList<>();
    static ArrayList<ExamSchedule> schedules = new ArrayList<>();
    static ArrayList<MarkEntry> markEntries = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== University Exams & Grading System =====");
            System.out.println("1. Add Course");
            System.out.println("2. Add Student");
            System.out.println("3. Create Assessment");
            System.out.println("4. Schedule Exam");
            System.out.println("5. Record Marks");
            System.out.println("6. Publish Grades");
            System.out.println("7. Generate Transcript");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addCourse(); break;
                case 2: addStudent(); break;
                case 3: createAssessment(); break;
                case 4: scheduleExam(); break;
                case 5: recordMarks(); break;
                case 6: publishGrades(); break;
                case 7: generateTranscript(); break;
                case 0: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    static void addCourse() {
        System.out.print("Enter Course ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Course Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Credits: ");
        int credits = sc.nextInt();
        courses.add(new Course(id, name, credits));
        System.out.println("Course added successfully!");
    }

    static void addStudent() {
        System.out.print("Enter Register No: ");
        String reg = sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        students.add(new Student(reg, name));
        System.out.println("Student added successfully!");
    }

    static void createAssessment() {
        System.out.print("Enter Assessment Type: ");
        String type = sc.nextLine();
        System.out.print("Enter Weightage (%): ");
        double w = sc.nextDouble();
        assessments.add(new Assessment(type, w));
        System.out.println("Assessment created successfully!");
    }

    static void scheduleExam() {
        System.out.print("Enter Course ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Exam Date (DD-MM-YYYY): ");
        String date = sc.nextLine();
        schedules.add(new ExamSchedule(id, date));
        System.out.println("Exam scheduled!");
    }

    static void recordMarks() {
        System.out.print("Enter Student Reg No: ");
        String reg = sc.nextLine();
        Student stu = findStudent(reg);
        if (stu == null) { System.out.println("Student not found!"); return; }

        System.out.print("Enter Course ID: ");
        String cid = sc.nextLine();
        Course c = findCourse(cid);
        if (c == null) { System.out.println("Course not found!"); return; }

        System.out.print("Enter Total Marks: ");
        double marks = sc.nextDouble();
        markEntries.add(new MarkEntry(stu, c, marks));
        System.out.println("Marks recorded!");
    }

    static void publishGrades() {
        for (MarkEntry m : markEntries) {
            String grade = Grade.calculateGrade(m.totalMarks);
            System.out.println(m.student.name + " - " + m.course.courseName + " : " + grade);
        }
    }

    static void generateTranscript() {
        System.out.print("Enter Student Reg No: ");
        String reg = sc.nextLine();
        Student stu = findStudent(reg);
        if (stu == null) { System.out.println("Student not found!"); return; }

        Transcript t = new Transcript(stu);
        for (MarkEntry m : markEntries) {
            if (m.student.equals(stu))
                t.addMark(m);
        }
        t.generateTranscript();
    }

    static Student findStudent(String reg) {
        for (Student s : students)
            if (s.regNo.equalsIgnoreCase(reg))
                return s;
        return null;
    }

    static Course findCourse(String id) {
        for (Course c : courses)
            if (c.courseId.equalsIgnoreCase(id))
                return c;
        return null;
    }
}



