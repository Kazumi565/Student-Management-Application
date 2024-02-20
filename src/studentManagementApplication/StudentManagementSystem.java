package studentManagementApplication;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

public class StudentManagementSystem {
    private List<Faculty> faculties;
    private SaveManager saveManager;

    public StudentManagementSystem() {
        faculties = new ArrayList<>();
        saveManager = new SaveManager();
        loadState(); // Load previous state
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nStudent Management System Menu:");
            System.out.println("1. Faculty Operations");
            System.out.println("2. General Operations");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    facultyOperations(scanner);
                    break;
                case 2:
                    generalOperations(scanner);
                    break;
                case 3:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Save state before exiting
        saveState();
    }

    private void facultyOperations(Scanner scanner) {
        System.out.println("\nFaculty Operations Menu:");
        System.out.println("1. Create and assign a student to a faculty.");
        System.out.println("2. Graduate a student from a faculty.");
        System.out.println("3. Display current enrolled students.");
        System.out.println("4. Display graduates.");
        System.out.println("5. Check if a student belongs to a faculty.");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                createAndAssignStudent(scanner);
                break;
            case 2:
                graduateStudent(scanner);
                break;
            case 3:
                displayEnrolledStudents();
                break;
            case 4:
                displayGraduates();
                break;
            case 5:
                checkStudentBelonging(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void generalOperations(Scanner scanner) {
        System.out.println("\nGeneral Operations Menu:");
        System.out.println("1. Create a new faculty.");
        System.out.println("2. Search what faculty a student belongs to by a unique identifier.");
        System.out.println("3. Display University faculties.");
        System.out.println("4. Display all faculties belonging to a field.");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                createNewFaculty(scanner);
                break;
            case 2:
                searchStudentFaculty(scanner);
                break;
            case 3:
                displayUniversityFaculties();
                break;
            case 4:
                displayFacultiesByField(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void createAndAssignStudent(Scanner scanner) {
        System.out.print("Enter student's first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter student's last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter student's email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter student's enrollment date (yyyy-MM-dd): ");
        String enrollmentDateStr = scanner.nextLine();
        LocalDate enrollmentDate = LocalDate.parse(enrollmentDateStr);

        System.out.print("Enter student's date of birth (yyyy-MM-dd): ");
        String dateOfBirthStr = scanner.nextLine();
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);

        // Display available faculties for assignment
        displayUniversityFaculties();
        System.out.print("Enter the abbreviation of the faculty to assign the student: ");
        String facultyAbbreviation = scanner.nextLine();

        Faculty targetFaculty = findFacultyByAbbreviation(facultyAbbreviation);
        if (targetFaculty != null) {
            // Create a new student
            Student newStudent = new Student(firstName, lastName, email, enrollmentDate, dateOfBirth);

            // Assign the student to the faculty
            targetFaculty.enrollStudent(newStudent);

            System.out.println("Student assigned successfully.");
        } else {
            System.out.println("Faculty not found. Student assignment failed.");
        }
    }

    private void graduateStudent(Scanner scanner) {
        System.out.print("Enter student's email to graduate: ");
        String emailToGraduate = scanner.nextLine();

        for (Faculty faculty : faculties) {
            for (Student student : faculty.getStudents()) {
                if (student.getEmail().equals(emailToGraduate)) {
                    // Graduate the student
                    faculty.graduateStudent(student);
                    System.out.println("Student graduated successfully.");
                    return; // Exit method after successful graduation
                }
            }
        }

        System.out.println("Student not found. Graduation failed.");
    }

    private void displayEnrolledStudents() {
        System.out.println("\nEnrolled Students:");

        for (Faculty faculty : faculties) {
            System.out.println("Faculty: " + faculty.getName());
            List<Student> enrolledStudents = faculty.getStudents();

            if (!enrolledStudents.isEmpty()) {
                for (Student student : enrolledStudents) {
                    System.out.println("- " + student.getFirstName() + " " + student.getLastName());
                }
            } else {
                System.out.println("No enrolled students in this faculty.");
            }

            System.out.println();
        }
    }

    private void displayGraduates() {
        System.out.println("\nGraduated Students:");

        for (Faculty faculty : faculties) {
            System.out.println("Faculty: " + faculty.getName());
            List<Student> graduatedStudents = faculty.getGraduates();

            if (!graduatedStudents.isEmpty()) {
                for (Student student : graduatedStudents) {
                    System.out.println("- " + student.getFirstName() + " " + student.getLastName());
                }
            } else {
                System.out.println("No graduates in this faculty.");
            }

            System.out.println();
        }
    }

    private void checkStudentBelonging(Scanner scanner) {
        System.out.print("Enter student's email to check belonging: ");
        String emailToCheck = scanner.nextLine();

        for (Faculty faculty : faculties) {
            for (Student student : faculty.getStudents()) {
                if (student.getEmail().equals(emailToCheck)) {
                    System.out.println("Student belongs to the faculty: " + faculty.getName());
                    return; // Exit method after finding the faculty
                }
            }
        }

        System.out.println("Student not found in any faculty.");
    }

    private void createNewFaculty(Scanner scanner) {
        System.out.print("Enter faculty name: ");
        String facultyName = scanner.nextLine();

        System.out.print("Enter faculty abbreviation: ");
        String facultyAbbreviation = scanner.nextLine();

        // Display available study fields for assignment
        displayStudyFields();
        System.out.print("Enter the name of the study field for the faculty: ");
        String studyFieldName = scanner.nextLine();

        StudyField studyField = StudyField.valueOf(studyFieldName.toUpperCase());

        // Create a new faculty
        Faculty newFaculty = new Faculty(facultyName, facultyAbbreviation, studyField);

        // Add the new faculty to the list
        faculties.add(newFaculty);

        System.out.println("Faculty created successfully.");
    }

    private void searchStudentFaculty(Scanner scanner) {
        System.out.print("Enter student's email to search for the faculty: ");
        String emailToSearch = scanner.nextLine();

        for (Faculty faculty : faculties) {
            for (Student student : faculty.getStudents()) {
                if (student.getEmail().equals(emailToSearch)) {
                    System.out.println("Student belongs to the faculty: " + faculty.getName());
                    return; // Exit method after finding the faculty
                }
            }
        }

        System.out.println("Student not found in any faculty.");
    }

    private void displayUniversityFaculties() {
        System.out.println("\nUniversity Faculties:");

        for (Faculty faculty : faculties) {
            System.out.println("- " + faculty.getName() + " (Abbreviation: " + faculty.getAbbreviation() + ")");
        }
    }

    private void displayFacultiesByField(Scanner scanner) {
        displayStudyFields();
        System.out.print("Enter the name of the study field to display faculties: ");
        String studyFieldName = scanner.nextLine();

        StudyField studyField = StudyField.valueOf(studyFieldName.toUpperCase());

        System.out.println("\nFaculties in " + studyField + " Field:");

        for (Faculty faculty : faculties) {
            if (faculty.getStudyField() == studyField) {
                System.out.println("- " + faculty.getName() + " (Abbreviation: " + faculty.getAbbreviation() + ")");
            }
        }
    }

    private Faculty findFacultyByAbbreviation(String facultyAbbreviation) {
        for (Faculty faculty : faculties) {
            if (faculty.getAbbreviation().equals(facultyAbbreviation)) {
                return faculty;
            }
        }
        return null;
    }

    private void displayStudyFields() {
        System.out.println("Available Study Fields:");

        for (StudyField studyField : StudyField.values()) {
            System.out.println("- " + studyField.name());
        }
    }

    private void saveState() {
        saveManager.saveState(faculties);
    }

    private void loadState() {
        faculties = saveManager.loadState();
    }

    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }
}
