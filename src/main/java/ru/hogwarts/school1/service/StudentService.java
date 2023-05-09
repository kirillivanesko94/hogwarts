package ru.hogwarts.school1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school1.model.Faculty;
import ru.hogwarts.school1.model.Student;
import ru.hogwarts.school1.repositories.StudentRepository;

import java.util.*;
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

    public void getNameAllStudents() {
//        List<String> listOfName = getAllStudent()
//                .stream()
//                .map(Student::getName)
//                .collect(Collectors.toList());
//        listOfName.stream()
//                .limit(2)
//                .forEach(System.out::println);
//        new Thread(() -> listOfName.stream()
//                .skip(2)
//                .limit(2)
//                .forEach(System.out::println));
//        new Thread(() -> listOfName.stream()
//                .skip(4)
//                .limit(2)
//                .forEach(System.out::println));

        List<Student> students;
        students = studentRepository.findAll();

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        new Thread(() -> {
            try {
                Thread.sleep(3_000);
                System.out.println(students.get(2).getName());
                System.out.println(students.get(3).getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3_000);
                System.out.println(students.get(4).getName());
                System.out.println(students.get(5).getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void getNameAllStudentsSync() {
        List<Student> students = studentRepository.findAll();
        printNameSync(students, 0);
        printNameSync(students, 1);
        new Thread(() -> {
            try {
                Thread.sleep(3_000);
                printNameSync(students, 2);
                printNameSync(students, 3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(3_000);
                printNameSync(students, 4);
                printNameSync(students, 5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    public void printNameSync(List<Student> students, int index) {
        synchronized (this) {
            System.out.println(students.get(index).getName());
        }
    }
}


