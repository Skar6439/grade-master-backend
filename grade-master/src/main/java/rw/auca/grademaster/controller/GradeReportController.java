package rw.auca.grademaster.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rw.auca.grademaster.domain.GradeReport;
import rw.auca.grademaster.service.GradeReportService;

@RestController 
@RequestMapping("/api/grade-reports")
public class GradeReportController {
    
    private final GradeReportService gradeReportService;

    public GradeReportController(GradeReportService gradeReportService) {
        this.gradeReportService = gradeReportService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<GradeReport>> getAll() {
        return new ResponseEntity<>(gradeReportService.getAllGradeReports(), HttpStatus.OK);
    }

    //E1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody GradeReport report, @RequestParam UUID assessmentId){
        String message = gradeReportService.saveGradeReport(report, assessmentId);
        if (message.equals("Grade report saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("The assessment with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        if (message.equals("Grade reports are only for MARKED assessments")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //E2
    @GetMapping("/student/{regNumber}")
    public ResponseEntity<List<GradeReport>> getByStudent(@PathVariable String regNumber) {
        return new ResponseEntity<>(gradeReportService.getGradeReportsByStudent(regNumber), HttpStatus.OK);
    }

    //E3
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = gradeReportService.deleteGradeReport(id);
        if (message.equals("The grade report with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
