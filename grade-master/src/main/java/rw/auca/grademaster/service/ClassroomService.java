package rw.auca.grademaster.service;

import java.util.*;
import rw.auca.grademaster.domain.Classroom;
import rw.auca.grademaster.repository.ClassroomRepository;

import org.springframework.stereotype.Service;

@Service 
public class ClassroomService {
    
    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }
    
    public String saveClassroom(Classroom classroom){
        // Check if roomNumber is already taken
        if (classroomRepository.findByRoomNumber(classroom.getRoomNumber()).isPresent()) {
            return "That classroom room number already exists";
        }
        classroomRepository.save(classroom);
        return "Classroom saved successfully";
    }

    public List<Classroom> getAllClassrooms(){
        return classroomRepository.findAll();
    }

    public Optional<Classroom> getClassroomById(UUID id){
        return classroomRepository.findById(id);
    }

    public Optional<Classroom>updateClassroom(UUID id, Classroom body){
        Optional<Classroom>existing = classroomRepository.findById(id);
        if (existing.isEmpty()) return Optional.empty();
        Classroom c = existing.get();
        c.setRoomNumber(body.getRoomNumber());
        c.setName(body.getName());
        return Optional.of(classroomRepository.save(c));
    }

    public String deleteClassroom(UUID id){
        return "Classroom deleted successfully";
    }

}
