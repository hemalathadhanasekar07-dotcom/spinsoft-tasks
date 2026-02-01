package org.example.demo2.controllers;
import org.springframework.http.HttpStatus;
import org.example.demo2.entities.Student;
import org.example.demo2.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.demo2.repositories.StudentRepository;
import java.util.List;

@RestController
@RequestMapping("/students")
public class Controller {
    private final StudentRepository studentRepository;
    public Controller(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/add_")
    public int add(
            @RequestParam int a,
            @RequestParam int b) {
        return a + b;
    }

    @GetMapping("/mul_")
    public int mul(
            @RequestParam int a,
            @RequestParam int b){
        return a * b ;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    @PostMapping("/createstudent")
    public Student createuser(@RequestBody Student user){
         return studentRepository.save(user);

    }
    @GetMapping("/{id}")
    public Student getUserById(@PathVariable Long id){
        return studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with this id:"+ id));
    }
    @PutMapping("/{id}")
    public Student UpdateUser(@PathVariable long id,@RequestBody Student user){
        Student userdata= studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with this id:"+ id));
        userdata.setEmail(user.getEmail());
        userdata.setName(user.getName());
        return studentRepository.save(userdata);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteuser(@PathVariable long id){
        Student userdata=studentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with this id:"+ id));
        studentRepository.delete(userdata);
        return ResponseEntity.ok().build();
    }

}
