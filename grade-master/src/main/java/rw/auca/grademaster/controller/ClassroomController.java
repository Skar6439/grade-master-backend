package rw.auca.grademaster.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.grademaster.domain.Classroom;
import rw.auca.grademaster.service.ClassroomService;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Classroom classroom) {
        String message = classroomService.saveClassroom(classroom);
        if (message.equals("Classroom saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("That classroom room number already exists")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Classroom>> getAll() {
        return new ResponseEntity<>(classroomService.getAllClassrooms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Classroom> opt = classroomService.getClassroomById(id);
        if (opt.isPresent()) return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        return new ResponseEntity<>("The classroom with that id does not exist", HttpStatus.NOT_FOUND);
    }

    //C2
    @GetMapping("/free")
    public ResponseEntity<List<Classroom>> getFreeClassrooms() {
        return new ResponseEntity<>(classroomService.getFreeClassrooms(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Classroom body) {
        Optional<Classroom> updated = classroomService.updateClassroom(id, body);
        if (updated.isPresent()) return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        return new ResponseEntity<>("The classroom with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = classroomService.deleteClassroom(id);
        if (message.equals("The classroom with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}