package ru.hogwarts.school1.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school1.model.Faculty;
import ru.hogwarts.school1.model.Student;
import ru.hogwarts.school1.repositories.StudentRepository;
import ru.hogwarts.school1.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;

    Student harry = new Student(1L, "Harry", 11);


    @Test
    void testGetStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(harry));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + harry.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(harry.getId()))
                .andExpect(jsonPath("$.name").value(harry.getName()))
                .andExpect(jsonPath("$.age").value(harry.getAge()));

        verify(studentRepository,times(1)).findById(any(Long.class));
    }

    @Test
    void testGetStudentByAge() throws Exception {
        when(studentRepository.findByAge(any(Integer.class))).thenReturn(List.of(harry));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student").param("age", "11")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo(harry.getName())))
                .andExpect(jsonPath("$[0].age", equalTo(harry.getAge())));

        verify(studentRepository,times(1)).findByAge(any(Integer.class));
    }

    @Test
    void testGetStudentByRange() throws Exception {
        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(List.of(harry));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/range?min=11&max=12")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo(harry.getName())))
                .andExpect(jsonPath("$[0].age", equalTo(harry.getAge())));

        verify(studentRepository,times(1)).findByAgeBetween(any(Integer.class), any(Integer.class));
    }
    @Test
    void testGetFacultyByStudentId() throws Exception {
        Faculty faculty = new Faculty(1L, "Грифиндор", "Красный");
        harry.setFaculty(faculty);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(harry));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get/faculty?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

        verify(studentRepository,times(1)).findById(any(Long.class));

    }
    @Test
    public void testGetCountAllStudent() throws Exception {
        long expectedCount = 10L;
        when(studentRepository.count()).thenReturn(expectedCount);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/count-all-student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedCount));

        verify(studentRepository, times(1)).count();
    }
    @Test
    public void testGetAverageAgeAllStudents() throws Exception {
        Float expectedCount = 11f;
        when(studentRepository.getAverageAge()).thenReturn(expectedCount);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/average-age")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedCount));

        verify(studentRepository, times(1)).getAverageAge();
    }
    @Test
    public void testFindFiveLastStudent() throws Exception {

        when(studentRepository.findFiveLastStudent()).thenReturn(List.of(harry));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/five-last")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(harry.getId()))
                .andExpect(jsonPath("$[0].name").value(harry.getName()))
                .andExpect(jsonPath("$[0].age").value(harry.getAge()));
        verify(studentRepository, times(1)).findFiveLastStudent();
    }
    @Test
    void testGetStudentsNameStartingWithA() throws Exception {
        Student student = new Student(14L, "Афанасий", 20);
        when(studentRepository.findAll()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/starts-with-a")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));

        verify(studentRepository, times(1)).findAll();
    }
    @Test
    void testGetAvgAgeAllStudents() throws Exception {
        when(studentRepository.findAll()).thenReturn(List.of(harry));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/avg"))
                .andExpect(status().isOk());

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testCreateStudent() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", harry.getId());
        studentObject.put("name", harry.getName());
        studentObject.put("age", harry.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(harry);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(harry.getId()))
                .andExpect(jsonPath("$.name").value(harry.getName()))
                .andExpect(jsonPath("$.age").value(harry.getAge()));

        verify(studentRepository,times(1)).save(any(Student.class));
    }

    @Test
    void testEditStudent() throws Exception {
        Student newStudent = new Student(harry.getId(), "Harry", harry.getAge());
        JSONObject studentObject = new JSONObject();
        studentObject.put("id", newStudent.getId());
        studentObject.put("name", newStudent.getName());
        studentObject.put("age", newStudent.getAge());

        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newStudent.getId()))
                .andExpect(jsonPath("$.name").value(newStudent.getName()))
                .andExpect(jsonPath("$.age").value(newStudent.getAge()));

        verify(studentRepository,times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + harry.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(studentRepository, times(1)).deleteById(harry.getId());
    }

}
