package ru.hogwarts.school1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school1.model.Faculty;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColor(String color);

}
