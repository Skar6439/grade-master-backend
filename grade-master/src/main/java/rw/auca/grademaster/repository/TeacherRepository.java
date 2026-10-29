package rw.auca.grademaster.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.grademaster.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, UUID>{
    Optional<Teacher> findByEmail(String email);
}
