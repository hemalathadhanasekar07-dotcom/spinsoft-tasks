package org.example.demo2.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
@Table(name="Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    public Student() {}
    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

}