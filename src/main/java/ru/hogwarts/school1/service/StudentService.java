package ru.hogwarts.school1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school1.model.Faculty;
import ru.hogwarts.school1.model.Student;
import ru.hogwarts.school1.repositories.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public Student addStudent(Student student) {
        logger.info("Was invoked method for create the next student: {}", student);
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method to search for a student with the following id: {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.info("Was invoked method for edit and save the next student: {}", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method to deleted for a student with the following id: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudent() {
        logger.info("Was invoked method to get all students");
        return studentRepository.findAll();
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Was invoked method to get all students by age: {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findStudentsInARangeOfAges(int min, int max) {
        logger.info("Was invoked method to get all students aged from {} to {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty findFacultyByStudentId(Long id) {
        logger.info("Was invoked method to get faculty with the following id student: {}", id);
        Optional<Student> student = studentRepository.findById(id);
        return student.map(Student::getFaculty).orElse(null);
    }

    public Long getCountAllStudent() {
        logger.info("Was invoked method to get count all students");
        return studentRepository.count();
    }

    public Float getAverageAgeAllStudent() {
        logger.info("Was invoked method to get average age all students");
        return studentRepository.getAverageAge();
    }

    public List<Student> findFiveLastStudent() {
        logger.info("Was invoked method to find last five student");
        return studentRepository.findFiveLastStudent();
    }

    public List<Student> getStudentsNameStartingWithA() {
        return getAllStudent()
                .stream()
                .filter(e -> e.getName().startsWith("А"))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    public Double getAvgAgeAllStudentsStream() {
        return getAllStudent()
                .stream()
                .mapToInt(Student::getAge).average().orElse(0.0);
    }
}

