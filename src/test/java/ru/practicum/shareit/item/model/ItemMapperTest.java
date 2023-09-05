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
        assertEquals(itemDtoRequestList.get(0).getId(), item1.getId());
        assertEquals(itemDtoRequestList.get(0).getName(), item1.getName());
        assertEquals(itemDtoRequestList.get(0).getDescription(), item1.getDescription());
        assertEquals(itemDtoRequestList.get(0).getAvailable(), item1.getAvailable());
        assertEquals(itemDtoRequestList.get(0).getRequestId(), item1.getRequest().getId());
        assertEquals(itemDtoRequestList.get(1).getId(), item2.getId());
        assertEquals(itemDtoRequestList.get(1).getName(), item2.getName());
        assertEquals(itemDtoRequestList.get(1).getDescription(), item2.getDescription());
        assertEquals(itemDtoRequestList.get(1).getAvailable(), item2.getAvailable());
        assertEquals(itemDtoRequestList.get(1).getRequestId(), item2.getRequest().getId());
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
        assertEquals(itemDtoList.get(0).getId(), item1.getId());
        assertEquals(itemDtoList.get(0).getName(), item1.getName());
        assertEquals(itemDtoList.get(0).getDescription(), item1.getDescription());
        assertEquals(itemDtoList.get(0).getAvailable(), item1.getAvailable());
        assertEquals(itemDtoList.get(1).getId(), item2.getId());
        assertEquals(itemDtoList.get(1).getName(), item2.getName());
        assertEquals(itemDtoList.get(1).getDescription(), item2.getDescription());
        assertEquals(itemDtoList.get(1).getAvailable(), item2.getAvailable());
    }
}