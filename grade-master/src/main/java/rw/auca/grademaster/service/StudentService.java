package rw.auca.grademaster.service;

import java.util.*;

import org.springframework.stereotype.Service;

import rw.auca.grademaster.domain.Student;
import rw.auca.grademaster.repository.StudentRepository;
import rw.auca.grademaster.repository.TeacherRepository;

@Service 
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public String saveStudent(Student student){
        if (studentRepository.findByRegNumber(student.getRegNumber()).isPresent()) {
            return "A student with that regNumber already exists";
        }
        studentRepository.save(student);
        return "Student saved successfully";
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(UUID id){
        return studentRepository.findById(id);
    }
    
    public Optional<Student> updateStudent(UUID id, Student body){
        Optional<Student> existing = studentRepository.findById(id);
        if(existing.isEmpty()) return Optional.empty();
        Student s = existing.get();
        s.setFirstName(body.getFirstName());
        s.setLastName(body.getLastName());
        s.setRegNumber(body.getRegNumber());
        s.setEmail(body.getEmail());
        s.setPhone(body.getPhone());
        return Optional.of(studentRepository.save(s));
    }

    public String deleteStudent(UUID id){
        if(!studentRepository.existsById(id)){
            return "The student with that id does not exist";
        }
        studentRepository.deleteById(id);
        return "Student deleted successfully";
    }

    public List<Student> getStudentsByLastName(String lastName) {
        return studentRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    //C4
    public Optional<List<Student>> getStudentsOfTeacher(UUID teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            return Optional.empty(); // This triggers the 404 in the controller
        }
        return Optional.of(studentRepository.findStudentsOfTeacher(teacherId));
    }
}
