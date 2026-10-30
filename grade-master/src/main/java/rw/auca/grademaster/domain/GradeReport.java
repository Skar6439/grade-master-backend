package rw.auca.grademaster.domain;

import java.time.LocalDate;
import java.util.*;
import jakarta.persistence.*;

@Entity 
@Table (name = "grade_repoort")
public class GradeReport {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "letter_grade")
    private String letterGrade;

    @Column(name = "comment")
    private String comment;
    
    @Column(name = "score")
    private int score;
    
    @Column(name = "report_date")
    private LocalDate reportDate;

    @ManyToOne 
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    public UUID getId() {
        return id;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
    
    
}
