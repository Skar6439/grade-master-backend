package rw.auca.grademaster.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.grademaster.domain.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {

    Optional<Classroom> findByRoomNumber(int roomNumber); //needed for D4
}
