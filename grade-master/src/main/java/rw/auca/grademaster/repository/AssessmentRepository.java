package rw.auca.grademaster.repository;

import java.time.LocalDate;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.grademaster.domain.Assessment;
import rw.auca.grademaster.domain.AssessmentStatus;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID>{
    
    // B2: Enum parameter, OrderBy earliest date first
    List<Assessment> findByStatusOrderByAssessmentDateAsc(AssessmentStatus status);

    // B3: Between dates (inclusive), OrderBy date
    List<Assessment> findByAssessmentDateBetweenOrderByAssessmentDateAsc(LocalDate start, LocalDate end);

    //D1: to check teacher double-booking
    boolean existsByTeacherAndAssessmentDateAndStatusNot(
        rw.auca.grademaster.domain.Teacher teacher, 
        java.time.LocalDate assessmentDate, 
        rw.auca.grademaster.domain.AssessmentStatus status
    );

    //F1: Group By status and Count
    @Query("SELECT a.status, COUNT(a) FROM Assessment a GROUP BY a.status")
    List<Object[]> countAssessmentsByStatus();

    //F2: Bulk Update query
    @Modifying
    @Query("UPDATE Assessment a SET a.status = :voidedStatus WHERE a.teacher.id = :teacherId AND a.assessmentDate = :date AND a.status <> :markedStatus")
    int cancelAssessmentsForTeacherOnDay(
        @Param("teacherId") UUID teacherId,
        @Param("date") LocalDate date,
        @Param("voidedStatus") AssessmentStatus voidedStatus,
        @Param("markedStatus") AssessmentStatus markedStatus
    );
    
    long countByAssessmentDate(LocalDate date);
    long countByStatus(AssessmentStatus status);
}
