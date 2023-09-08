package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public UserDto getUser(@PathVariable(required = false) long id) {
        log.debug("/users");
        log.debug("get request '/users/{}'", id);
        return userService.getUserDto(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.debug("/users");
        log.debug("get request '/users");
        return userService.getAll();
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto user) {
        log.debug("/users");
        log.debug("post request '/users");
        return userService.create(UserMapper.fromUser(user));
    }

    @PatchMapping("{id}")
    public UserDto patchUpdate(@PathVariable long id, @RequestBody Map<String, String> updates) {
        log.debug("/users");
        log.debug("patch request '/users/{}'", id);
        return userService.update(id, updates);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable long id) {
        log.debug("/users");
        log.debug("delete request '/users/{}'", id);
        userService.delete(id);
    }
}
