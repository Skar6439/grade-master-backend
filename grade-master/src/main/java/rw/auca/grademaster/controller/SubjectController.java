package rw.auca.grademaster.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.grademaster.domain.Subject;
import rw.auca.grademaster.service.SubjectService;

@RestController 
@RequestMapping ("/api/subjects")
public class SubjectController {
    
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Subject subject){
        String message = subjectService.saveSubject(subject);
        if (message.equals("Subject saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("A subject with that name already exists")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subject>> getAll(){
        return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        Optional<Subject> opt = subjectService.getSubjectById(id);
        if (opt.isPresent()) return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        return new ResponseEntity<>("The subject with that id does not exist", HttpStatus.NOT_FOUND);
    }

    //C3
    @GetMapping("/unused")
    public ResponseEntity<List<Subject>> getUnusedSubjects() {
        return new ResponseEntity<>(subjectService.getUnusedSubjects(), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Subject body){
         Optional<Subject> updated = subjectService.updateSubject(id, body);
        if (updated.isPresent()) return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        return new ResponseEntity<>("The subject with that id does not exist", HttpStatus.NOT_FOUND);
    }
     @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = subjectService.deleteSubject(id);
        if (message.equals("The subject with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
