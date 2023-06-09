package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.hogwarts.school1.model.Student;
import ru.hogwarts.school1.repositories.StudentRepository;
import ru.hogwarts.school1.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    StudentRepository studentRepository = mock(StudentRepository.class);

    StudentService studentService = new StudentService(studentRepository);
    Student harry = new Student(0L, "Harry", 11);
    Student hermiona = new Student(0L, "Hermiona", 12);
    Student ron = new Student(0L, "Ron", 12);

    @Test
    void checkAddStudent() {
        when(studentRepository.save(harry)).thenReturn(harry);

        Student result = studentService.addStudent(harry);

        assertEquals(result, harry);
        verify(studentRepository, times(1)).save(harry);
    }

    @Test
    void checkFindStudent() {
        when(studentRepository.findById(harry.getId())).thenReturn(Optional.of(harry));

        Student result = studentService.findStudent(harry.getId());

        assertEquals(result, harry);
        verify(studentRepository, times(1)).findById(harry.getId());
    }

    @Test
    void checkFindStudentByNull() {
        when(studentRepository.findById(harry.getId())).thenReturn(Optional.empty());

        Student result = studentService.findStudent(harry.getId());

        assertNull(result);
        verify(studentRepository, times(1)).findById(harry.getId());
    }

    @Test
    void checkEditStudent() {
        when(studentRepository.save(harry)).thenReturn(harry);

        harry.setAge(115);
        Student result = studentService.editStudent(harry);

        assertEquals(115, result.getAge());
        assertEquals(result.getId(), harry.getId());
        verify(studentRepository, times(1)).save(harry);
    }

    @Test
    void checkDeleteStudent() {
        studentService.deleteStudent(harry.getId());
        verify(studentRepository).deleteById(harry.getId());
    }

    @Test
    void checkGetAllStudent() {
        when(studentRepository.findAll()).thenReturn(List.of(harry, hermiona));

        Collection<Student> expected = new ArrayList<>();
        expected.add(harry);
        expected.add(hermiona);
        Collection<Student> actual = studentService.getAllStudent();

        assertEquals(expected, actual);
        verify(studentRepository, times(1)).findAll();
    }

    @ParameterizedTest
    @ValueSource(ints = 12)
    void checkFindByAge(int age) {
        when(studentRepository.findByAge(age)).thenReturn(List.of(hermiona, ron));

        Collection<Student> expected = new ArrayList<>();
        expected.add(hermiona);
        expected.add(ron);

        Collection<Student> actual = studentService.findByAge(age);

        assertEquals(expected, actual);

    }

    @Test
    void checkFindStudentsInARangeOfAges() {
        int min = 0;
        int max = 20;
        when(studentRepository.findByAgeBetween(min, max)).thenReturn(List.of(harry, hermiona, ron));

        Collection<Student> expected = new ArrayList<>();
        expected.add(harry);
        expected.add(hermiona);
        expected.add(ron);

        Collection<Student> actual = studentService.findStudentsInARangeOfAges(min, max);

        assertEquals(expected, actual);
    }

    @Test
    void checkFindFacultyByStudentId() {
        Optional<Student> student = studentRepository.findById(harry.getId());
        assertNotNull(student);
        verify(studentRepository, times(1)).findById(harry.getId());
    }

    @Test
    void checkGetCountAllStudent() {
        Long expected = 3L;
        when(studentRepository.count()).thenReturn(expected);

        Long actual = studentService.getCountAllStudent();

        assertEquals(expected, actual);
    }

    @Test
    void checkGetAverageAgeAllStudent() {
        Float expected = 11.33f;
        when(studentRepository.getAverageAge()).thenReturn(expected);

        Float actual = studentService.getAverageAgeAllStudent();

        assertEquals(expected, actual);
        verify(studentRepository).getAverageAge();
    }

    @Test
    void checkFindFiveLastStudent() {
        List<Student> expected = new ArrayList<>();
        expected.add(hermiona);
        expected.add(harry);
        expected.add(ron);
        when(studentRepository.findFiveLastStudent()).thenReturn(expected);

        List<Student> actual = studentService.findFiveLastStudent();

        assertEquals(expected, actual);
    }

    @Test
    void checkGetAllStudentSorted() {
        Student student = new Student(14L, "Афанасий", 20);
        when(studentRepository.findAll()).thenReturn(List.of(student));
        List<Student> expected = List.of(student);

        List<Student> actual = studentService.getStudentsNameStartingWithA();

        assertEquals(expected, actual);
    }

    @Test
    void checkAvgAgeAllStudentsStream() {
        Float age = (float)harry.getAge();
        when(studentRepository.findAll()).thenReturn(List.of(harry));
        when(studentRepository.getAverageAge()).thenReturn(age);
        Double expected = (double) age;

        Double actual = studentService.getAvgAgeAllStudentsStream();

        assertEquals(expected, actual);
    }
}
