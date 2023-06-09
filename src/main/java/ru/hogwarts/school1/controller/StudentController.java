package ru.hogwarts.school1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school1.model.Faculty;
import ru.hogwarts.school1.model.Student;
import ru.hogwarts.school1.service.StudentService;

import java.util.Collection;
import java.util.List;

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

    @GetMapping("get/faculty")
    public Faculty getFacultyByStudentId(@RequestParam Long id){
        return studentService.findFacultyByStudentId(id);
    }
    @GetMapping("count-all-student")
    public ResponseEntity<Long> getCountAllStudent() {
        return ResponseEntity.ok(studentService.getCountAllStudent());
    }
    @GetMapping("average-age")
    public ResponseEntity<Float> getAverageAgeAllStudents() {
        return ResponseEntity.ok(studentService.getAverageAgeAllStudent());
    }
    @GetMapping("five-last")
    public ResponseEntity<List<Student>> findFiveLastStudent() {
        return ResponseEntity.ok(studentService.findFiveLastStudent());
    }
    @GetMapping("starts-with-a")
    public ResponseEntity<List<Student>> getStudentsNameStartingWithA(){
        return ResponseEntity.ok(studentService.getStudentsNameStartingWithA());
    }
    @GetMapping("avg")
    public ResponseEntity<Double> getAvgAgeAllStudents(){
        return ResponseEntity.ok(studentService.getAvgAgeAllStudentsStream());
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
