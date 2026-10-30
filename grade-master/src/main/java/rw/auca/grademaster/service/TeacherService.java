package rw.auca.grademaster.service;

import java.util.*;
import org.springframework.stereotype.Service;

import rw.auca.grademaster.domain.Classroom;
import rw.auca.grademaster.domain.Subject;
import rw.auca.grademaster.domain.Teacher;
import rw.auca.grademaster.repository.ClassroomRepository;
import rw.auca.grademaster.repository.SubjectRepository;
import rw.auca.grademaster.repository.TeacherRepository;

@Service 
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final SubjectRepository subjectRepository;

    public TeacherService(TeacherRepository teacherRepository, ClassroomRepository classroomRepository,
            SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.classroomRepository = classroomRepository;
        this.subjectRepository = subjectRepository;
    }


    public String saveTeacher(Teacher teacher){
        if (teacherRepository.findByEmail(teacher.getEmail()).isPresent()) {
            return "A teacher with that email already exists";
        }
        String error = resolveRelations(teacher);
        if (error!= null) return error;
        teacherRepository.save(teacher);
        return "Teacher saved successfully";
    }

    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    public Optional<Teacher> getTeacherById(UUID id){
        return teacherRepository.findById(id);
    }

    public Optional<Teacher> updateTeacher(UUID id, Teacher body){
        Optional<Teacher> existing = teacherRepository.findById(id);
        if (existing.isEmpty()) return Optional.empty();

        Teacher t = existing.get();
        t.setFirstName(body.getFirstName());
        t.setLastName(body.getLastName());
        t.setEmail(body.getEmail());

        if(body.getClassroom() != null && body.getClassroom().getId() != null){
            classroomRepository.findById(body.getClassroom().getId()).ifPresent(t::setClassroom);
        }else{
            t.setClassroom(null);
        }
        if (body.getSubjects() !=null){
            List<Subject> subs = new ArrayList<>();
            for (Subject s : body.getSubjects()){
                subjectRepository.findById(s.getId()).ifPresent(subs::add);
            }
            t.setSubjects(subs);
        }
        return Optional.of(teacherRepository.save(t));
    }

    public String deleteTeacher(UUID id){
        if(!teacherRepository.existsById(id)){
            return "The teacher with that id does not exist";
        }
        teacherRepository.deleteById(id);
        return "Teacher deleted successfully";
    }

    private String resolveRelations(Teacher teacher){
        if (teacher.getClassroom() != null && teacher.getClassroom().getId() != null){
            Optional<Classroom> c = classroomRepository.findById(teacher.getClassroom().getId());
            if (c.isEmpty()) return "The classroom with that id does not exist";
            teacher.setClassroom(null);
        }

        List<Subject> real = new ArrayList<>();
        if (teacher.getSubjects() != null){
            for (Subject s : teacher.getSubjects()) {
                Optional<Subject> subj = subjectRepository.findById(s.getId());
                if (subj.isEmpty()) return "That subject does not exist";
                real.add(subj.get());
            }
        }
        teacher.setSubjects(real);
        return null;
    }

    //C1
    public List<Teacher> getTeachersBySubject(String name) {
        return teacherRepository.findTeachersBySubjectName(name);
    }

    //D3
    public String addSubjectToTeacher(UUID teacherId, UUID subjectId) {
        Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
        if (teacherOpt.isEmpty()) return "The teacher with that id does not exist";
        
        Optional<Subject> subjectOpt = subjectRepository.findById(subjectId);
        if (subjectOpt.isEmpty()) return "That subject does not exist";
        
        Teacher t = teacherOpt.get();
        Subject s = subjectOpt.get();
        
        if (t.getSubjects().contains(s)) {
            return "The teacher already has that subject";
        }
        
        t.getSubjects().add(s);
        teacherRepository.save(t);
        return "Subject added to the teacher";
    }

    //D4: in order to update data without conflit
    public String changeClassroom(UUID teacherId, int roomNumber){
        Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
        if (teacherOpt.isEmpty()) return "The teacher with that id does not exist";

        Optional<Classroom> classroomOpt = classroomRepository.findByRoomNumber(roomNumber);
        if (classroomOpt.isEmpty()) return "The classroom with that roomNumber does not exist";

        Teacher t = teacherOpt.get();
        Classroom c = classroomOpt.get();

        if (teacherRepository.existsByClassroom(c)){
            return "That classroom is taken by another teacher";
        }
        t.setClassroom(c);
        teacherRepository.save(t);
        return "Classroom changed successfully";
    }
}
