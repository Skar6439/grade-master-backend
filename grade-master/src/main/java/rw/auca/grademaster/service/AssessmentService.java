package rw.auca.grademaster.service;

import java.time.LocalDate;
import java.util.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.auca.grademaster.domain.Assessment;
import rw.auca.grademaster.domain.AssessmentStatus;
import rw.auca.grademaster.domain.Student;
import rw.auca.grademaster.domain.Teacher;
import rw.auca.grademaster.repository.AssessmentRepository;
import rw.auca.grademaster.repository.GradeReportRepository;
import rw.auca.grademaster.repository.StudentRepository;
import rw.auca.grademaster.repository.TeacherRepository;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GradeReportRepository gradeReportRepository;

    public AssessmentService(AssessmentRepository assessmentRepository, StudentRepository studentRepository,
            TeacherRepository teacherRepository, GradeReportRepository gradeReportRepository) {
        this.assessmentRepository = assessmentRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.gradeReportRepository = gradeReportRepository;
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

        // D1 Rule 1: Past date
        if (assessment.getAssessmentDate().isBefore(java.time.LocalDate.now())) {
            return "Assessment date cannot be in the past";
        }

        // D1 Rule 2: Double booking check (ignore VOIDED)
        boolean busy = assessmentRepository.existsByTeacherAndAssessmentDateAndStatusNot(
                assessment.getTeacher(), assessment.getAssessmentDate(), rw.auca.grademaster.domain.AssessmentStatus.VOIDED);
        if (busy) {
            return "Teacher already has an assessment on that date";
        }

        // D1 Rule 3: Always start as PLANNED
        assessment.setStatus(rw.auca.grademaster.domain.AssessmentStatus.PLANNED);
        
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
        Optional<Assessment> assessmentOpt = assessmentRepository.findById(id);
        if (assessmentOpt.isEmpty()) {
            return "The assessment with that id does not exist";
        }
        Assessment assessment = assessmentOpt.get();
        if(gradeReportRepository.existsByAssessment(assessment)){
            return "That assessment has grade reports, delete them first";
        }
        assessmentRepository.delete(assessment);
        return "Assessment deleted successfully";
    }

    public List<Assessment> getAssessmentsByStatus(AssessmentStatus status) {
        return assessmentRepository.findByStatusOrderByAssessmentDateAsc(status);
    }

    public List<Assessment> getAssessmentsBetween(LocalDate start, LocalDate end) {
        return assessmentRepository.findByAssessmentDateBetweenOrderByAssessmentDateAsc(start, end);
    }

    public String changeAssessmentStatus(UUID id, rw.auca.grademaster.domain.AssessmentStatus newStatus) {
        Optional<Assessment> existing = assessmentRepository.findById(id);
        if (existing.isEmpty()) return "The assessment with that id does not exist";
        
        Assessment a = existing.get();
        rw.auca.grademaster.domain.AssessmentStatus current = a.getStatus();
        
        boolean allowed = false;
        if (current == rw.auca.grademaster.domain.AssessmentStatus.PLANNED) {
            allowed = (newStatus == rw.auca.grademaster.domain.AssessmentStatus.SUBMITTED || newStatus == rw.auca.grademaster.domain.AssessmentStatus.VOIDED);
        } else if (current == rw.auca.grademaster.domain.AssessmentStatus.SUBMITTED) {
            allowed = (newStatus == rw.auca.grademaster.domain.AssessmentStatus.MARKED || newStatus == rw.auca.grademaster.domain.AssessmentStatus.VOIDED);
        }
        
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        
        a.setStatus(newStatus);
        assessmentRepository.save(a);
        return "Assessment status updated successfully";
    }

    //F1
    public List<Object[]> getAssessmentCountByStatus(){
        return assessmentRepository.countAssessmentsByStatus();
    }

    //F2
    @Transactional 
    public String cancelDayForTeacher(UUID teacherId, LocalDate date){
        int count = assessmentRepository.cancelAssessmentsForTeacherOnDay(teacherId, date, AssessmentStatus.VOIDED, AssessmentStatus.MARKED);
        return count + " assessments cancelled";
    }

    //Bonus
    public Page<Assessment> getAssessmentsPage(Pageable pageable){
        return assessmentRepository.findAll(pageable);
    }
}