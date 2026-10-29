package rw.auca.grademaster.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.grademaster.domain.Teacher;
import rw.auca.grademaster.service.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Teacher teacher) {
        String message = teacherService.saveTeacher(teacher);
        if (message.equals("Teacher saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("A teacher with that email already exists")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        if (message.startsWith("The ") || message.startsWith("That ")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Teacher>> getAll() {
        return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
    }

    //C1
    @GetMapping("/by-subject")
    public ResponseEntity<List<Teacher>> getBySubject(@RequestParam String name) {
        return new ResponseEntity<>(teacherService.getTeachersBySubject(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Teacher> opt = teacherService.getTeacherById(id);
        if (opt.isPresent()) return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        return new ResponseEntity<>("The teacher with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Teacher body) {
        Optional<Teacher> updated = teacherService.updateTeacher(id, body);
        if (updated.isPresent()) return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        return new ResponseEntity<>("The teacher with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = teacherService.deleteTeacher(id);
        if (message.equals("The teacher with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}