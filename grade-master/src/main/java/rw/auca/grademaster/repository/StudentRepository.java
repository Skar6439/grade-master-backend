package rw.auca.grademaster.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.grademaster.domain.Student;

public interface StudentRepository extends JpaRepository<Student, UUID>{

    Optional<Student> findByRegNumber(String regNumber); //needed later (E2)
}
