package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.TeacherDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Teacher {

    public Teacher(TeacherDto teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "teacher")
    private List<Book> books = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "student_teacher", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    List<Student> students;

}
