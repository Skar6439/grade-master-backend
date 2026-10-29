package rw.auca.grademaster.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.grademaster.domain.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {

    Optional<Classroom> findByRoomNumber(int roomNumber); //needed for D4

    // C2: Free classrooms using NOT EXISTS subquery
    @Query("SELECT c FROM Classroom c WHERE NOT EXISTS (SELECT t FROM Teacher t WHERE t.classroom = c) ORDER BY c.roomNumber ASC")
    List<Classroom> findFreeClassrooms();





}
