package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {
    User create(User user);

    List<User> getAll();

    User getUser(long id);

    User update(long id, Map<String, String> updates);

    void delete(long userId);

    void deleteAll();
}