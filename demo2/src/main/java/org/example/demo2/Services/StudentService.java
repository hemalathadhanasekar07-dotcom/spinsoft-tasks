package org.example.demo2.Services;

import lombok.extern.slf4j.Slf4j;
import org.example.demo2.entities.Student;
import org.example.demo2.exceptions.UnauthorizedActionException;
import org.example.demo2.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // CREATE
    public Student createStudent(Student student){

        log.info("Creating new student");

        Student savedStudent = studentRepository.save(student);

        savedStudent.setCreatedBy(savedStudent.getId());
        savedStudent.setModifiedBy(savedStudent.getId());

        log.debug("Student created with ID: {}", savedStudent.getId());

        return studentRepository.save(savedStudent);
    }

    // UPDATE
    public Student updateStudent(Long id, Student student) throws Exception {

        log.info("Updating student with ID: {}", id);

        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with ID: {}", id);
                    return new RuntimeException("Student not found");
                });

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        log.debug("Logged in username: {}", username);

        Long loggedInUserId =
                studentRepository.findIdByUsername(username).orElse(null);

        if(loggedInUserId != null) {

            existing.setModifiedBy(loggedInUserId);

            if(!loggedInUserId.equals(existing.getId())){
                log.warn("Unauthorized update attempt by user ID: {}", loggedInUserId);
                try {
                    throw new UnauthorizedActionException(
                            "Only Logged-In User can update their own profile details."
                    );
                } catch (UnauthorizedActionException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        log.debug("username: {}, loggedInUserId: {}", username, loggedInUserId);

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());

        log.info("Student updated successfully with ID: {}", id);

        return studentRepository.save(existing);
    }
}
