package shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void testCreate() throws Exception {
        UserDto userDto = UserDto.builder()
                .name("user1")
                .email("user1@mail.ru")
                .build();

        when(userService.create(Mockito.any(User.class)))
                .thenAnswer(u -> {
                    User userMock = u.getArgument(0, User.class);
                    userMock.setId(1L);
                    return UserMapper.toUserDto(userMock);
                });

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1L), Long.class));
    }

    @Test
    void testGetUser() throws Exception {

        when(userService.getUser(anyLong()))
                .thenReturn(new User(1L,"name","email@mail.com"));

        mvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void testGetAllUsers() throws Exception {

        when(userService.getAll())
                .thenReturn(List.of(new UserDto(1L,"name1","email1@mail.com"),
                        new UserDto(2L,"name2","email2@mail.com")));

        mvc.perform(get("/users/")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id",is(1L), Long.class))
                .andExpect(jsonPath("$.[1].id",is(2L), Long.class));
    }

    @Test
    void testUpdateUserTes() throws Exception {
        User user = User.builder()
                .id(1L)
                .name("nameUpd")
                .email("emailUpd@mail.com")
                .build();
        Map<String, String> update = new HashMap<>();
        update.put("name", user.getName());
        update.put("email", user.getEmail());
        when(userService.update(anyLong(),Mockito.any()))
                .thenReturn(UserMapper.toUserDto(user));

        mvc.perform(patch("/users/1")
                        .content(mapper.writeValueAsString(user))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1L), Long.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        mvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}