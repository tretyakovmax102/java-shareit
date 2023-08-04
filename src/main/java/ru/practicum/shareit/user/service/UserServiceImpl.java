package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public UserDto create(User user) {
        return UserMapper.toUserDto(userStorage.create(user));
    }

    @Override
    public UserDto update(long id, Map<String, String> updates) {
        return UserMapper.toUserDto(userStorage.update(id, updates));
    }

    @Override
    public UserDto getUser(long id) {
        return UserMapper.toUserDto(userStorage.getUser(id));
    }

    @Override
    public List<UserDto> getAll() {
        return UserMapper.toUserDtoList(userStorage.getAll());
    }

    @Override
    public void delete(long userId) {
        userStorage.delete(userId);
    }

    @Override
    public void deleteAll() {
        userStorage.deleteAll();
    }

}