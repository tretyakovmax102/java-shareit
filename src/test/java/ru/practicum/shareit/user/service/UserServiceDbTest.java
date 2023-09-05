package ru.practicum.shareit.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceDbTest {
    @InjectMocks
    private UserServiceDb userService;

    @Mock
    UserRepository userRepository;

    @Test
    @DirtiesContext
    void testCreateAndGet() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("user1")
                .email("user1@mail.ru")
                .build();

        when(userRepository.findById(userDto.getId()))
                .thenReturn(Optional.of(UserMapper.fromUser(userDto)));
        when(userRepository.save(any(User.class)))
                .thenReturn(UserMapper.fromUser(userDto));
        User user = userService.getUser(userDto.getId());
        userService.getUserDto(1L);
        userRepository.save(user);
        assertEquals(user.getId(),userDto.getId());
        assertEquals(user.getName(),userDto.getName());
        assertEquals(user.getEmail(),userDto.getEmail());

        assertThrows(NotFoundException.class,() -> userService.getUser(-10L));
        assertThrows(NotFoundException.class,() -> userService.getUser(0L));
        assertThrows(NotFoundException.class, () -> userService.getUser(2L));

        verify(userRepository,times(1)).save(Mockito.any(User.class));
        verify(userRepository,times(5)).findById(Mockito.anyLong());
    }

    @Test
    @DirtiesContext
    void testGetAllUsersAndDelete() {
        when(userRepository.findAll())
                .thenReturn(new ArrayList<>());
        List<UserDto> emptyUserList = userService.getAll();
        assertTrue(emptyUserList.isEmpty());

        UserDto userDto1 = UserDto.builder()
                .id(1L)
                .name("user1")
                .email("user1@mail.ru")
                .build();
        UserDto userDto2 = UserDto.builder()
                .id(2L)
                .name("user2")
                .email("user2@mail.ru")
                .build();
        UserDto userDto3 = UserDto.builder()
                .id(3L)
                .name("user3")
                .email("user3@mail.ru")
                .build();
        List<User> users = new ArrayList<>();
        users.add(UserMapper.fromUser(userDto1));
        users.add(UserMapper.fromUser(userDto2));
        users.add(UserMapper.fromUser(userDto3));

        when(userRepository.findAll())
                .thenReturn(users);

        List<UserDto> userList = userService.getAll();
        assertEquals(users.size(), userList.size());
        assertEquals(users.get(0).getId(), userList.get(0).getId());
        assertEquals(users.get(1).getId(), userList.get(1).getId());
        assertEquals(users.get(2).getId(), userList.get(2).getId());

        userService.delete(1L);
        verify(userRepository,times(1)).deleteById(1L);
    }

    @Test
    @DirtiesContext
    void testUpdateUserTest() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("user1")
                .email("user1@mail.ru")
                .build();


        UserDto userDtoUpdate = UserDto.builder()
                .id(1L)
                .name("user1Update")
                .email("user1Update@mail.ru")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(UserMapper.fromUser(userDtoUpdate)));
        Map<String, String> update = new HashMap<>();
        update.put("name", userDtoUpdate.getName());
        update.put("email", userDtoUpdate.getEmail());
        UserDto user = userService.update(userDtoUpdate.getId(),update);
        assertEquals(user.getName(),"user1Update");
        assertEquals(user.getEmail(),"user1Update@mail.ru");
        assertThrows(NotFoundException.class,() -> userService.update(-10L, any(Map.class)));
        assertThrows(NotFoundException.class,() -> userService.update(0L, any(Map.class)));
    }
}