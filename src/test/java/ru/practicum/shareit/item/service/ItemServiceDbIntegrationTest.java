package ru.practicum.shareit.item.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceDbIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Test
    @DirtiesContext
    public void createItemAndGetTest() {
        UserDto user = userService.create(User.builder()
                .name("nameUser")
                .email("nameUser@mail.com")
                .build());

        ItemDtoRequest item1 = itemService.add(user.getId(),
                ItemDtoRequest.builder()
                        .name("item")
                        .description("description")
                        .available(true)
                        .build());

        assertNotNull(item1);
        assertEquals(item1.getName(), "item");

        ItemDto itemGetter = itemService.getItemDtoById(item1.getId(), user.getId());
        assertNotNull(itemGetter);
        assertEquals(item1.getId(), itemGetter.getId());
        assertEquals(item1.getName(), itemGetter.getName());

        itemService.delete(1L, item1.getId());
        List<ItemDto> items = itemService.getAllUserItems(user.getId(), null, null);
        assertTrue(items.isEmpty());
    }
}
