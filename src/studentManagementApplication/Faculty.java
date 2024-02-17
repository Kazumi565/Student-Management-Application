package studentManagementApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Faculty implements Serializable {
	private static final long serialVersionUID = 1L;
    private String name;
    private String abbreviation;
    private List<Student> students;
    private StudyField studyField;
    private List<Student> graduates;

    public Faculty(String name, String abbreviation, StudyField studyField) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.studyField = studyField;
        this.students = new ArrayList<>();
        this.graduates = new ArrayList<>();
    }

    // Methods to manage students in the faculty
    public void enrollStudent(Student student) {
        students.add(student);
    }

    public void graduateStudent(Student student) {
        students.remove(student);
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public StudyField getStudyField() {
        return studyField;
    }

    public void setStudyField(StudyField studyField) {
        this.studyField = studyField;
    }
    public List<Student> getGraduates() {
        return graduates;
    }
}
