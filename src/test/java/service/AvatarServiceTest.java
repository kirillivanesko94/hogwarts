package service;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.hogwarts.school1.model.Avatar;
import ru.hogwarts.school1.repositories.AvatarRepository;
import ru.hogwarts.school1.service.AvatarService;
import ru.hogwarts.school1.service.StudentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AvatarServiceTest {
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    StudentService studentService = mock(StudentService.class);
    AvatarService avatarService = new AvatarService(avatarRepository, studentService);

    @Test
    void testGetAll() {
        int page = 1;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        Avatar avatar = new Avatar();
        avatar.setId(1l);
        List<Avatar> expected = List.of(avatar);
        PageImpl<Avatar> pageImpl = new PageImpl<>(expected);
        when(avatarRepository.findAll(pageRequest)).thenReturn(pageImpl);

        List<Avatar> actual = avatarService.getAll(page, size);

        assertEquals(expected,actual);
        verify(avatarRepository,times(1)).findAll(pageRequest);

    }

}
