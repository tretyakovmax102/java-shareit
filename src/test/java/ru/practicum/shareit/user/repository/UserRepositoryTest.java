package ru.practicum.shareit.user.repository;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    private final EasyRandom generator = new EasyRandom();

    @Test
    @DirtiesContext
    void testFindByIdNot() {
        User user1 = generator.nextObject(User.class);
        User user2 = generator.nextObject(User.class);
        User user3 = generator.nextObject(User.class);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        assertEquals(userRepository.findAll().size() - 1, userRepository.findByIdNot(1L).size());
        assertEquals(0, userRepository.findByIdNot(1L).stream().filter(c -> c.getId() == 1L).count());
    }
}