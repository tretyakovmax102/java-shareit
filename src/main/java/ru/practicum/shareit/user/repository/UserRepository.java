package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Map;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByEmailEqualsIgnoreCase(String email);

    List<User> findUsersByNameEqualsIgnoreCase(String name);

}