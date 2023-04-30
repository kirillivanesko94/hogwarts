package ru.hogwarts.school1.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school1.model.Avatar;
import ru.hogwarts.school1.repositories.AvatarRepository;
import ru.hogwarts.school1.repositories.StudentRepository;
import ru.hogwarts.school1.service.AvatarService;
import ru.hogwarts.school1.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AvatarController.class)
public class AvatarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private AvatarController avatarController;

    @Test
    public void testGetAll() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize);
        Avatar avatar = new Avatar();
        avatar.setId(1L);
        List<Avatar> avatars = new ArrayList<>();
        avatars.add(avatar);
        PageImpl<Avatar> pageImpl = new PageImpl<>(avatars);

        when(avatarRepository.findAll(pageRequest)).thenReturn(pageImpl);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/avatar/get-all")
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(avatar.getId()));

        verify(avatarRepository,times(1)).findAll(pageRequest);
    }

}
