package ru.hogwarts.school1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school1.model.Faculty;
import ru.hogwarts.school1.model.Student;
import ru.hogwarts.school1.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getFacultyByNameOrColor(@RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) String color) {
        if (name != null && !name.isEmpty()) {
            return ResponseEntity.ok(facultyService.findByNameOrColor(name, color));
        }
        if (color != null && !color.isEmpty()) {
            return ResponseEntity.ok(facultyService.findByNameOrColor(name, color));
        }
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }
    @GetMapping("students")
    public ResponseEntity <Collection<Student>> findStudentsByFaculty(@RequestParam Long id) {
        if(id != null) {
            return ResponseEntity.ok(facultyService.findStudentByFacultyId(id));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);

        return ResponseEntity.ok().build();
    }
}
