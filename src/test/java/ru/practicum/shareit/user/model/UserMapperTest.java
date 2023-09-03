package ru.practicum.shareit.user.model;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserMapperTest {
    private final EasyRandom generator = new EasyRandom();

    @Test
    void toUserDto() {
        User user = generator.nextObject(User.class);
        UserDto userDto = UserMapper.toUserDto(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getName(), user.getName());
    }

    @Test
    void toUserDtoList() {
        User user1 = generator.nextObject(User.class);
        User user2 = generator.nextObject(User.class);
        List<User> users = List.of(user1, user2);
        List<UserDto> userDtos = UserMapper.toUserDtoList(users);
        assertEquals(userDtos.get(0).getId(), users.get(0).getId());
        assertEquals(userDtos.get(0).getEmail(), users.get(0).getEmail());
        assertEquals(userDtos.get(0).getName(), users.get(0).getName());
        assertEquals(userDtos.get(1).getId(), users.get(1).getId());
        assertEquals(userDtos.get(1).getEmail(), users.get(1).getEmail());
        assertEquals(userDtos.get(1).getName(), users.get(1).getName());
    }

    @Test
    void fromUser() {
        UserDto userDto = generator.nextObject(UserDto.class);
        User user = UserMapper.fromUser(userDto);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getName(), user.getName());
    }
}