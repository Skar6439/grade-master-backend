package rw.auca.grademaster.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import rw.auca.grademaster.domain.AssessmentStatus;
import rw.auca.grademaster.repository.AssessmentRepository;
import rw.auca.grademaster.repository.ClassroomRepository;
import rw.auca.grademaster.repository.GradeReportRepository;
import rw.auca.grademaster.repository.StudentRepository;
import rw.auca.grademaster.repository.TeacherRepository;

@Service 
public class DashboardService {
    
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final AssessmentRepository assessmentRepository;
    private final GradeReportRepository gradeReportRepository;

    public DashboardService(StudentRepository studentRepository, TeacherRepository teacherRepository,
            ClassroomRepository classroomRepository, AssessmentRepository assessmentRepository,
            GradeReportRepository gradeReportRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
        this.assessmentRepository = assessmentRepository;
        this.gradeReportRepository = gradeReportRepository;
    }

    public Map<String, Long> getSummary(){
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("students", studentRepository.count());
        summary.put("teachers", teacherRepository.count());

        //Reusing C2's free classroom logic!
        summary.put("freeClassrooms", (long) classroomRepository.findFreeClassrooms().size());

        //Need a derived count method in AssessmentRepo for today's assessments
        summary.put("assessmentsToday", assessmentRepository.countByAssessmentDate(LocalDate.now()));

        //Derived count method for planned
        summary.put("planned", assessmentRepository.countByStatus(AssessmentStatus.PLANNED));

        summary.put("gradeReports", gradeReportRepository.count());
        return summary;

    }

    
}
