package rw.auca.grademaster.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.grademaster.domain.Student;

public interface StudentRepository extends JpaRepository<Student, UUID>{

    Optional<Student> findByRegNumber(String regNumber); //needed later (E2)

    // B1: IgnoreCase for lastName, OrderBy for firstName A-Z
    List<Student> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // C4: Students of a teacher (DISTINCT, path navigation)
    @Query("SELECT DISTINCT a.student FROM Assessment a WHERE a.teacher.id = :teacherId")
    List<Student> findStudentsOfTeacher(@Param("teacherId") UUID teacherId);
}
