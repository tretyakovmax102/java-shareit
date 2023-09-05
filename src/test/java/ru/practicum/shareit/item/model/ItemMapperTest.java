package ru.practicum.shareit.item.model;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {
    private final EasyRandom generator = new EasyRandom();

    @Test
    void testToItemDto() {
        Item item = generator.nextObject(Item.class);
        ItemDto itemDto = ItemMapper.toItemDto(item);
        assertEquals(item.getId(), itemDto.getId());
        assertEquals(item.getName(), itemDto.getName());
        assertEquals(item.getDescription(), itemDto.getDescription());
        assertEquals(item.getAvailable(), itemDto.getAvailable());
    }

    @Test
    void testToItemDtoRequest() {
        Item item = generator.nextObject(Item.class);
        ItemDtoRequest itemDtoRequest = ItemMapper.toItemDtoRequest(item);
        assertEquals(item.getId(), itemDtoRequest.getId());
        assertEquals(item.getName(), itemDtoRequest.getName());
        assertEquals(item.getDescription(), itemDtoRequest.getDescription());
        assertEquals(item.getAvailable(), itemDtoRequest.getAvailable());
        assertEquals(item.getRequest().getId(), itemDtoRequest.getRequestId());
    }

    @Test
    void testToItemDtoRequestList() {
        Item item1 = generator.nextObject(Item.class);
        Item item2 = generator.nextObject(Item.class);
        List<ItemDtoRequest> itemDtoRequestList = ItemMapper.toItemDtoRequestList(List.of(item1, item2));
        assertEquals(item1.getId(), itemDtoRequestList.get(0).getId());
        assertEquals(item1.getName(), itemDtoRequestList.get(0).getName());
        assertEquals(item1.getDescription(), itemDtoRequestList.get(0).getDescription());
        assertEquals(item1.getAvailable(), itemDtoRequestList.get(0).getAvailable());
        assertEquals(item1.getRequest().getId(), itemDtoRequestList.get(0).getRequestId());
        assertEquals(item2.getId(), itemDtoRequestList.get(1).getId());
        assertEquals(item2.getName(), itemDtoRequestList.get(1).getName());
        assertEquals(item2.getDescription(), itemDtoRequestList.get(1).getDescription());
        assertEquals(item2.getAvailable(), itemDtoRequestList.get(1).getAvailable());
        assertEquals(item2.getRequest().getId(), itemDtoRequestList.get(1).getRequestId());
    }

    @Test
    void testToItem() {
        ItemDto itemDto = generator.nextObject(ItemDto.class);
        Item item = ItemMapper.toItem(itemDto);
        assertEquals(itemDto.getId(), item.getId());
        assertEquals(itemDto.getName(), item.getName());
        assertEquals(itemDto.getDescription(), item.getDescription());
        assertEquals(itemDto.getAvailable(), item.getAvailable());
    }

    @Test
    void testToItemDtoList() {
        Item item1 = generator.nextObject(Item.class);
        Item item2 = generator.nextObject(Item.class);
        List<ItemDto> itemDtoList = ItemMapper.toItemDtoList(List.of(item1, item2));
        assertEquals(item1.getId(), itemDtoList.get(0).getId());
        assertEquals(item1.getName(), itemDtoList.get(0).getName());
        assertEquals(item1.getDescription(), itemDtoList.get(0).getDescription());
        assertEquals(item1.getAvailable(), itemDtoList.get(0).getAvailable());
        assertEquals(item2.getId(), itemDtoList.get(1).getId());
        assertEquals(item2.getName(), itemDtoList.get(1).getName());
        assertEquals(item2.getDescription(), itemDtoList.get(1).getDescription());
        assertEquals(item2.getAvailable(), itemDtoList.get(1).getAvailable());
    }
}