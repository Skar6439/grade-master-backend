package rw.auca.grademaster.service;

import java.util.*;

import org.springframework.stereotype.Service;

import rw.auca.grademaster.domain.Subject;
import rw.auca.grademaster.repository.SubjectRepository;

@Service 
public class SubjectService {
    
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository=subjectRepository;
    }

    public String saveSubject(Subject subject){
        subjectRepository.save(subject);
        return "Subject saved successfully";
    }

    public List<Subject> getAllSubjects(){
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(UUID id){
        return subjectRepository.findById(id);
    }
    
    public Optional<Subject> updateSubject(UUID id, Subject body){
        Optional<Subject> existing= subjectRepository.findById(id);
        if (existing.isEmpty())
            return Optional.empty();
        Subject s = existing.get();
        s.setName(body.getName());
        return Optional.of(subjectRepository.save(s));
    }

    public String deleteSubject(UUID id){
        if(!subjectRepository.existsById(id)){
            return "The subject with that id does not exist";
        }
        subjectRepository.deleteById(id);
        return "Subject deleted successfully";
    }
    
    //C3
    public List<Subject> getUnusedSubjects() {
        return subjectRepository.findUnusedSubjects();
    }
}
