package service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.hogwarts.school1.model.Faculty;
import ru.hogwarts.school1.repositories.FacultyRepository;
import ru.hogwarts.school1.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class FacultyServiceTest {
    FacultyRepository repository = mock(FacultyRepository.class);

    FacultyService facultyService = new FacultyService(repository);
    Faculty gryffindor = new Faculty(0L, "Gryffindor", "Red");
    Faculty slytherin = new Faculty(0L, "Slytherin", "Green");
    Faculty ravenclaw = new Faculty(0L, "Ravenclaw", "Orange");

    @Test
    void checkAddFaculty() {
        when(repository.save(gryffindor)).thenReturn(gryffindor);

        Faculty result = facultyService.addFaculty(gryffindor);

        assertEquals(result, gryffindor);
        verify(repository, times(1)).save(gryffindor);
    }

    @Test
    void checkFindFaculty() {
        when(repository.findById(gryffindor.getId())).thenReturn(Optional.of(gryffindor));

        Faculty result = facultyService.findFaculty(gryffindor.getId());

        assertEquals(result, gryffindor);
        verify(repository, times(1)).findById(gryffindor.getId());
    }

    @Test
    void checkFindFacultyByNull() {
        when(repository.findById(gryffindor.getId())).thenReturn(Optional.empty());

        Faculty result = facultyService.findFaculty(gryffindor.getId());

        assertNull(result);
        verify(repository, times(1)).findById(gryffindor.getId());
    }

    @Test
    void checkEditFaculty() {
        when(repository.save(gryffindor)).thenReturn(gryffindor);

        gryffindor.setColor("Orange");
        Faculty result = facultyService.editFaculty(gryffindor);

        assertEquals("Orange", result.getColor());
        assertEquals(result.getId(), gryffindor.getId());
        verify(repository, times(1)).save(gryffindor);
    }

    @Test
    void checkDeleteFaculty() {
        facultyService.deleteFaculty(gryffindor.getId());
        verify(repository).deleteById(gryffindor.getId());
    }

    @Test
    void checkGetAllFaculty() {
        when(repository.findAll()).thenReturn(List.of(gryffindor, slytherin, ravenclaw));

        Collection<Faculty> expected = new ArrayList<>();
        expected.add(gryffindor);
        expected.add(slytherin);
        expected.add(ravenclaw);
        Collection<Faculty> actual = facultyService.getAllFaculty();

        assertEquals(expected, actual);
        verify(repository, times(1)).findAll();
    }

    @ParameterizedTest
    @ValueSource(strings = "Red")
    void checkFindByColor(String color) {
        when(repository.findByColor(color)).thenReturn(List.of(gryffindor));

        Collection<Faculty> expected = new ArrayList<>();
        expected.add(gryffindor);


        Collection<Faculty> actual = facultyService.findByColor(color);

        assertEquals(expected, actual);
        verify(repository, times(1)).findByColor(color);

    }

    @Test
    void checkFindByNameOrColor() {
        String name = "Gryffindor";
        String color = "Red";
        when(repository.findByNameOrColorIgnoreCase(name, color)).thenReturn(List.of(gryffindor));

        Collection<Faculty> expected = new ArrayList<>();
        expected.add(gryffindor);


        Collection<Faculty> actual = facultyService.findByNameOrColor(name, color);

        assertEquals(expected, actual);
        verify(repository, times(1)).findByNameOrColorIgnoreCase(name, color);

    }

    @ParameterizedTest
    @ValueSource(longs = 6)
    void checkFindFacultyByStudent(Long id) {
//        when(repository.findFacultyByStudentId(id)).thenReturn(gryffindor);
//        Faculty result = facultyService.findFacultyByStudent(id);
//        assertEquals(gryffindor, result);
//        verify(repository, times(1)).findFacultyByStudentId(id);
   }
}
