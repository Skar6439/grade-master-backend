package rw.auca.grademaster.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity 
@Table (name = "subject")
public class Subject {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @JsonIgnore // since teacher is the owning side
    @ManyToMany(mappedBy = "subjects") 
    private List<Teacher> teachers=new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
    
    
}
