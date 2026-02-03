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
    private String username;
    private String password;

    public Student() {}
    public Student(String name, String email,String username,String password) {
        this.name = name;
        this.email = email;
        this.username=username;
        this.password=password;
    }

}