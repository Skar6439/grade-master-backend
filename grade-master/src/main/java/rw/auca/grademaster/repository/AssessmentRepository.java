package rw.auca.grademaster.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.grademaster.domain.Assessment;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID>{
    
}
