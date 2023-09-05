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
        List<ItemRequest> itemRequests = List.of(itemRequest1, itemRequest2);
        List<ItemRequestDto> itemRequestDtos = ItemRequestMapper.toDtoList(itemRequests);
        assertEquals(itemRequestDtos.get(0).getItems().size(), itemRequest1.getItems().size());
        assertEquals(itemRequestDtos.get(0).getDescription(), itemRequest1.getDescription());
        assertEquals(itemRequestDtos.get(0).getCreated(),itemRequest1.getCreated());
        assertEquals(itemRequestDtos.get(0).getId(), itemRequest1.getId());
        assertEquals(itemRequestDtos.get(1).getItems().size(), itemRequest2.getItems().size());
        assertEquals(itemRequestDtos.get(1).getDescription(), itemRequest2.getDescription());
        assertEquals(itemRequestDtos.get(1).getCreated(), itemRequest2.getCreated());
        assertEquals(itemRequestDtos.get(1).getId(), itemRequest2.getId());
    }

    @Test
    void testToItemRequest() {
        ItemRequestDto itemRequestDto = generator.nextObject(ItemRequestDto.class);
        ItemRequest itemRequest = ItemRequestMapper.fromDto(itemRequestDto, generator.nextObject(User.class));
        assertEquals(itemRequestDto.getDescription(), itemRequest.getDescription());
        assertEquals(itemRequestDto.getId(), itemRequest.getId());
    }
}