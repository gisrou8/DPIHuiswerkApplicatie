package Models;

import java.io.Serializable;

public class Assignment implements Serializable {

    private String studentName;
    private String studentClass;
    private String assignmentName;
    private boolean reviewed;


    public Assignment(String studentName, String studentClass, String assignmentName) {
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.assignmentName = assignmentName;
        reviewed = false;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }


    @Override
    public String toString(){
        return assignmentName;
    }


}
