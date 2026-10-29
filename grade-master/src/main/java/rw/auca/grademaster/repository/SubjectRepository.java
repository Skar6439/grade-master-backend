package rw.auca.grademaster.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.grademaster.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, UUID>{
    Optional<Subject> findByName(String name);
    
    // C3: Subjects nobody holds using IS EMPTY
    @Query("SELECT s FROM Subject s WHERE s.teachers IS EMPTY")
    List<Subject> findUnusedSubjects();
}
