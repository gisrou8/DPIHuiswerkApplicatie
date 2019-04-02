package Models;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Assignment implements Serializable {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int ID;
    private String studentName;
    private String studentClass;
    private String assignmentName;
    private String assignmentSubject;
    private boolean reviewed;
    private String reviewedBy;
    private String correlationId;


    public Assignment(String studentName, String studentClass, String assignmentName, String assignmentSubject) {
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.assignmentName = assignmentName;
        this.assignmentSubject = assignmentSubject;
        reviewed = false;
        ID = count.incrementAndGet();
    }

    public Assignment(){
        reviewed = false;
    }

    public String getAssignmentSubject() {
        return assignmentSubject;
    }

    public void setAssignmentSubject(String assignmentSubject) {
        this.assignmentSubject = assignmentSubject;
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
        return assignmentName + " " + isReviewed() + " " + reviewedBy;
    }


    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
