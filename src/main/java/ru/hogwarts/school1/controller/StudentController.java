package ru.hogwarts.school1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school1.model.Student;
import ru.hogwarts.school1.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudentByAge(@RequestParam(required = false) Integer age) {
        if (age != null) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping("range")
    public ResponseEntity<Collection<Student>> getStudentByRange(@RequestParam Integer min,
                                                                 @RequestParam Integer max) {
        if (min > 0 && max > min || max.equals(min)) {
            return ResponseEntity.ok(studentService.findStudentsInARangeOfAges(min, max));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("faculty")
    public ResponseEntity<Collection<Student>> findStudentsByFaculty(@RequestParam Long id) {
        if (id != null){
            return ResponseEntity.ok(studentService.findStudentsByFaculty(id));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
