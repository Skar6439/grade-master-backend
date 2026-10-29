package rw.auca.grademaster.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.grademaster.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, UUID>{
    Optional<Subject> findByName(String name);
    
}
