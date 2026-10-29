package rw.auca.grademaster.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.grademaster.domain.Student;
import rw.auca.grademaster.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Student student) {
        String message = studentService.saveStudent(student);
        if (message.equals("Student saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("A student with that regNumber already exists")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAll() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }
    
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Student>> getByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(studentService.getStudentsByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Student> opt = studentService.getStudentById(id);
        if (opt.isPresent()) return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Student body) {
        Optional<Student> updated = studentService.updateStudent(id, body);
        if (updated.isPresent()) return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        return new ResponseEntity<>("The student with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = studentService.deleteStudent(id);
        if (message.equals("The student with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


}