package shareit.request.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRequestServiceInDbIntegrationTest {

    @Autowired
    ItemRequestService itemRequestService;

    @Autowired
    UserService userService;

    @Test
    @DirtiesContext
    void testNewItemRequestAndGet() {
        User user2 = UserMapper.fromUser(userService.create(User.builder()
                .name("name2")
                .email("name2@mail.com")
                .build()));

        ItemRequest itemRequest = itemRequestService.create(user2.getId(),
                ItemRequestDto.builder()
                        .description("need Item 2")
                        .created(LocalDateTime.now())
                        .build());

        assertNotNull(itemRequest);
        assertEquals(1L, itemRequest.getId());

        List<ItemRequest> listItemRequest = itemRequestService.getOwnItemRequest(user2.getId());
        assertEquals(1, listItemRequest.size());
        assertEquals(1L, listItemRequest.get(0).getId());

        List<ItemRequest> allItemRequest = itemRequestService.getAllItemRequest(user2.getId(),null,null);
        assertTrue(allItemRequest.isEmpty());

        ItemRequest itemRequest1 = itemRequestService.getItemRequest(user2.getId(),1L);
        assertEquals(1L, itemRequest1.getId());
        assertEquals("need Item 2", itemRequest1.getDescription());
    }
}