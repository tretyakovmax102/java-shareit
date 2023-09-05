package ru.practicum.shareit.request.model;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestMapperTest {
    private final EasyRandom generator = new EasyRandom();

    @Test
    void testToRequestDto() {
        ItemRequest itemRequest = generator.nextObject(ItemRequest.class);
        ItemRequestDto itemRequestDto = ItemRequestMapper.toDto(itemRequest);
        assertEquals(itemRequestDto.getItems().size(), itemRequest.getItems().size());
        assertEquals(itemRequestDto.getDescription(), itemRequest.getDescription());
        assertEquals(itemRequestDto.getCreated(), itemRequest.getCreated());
        assertEquals(itemRequestDto.getId(), itemRequest.getId());
    }

    @Test
    void testToItemRequestDtoList() {
        ItemRequest itemRequest1 = generator.nextObject(ItemRequest.class);
        ItemRequest itemRequest2 = generator.nextObject(ItemRequest.class);
        UserMapper userMapper = new UserMapper();
        ItemRequestMapper itemRequestMapper = new ItemRequestMapper();
        List<ItemRequest> itemRequests = List.of(itemRequest1, itemRequest2);
        List<ItemRequestDto> itemRequestDtos = ItemRequestMapper.toDtoList(itemRequests);
        assertEquals(itemRequest1.getItems().size(), itemRequestDtos.get(0).getItems().size());
        assertEquals(itemRequest1.getDescription(), itemRequestDtos.get(0).getDescription());
        assertEquals(itemRequest1.getCreated(), itemRequestDtos.get(0).getCreated());
        assertEquals(itemRequest1.getId(), itemRequestDtos.get(0).getId());
        assertEquals(itemRequest2.getItems().size(), itemRequestDtos.get(1).getItems().size());
        assertEquals(itemRequest2.getDescription(), itemRequestDtos.get(1).getDescription());
        assertEquals(itemRequest2.getCreated(), itemRequestDtos.get(1).getCreated());
        assertEquals(itemRequest2.getId(), itemRequestDtos.get(1).getId());
    }

    @Test
    void testToItemRequest() {
        ItemRequestDto itemRequestDto = generator.nextObject(ItemRequestDto.class);
        ItemRequest itemRequest = ItemRequestMapper.fromDto(itemRequestDto, generator.nextObject(User.class));
        assertEquals(itemRequestDto.getDescription(), itemRequest.getDescription());
        assertEquals(itemRequestDto.getId(), itemRequest.getId());
    }
}