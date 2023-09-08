package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoRequest;

import java.util.List;
import java.util.Map;

public interface ItemService {
    ItemDtoRequest add(Long ownerId, ItemDtoRequest item);

    ItemDto update(Long ownerId, Long itemId, Map<String, String> updates);

    Item getItemById(Long itemId);

    Long getOwnerId(Long itemId);

    ItemDto getItemDtoById(Long itemId, Long userId);

    List<ItemDto> getAllUserItems(Long userId, Long from, Long size);

    List<ItemDto> searchItems(Long userId, String query, Long from, Long size);

    void delete(Long ownerId, Long itemId);

    CommentDto addComment(Long userId, Long itemId, CommentDto commentDto);
}
