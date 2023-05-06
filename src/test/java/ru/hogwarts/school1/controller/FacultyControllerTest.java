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
import ru.hogwarts.school1.repositories.FacultyRepository;
import ru.hogwarts.school1.service.FacultyService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyControllerTest facultyController;
    Faculty faculty = new Faculty(1L, "Gryffindor", "Red");

    @Test
    void checkGetFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

        verify(facultyRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void checkGetFacultyByNameOrColor() throws Exception {
        when(facultyRepository.findByNameOrColorIgnoreCase(any(String.class), any(String.class))).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?name=gryffindor&color=Red")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo(faculty.getName())))
                .andExpect(jsonPath("$[0].color", equalTo(faculty.getColor())));

        verify(facultyRepository, times(1))
                .findByNameOrColorIgnoreCase(any(String.class), any(String.class));
    }

    @Test
    void checkFindStudentsByFaculty() throws Exception {
        Student harry = new Student(1L, "Harry", 11);
        faculty.setStudent(List.of(harry));

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/students?id=1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo(harry.getName())))
                .andExpect(jsonPath("$[0].age", equalTo(harry.getAge())));

        verify(facultyRepository, times(1)).findById(any(Long.class));
    }
    @Test
    void testGetLongestFacultyName() throws Exception {
        when(facultyRepository.findAll()).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/longest-faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(faculty.getName()));

        verify(facultyRepository, times(1)).findAll();
    }

    @Test
    void checkCreateFaculty() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", faculty.getId());
        jsonObject.put("name", faculty.getName());
        jsonObject.put("color", faculty.getColor());

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

        verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    void checkEditFaculty() throws Exception {
        Faculty newFaculty = new Faculty(faculty.getId(), "Грифиндор", "Красный");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", newFaculty.getId());
        jsonObject.put("name", newFaculty.getName());
        jsonObject.put("color", newFaculty.getColor());
        when(facultyRepository.save(any(Faculty.class))).thenReturn(newFaculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newFaculty.getId()))
                .andExpect((jsonPath("$.name").value(newFaculty.getName())))
                .andExpect((jsonPath("$.color").value(newFaculty.getColor())));

        verify(facultyRepository, times(1)).save(any(Faculty.class));
    }

    @Test
    void testDeleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + faculty.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(facultyRepository, times(1)).deleteById(faculty.getId());
    }
}
