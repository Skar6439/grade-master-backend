package rw.auca.grademaster.repository;

import java.time.LocalDate;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
