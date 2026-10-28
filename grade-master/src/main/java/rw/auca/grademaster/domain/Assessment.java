package rw.auca.grademaster.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

@Entity 
@Table(name = "assessment")
public class Assessment {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="assessment_date")
    private LocalDate assessmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private AssessmentStatus status;

    @Column(name="title")
    private String title;

    @ManyToOne 
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne 
    @JoinColumn(name = "student_id")
    private Student student;

    public UUID getId() {
        return id;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public AssessmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssessmentStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }



    
}
