package rw.auca.grademaster.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.grademaster.domain.Assessment;
import rw.auca.grademaster.domain.AssessmentStatus;
import rw.auca.grademaster.domain.GradeReport;
import rw.auca.grademaster.repository.AssessmentRepository;
import rw.auca.grademaster.repository.GradeReportRepository;

@Service 
public class GradeReportService {
    
    private final GradeReportRepository gradeReportRepository;
    private final AssessmentRepository assessmentRepository;

    public GradeReportService(GradeReportRepository gradeReportRepository, AssessmentRepository assessmentRepository) {
        this.gradeReportRepository = gradeReportRepository;
        this.assessmentRepository = assessmentRepository;
    }

    public List<GradeReport> getAllGradeReports() {
        return gradeReportRepository.findAll();
    }
    
    //E1
    public String saveGradeReport(GradeReport report, UUID assessmentId){
        Optional<Assessment> assessmentOpt = assessmentRepository.findById(assessmentId);

        if (assessmentOpt.isEmpty()){
            return "The assessment with that id does not exist";
        }

        Assessment assessment = assessmentOpt.get();
        if(assessment.getStatus() != AssessmentStatus.MARKED){
            return "Grade reports are only for MARKED assessments";
        }
        report.setAssessment(assessment);
        report.setReportDate(LocalDate.now());
        gradeReportRepository.save(report);
        return "Grade report saved successfully";
    }

    //E2
    public  List<GradeReport> getGradeReportsByStudent(String regNumber){
        return gradeReportRepository.findByStudentRegNumberOrderByReportDateDesc(regNumber);
    }

    //E3
    public String deleteGradeReport(UUID id){
        if (!gradeReportRepository.existsById(id)){
            return "The grade report wiht that id does not exist";
        }
        gradeReportRepository.deleteById(id);
        return "Grade report deleted successfully";
    }
}
