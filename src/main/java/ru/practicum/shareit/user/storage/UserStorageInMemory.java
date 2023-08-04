package ru.practicum.shareit.user.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.util.*;

@Slf4j
@Repository
@Qualifier("userStorage")
public class UserStorageInMemory implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long userId = 1L;

    @Override
    public User getUser(long id) {
        User user = users.get(id);
        if (user == null) {
            throw new NotFoundException("user not found");
        } else {
            return user;
        }
    }

    @Override
    public List<User> getAll() {
        log.info("size users: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        isValidEmail(user.getId(), user.getEmail());
        user.setId(userId++);
        users.put(user.getId(), user);
        log.info("user added");
        return getUser(user.getId());
    }

    @Override
    public User update(long id, Map<String, String> updates) {
        User user = getUser(id);
        if (updates.containsKey("name")) {
            String name = updates.get("name");
            user.setName(name.trim());
        }
        if (updates.containsKey("email")) {
            String email = updates.get("email");
            isValidEmail(id, email);
            user.setEmail(email);
        }
        users.put(user.getId(), user);
        return getUser(id);
    }

    @Override
    public void delete(long userId) {
        getUser(userId);
        users.remove(userId);
        log.info("User removed");
    }

    @Override
    public void deleteAll() {
        userId = 0;
        users.clear();
        log.info("Users deleted");
    }

    private boolean isValidEmail(Long id, String email) { //проверка занятости email
        if (!(getAll().stream()
                .filter(u -> u.getId() != id)
                .filter(u -> email.equals(u.getEmail())).count() == 0)) {
            throw new ValidationException("email not correct");
        }
        return true;
    }
}