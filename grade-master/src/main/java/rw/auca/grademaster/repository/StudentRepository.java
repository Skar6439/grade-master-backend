package rw.auca.grademaster.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.grademaster.domain.Student;

public interface StudentRepository extends JpaRepository<Student, UUID>{

    Optional<Student> findByRegNumber(String regNumber); //needed later (E2)

    // B1: IgnoreCase for lastName, OrderBy for firstName A-Z
    List<Student> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
