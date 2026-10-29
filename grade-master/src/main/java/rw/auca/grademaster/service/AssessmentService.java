package rw.auca.grademaster.service;

import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;

import rw.auca.grademaster.domain.Assessment;
import rw.auca.grademaster.domain.AssessmentStatus;
import rw.auca.grademaster.domain.Student;
import rw.auca.grademaster.domain.Teacher;
import rw.auca.grademaster.repository.AssessmentRepository;
import rw.auca.grademaster.repository.StudentRepository;
import rw.auca.grademaster.repository.TeacherRepository;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public AssessmentService(AssessmentRepository assessmentRepository,
                             StudentRepository studentRepository,
                             TeacherRepository teacherRepository) {
        this.assessmentRepository = assessmentRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public String saveAssessment(Assessment assessment) {
        if (assessment.getStudent() == null || assessment.getStudent().getId() == null) {
            return "The student with that id does not exist";
        }
        Optional<Student> studentOpt = studentRepository.findById(assessment.getStudent().getId());
        if (studentOpt.isEmpty()) return "The student with that id does not exist";
        assessment.setStudent(studentOpt.get());

        if (assessment.getTeacher() == null || assessment.getTeacher().getId() == null) {
            return "The teacher with that id does not exist";
        }
        Optional<Teacher> teacherOpt = teacherRepository.findById(assessment.getTeacher().getId());
        if (teacherOpt.isEmpty()) return "The teacher with that id does not exist";
        assessment.setTeacher(teacherOpt.get());

        assessmentRepository.save(assessment);
        return "Assessment saved successfully";
    }

    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }

    public Optional<Assessment> getAssessmentById(UUID id) {
        return assessmentRepository.findById(id);
    }

    public Optional<Assessment> updateAssessment(UUID id, Assessment body) {
        Optional<Assessment> existing = assessmentRepository.findById(id);
        if (existing.isEmpty()) return Optional.empty();

        Assessment a = existing.get();
        a.setAssessmentDate(body.getAssessmentDate());
        a.setStatus(body.getStatus());      // will be dropped in D2
        a.setTitle(body.getTitle());

        if (body.getStudent() != null && body.getStudent().getId() != null) {
            studentRepository.findById(body.getStudent().getId()).ifPresent(a::setStudent);
        }
        if (body.getTeacher() != null && body.getTeacher().getId() != null) {
            teacherRepository.findById(body.getTeacher().getId()).ifPresent(a::setTeacher);
        }
        return Optional.of(assessmentRepository.save(a));
    }

    public String deleteAssessment(UUID id) {
        if (!assessmentRepository.existsById(id)) {
            return "The assessment with that id does not exist";
        }
        assessmentRepository.deleteById(id);
        return "Assessment deleted successfully";
    }

    public List<Assessment> getAssessmentsByStatus(AssessmentStatus status) {
        return assessmentRepository.findByStatusOrderByAssessmentDateAsc(status);
    }

    public List<Assessment> getAssessmentsBetween(LocalDate start, LocalDate end) {
        return assessmentRepository.findByAssessmentDateBetweenOrderByAssessmentDateAsc(start, end);
    }
}