package ru.hogwarts.school1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school1.model.Faculty;
import ru.hogwarts.school1.model.Student;
import ru.hogwarts.school1.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create the next faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Was invoked method to search for a faculty with the following id: {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method for edit and save the next faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method to deleted for a faculty with the following id: {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Was invoked method to search for a faculty with the following color: {}", color);
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.info("Was invoked method to get all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.info("Was invoked method to find all faculties by the following name - {} or color - {}", name, color);
        return facultyRepository.findByNameOrColorIgnoreCase(name, color);
    }

    public Collection<Student> findStudentsByFacultyId(Long id) {
        logger.info("Was invoked method to find all students by the following faculty id - {}", id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isPresent()) {
            return faculty.get().getStudent();
        } else {
            return Collections.emptyList();
        }
    }

    public String getLongestFacultyName() {
        return getAllFaculty()
                .parallelStream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }
}
