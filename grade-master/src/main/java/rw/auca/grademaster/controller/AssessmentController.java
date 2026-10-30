package rw.auca.grademaster.controller;

import java.time.LocalDate;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.grademaster.domain.Assessment;
import rw.auca.grademaster.domain.AssessmentStatus;
import rw.auca.grademaster.service.AssessmentService;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Assessment assessment) {
        String message = assessmentService.saveAssessment(assessment);
        if (message.equals("Assessment saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("Teacher already has an assessment on that data")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        if (message.startsWith("The ") || message.startsWith("That ")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Assessment>> getAll() {
        return new ResponseEntity<>(assessmentService.getAllAssessments(), HttpStatus.OK);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Assessment>> getByStatus(@RequestParam AssessmentStatus status) {
        return new ResponseEntity<>(assessmentService.getAssessmentsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Assessment>> getBetween(@RequestParam String start, @RequestParam String end) {
        // The paper explicitly says: "Parse the Strings into LocalDate in the controller."
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(assessmentService.getAssessmentsBetween(startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Assessment> opt = assessmentService.getAssessmentById(id);
        if (opt.isPresent()) return new ResponseEntity<>(opt.get(), HttpStatus.OK);
        return new ResponseEntity<>("The assessment with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Assessment body) {
        Optional<Assessment> updated = assessmentService.updateAssessment(id, body);
        if (updated.isPresent()) return new ResponseEntity<>(updated.get(), HttpStatus.OK);
        return new ResponseEntity<>("The assessment with that id does not exist", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = assessmentService.deleteAssessment(id);
        if (message.equals("The assessment with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(@PathVariable UUID id, @RequestParam AssessmentStatus status){
        String message = assessmentService.changeAssessmentStatus(id, status);
        if (message.equals("The assessment with that id does not exist")){
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.startsWith("Cannot change status")){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}