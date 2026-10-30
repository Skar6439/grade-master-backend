package rw.auca.grademaster.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.grademaster.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, UUID>{
    Optional<Teacher> findByEmail(String email);

    // C1: Join through the many-to-many subjects list, match case-insensitively - even abraviation
    @Query("SELECT DISTINCT t FROM Teacher t JOIN t.subjects s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Teacher> findTeachersBySubjectName(@Param("name") String name);

    //D4: to check if room is taken or not
    boolean existsByClassroom(rw.auca.grademaster.domain.Classroom classroom);


}
