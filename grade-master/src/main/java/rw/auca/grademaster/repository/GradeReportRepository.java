package rw.auca.grademaster.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.grademaster.domain.Assessment;
import rw.auca.grademaster.domain.GradeReport;

public interface GradeReportRepository extends JpaRepository<GradeReport, UUID>{
    
    //Needed for E3 (Assessment delete protection)
    boolean existsByAssessment(Assessment assessment);

    //E2: JPQL path and orderBy
    @Query("SELECT g FROM GradeReport g WHERE g.assessment.student.regNumber = :regNumber ORDER BY g.reportDate DESC")
    List<GradeReport> findByStudentRegNumberOrderByReportDateDesc(@Param("regNumber") String regNumber);
}
