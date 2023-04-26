package ru.hogwarts.school1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school1.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Avatar findAvatarByStudentId(Long studentId);
}
