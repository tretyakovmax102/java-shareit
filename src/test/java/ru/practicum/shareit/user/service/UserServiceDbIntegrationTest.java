package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceDbIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    @DirtiesContext
    void testCreateAndGet() {
        assertThrows(NotFoundException.class, () -> userService.getUser(0L));
        assertThrows(NotFoundException.class, () -> userService.getUser(-10L));
        assertThrows(NotFoundException.class, () -> userService.getUser(10L));

        User user = User.builder()
                .name("user1")
                .email("user1@user.ru")
                .build();
        userService.create(user);
        User userTest = userService.getUser(user.getId());
        assertEquals(user.getId(),userTest.getId());
        assertEquals(user.getName(),userTest.getName());
        assertEquals(user.getEmail(),userTest.getEmail());
    }

    @Test
    @DirtiesContext
    void testGetAllUsersAndDelete() {
        List<UserDto> emptyUserList = userService.getAll();
        assertTrue(emptyUserList.isEmpty());

        User user1 = User.builder()
                .name("user1")
                .email("user1@mail.ru")
                .build();

        User user2 = User.builder()
                .name("user2")
                .email("user2@mail.ru")
                .build();

        User user3 = User.builder()
                .name("user3")
                .email("user3@mail.ru")
                .build();

        userService.create(user1);
        userService.create(user2);
        userService.create(user3);

        List<UserDto> userList = userService.getAll();
        assertEquals(userList.size(),3);
        assertEquals(userList.get(0).getId(),userService.getUser(1L).getId());
        assertEquals(userList.get(0).getName(),userService.getUser(1L).getName());
        assertEquals(userList.get(0).getEmail(),userService.getUser(1L).getEmail());

        assertEquals(userList.get(1).getId(),userService.getUser(2L).getId());
        assertEquals(userList.get(1).getName(),userService.getUser(2L).getName());
        assertEquals(userList.get(1).getEmail(),userService.getUser(2L).getEmail());

        assertEquals(userList.get(2).getId(),userService.getUser(3L).getId());
        assertEquals(userList.get(2).getName(),userService.getUser(3L).getName());
        assertEquals(userList.get(2).getEmail(),userService.getUser(3L).getEmail());

        userService.delete(1L);
        assertThrows(NotFoundException.class, () -> userService.getUser(1L));
        assertEquals(userService.getAll().size(),2);
    }

    @Test
    @DirtiesContext
    void testUpdateUser() {
        User user = User.builder()
                .name("user1")
                .email("user1@mail.ru")
                .build();

        userService.create(user);
        Map<String, String> update = new HashMap<>();
        update.put("name", "updateName");
        update.put("email", "updateEmail@user.ru");
        userService.update(1L, update);
        user = userService.getUser(user.getId());
        assertEquals(user.getName(),update.get("name"));
        assertEquals(user.getEmail(),update.get("email"));
        assertThrows(NotFoundException.class,() -> userService.update(0L, update));
    }

}