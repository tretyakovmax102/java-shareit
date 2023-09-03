package ru.practicum.shareit.request.repository;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ItemRequestRepositoryTest {
    @Autowired
    private ItemRequestRepository itemRequestRepository;
    @Autowired
    private UserRepository userRepository;
    private final EasyRandom generator = new EasyRandom();


    @Test
    @DirtiesContext
    void findALlByRequesterInOrderByCreatedAscTest() {
        List<User> users = new ArrayList<>();
        User user1 = userRepository.save(generator.nextObject(User.class));
        User user2 = userRepository.save(generator.nextObject(User.class));
        User user3 = userRepository.save(generator.nextObject(User.class));
        users.add(user1);
        users.add(user2);
        users.add(user3);

        ItemRequest item1 = itemRequestRepository.save(new ItemRequest("des1", user1,
                LocalDateTime.of(2023,10,20, 1, 1).minusHours(1)));
        ItemRequest item2 = itemRequestRepository.save(new ItemRequest("des2", user2,
                LocalDateTime.of(2023,10,20, 1, 1).minusHours(2)));
        ItemRequest item3 = itemRequestRepository.save(new ItemRequest("des3", user3,
                LocalDateTime.of(2023,10,20, 1, 1)));

        User userNotInList = userRepository.save(generator.nextObject(User.class));
        ItemRequest item4 = itemRequestRepository.save(new ItemRequest("des4", userNotInList,
                LocalDateTime.of(2023,10,20, 1, 1)));

        List<ItemRequest> itemRequests = itemRequestRepository.findALlByRequesterInOrderByCreatedAsc(users,
                Pageable.unpaged());
        assertEquals(3L, itemRequests.size());
        assertEquals(item2.getId(), itemRequests.get(0).getRequester().getId());
        assertEquals(item1.getId(), itemRequests.get(1).getRequester().getId());
        assertEquals(item3.getId(), itemRequests.get(2).getRequester().getId());
    }

    @Test
    @DirtiesContext
    void findByRequester_IdOrderByCreatedAscTest() {
        List<User> users = new ArrayList<>();
        User user1 = userRepository.save(generator.nextObject(User.class));
        User user2 = userRepository.save(generator.nextObject(User.class));
        users.add(user1);
        users.add(user2);

        ItemRequest item1 = itemRequestRepository.save(new ItemRequest("des4", user1,
                LocalDateTime.of(2023,10,20, 1, 1).minusHours(1)));
        ItemRequest item2 = itemRequestRepository.save(new ItemRequest("des5", user2,
                LocalDateTime.of(2023,10,20, 1, 1).minusHours(2)));
        ItemRequest item3 = itemRequestRepository.save(new ItemRequest("des6", user1,
                LocalDateTime.of(2023,10,20, 1, 1)));

        List<ItemRequest> itemRequests = itemRequestRepository.findByRequester_IdOrderByCreatedAsc(user1.getId());
        assertEquals(2, itemRequests.size());
        assertEquals(item1.getId(), itemRequests.get(0).getId());
        assertEquals(item3.getId(), itemRequests.get(1).getId());
    }
}