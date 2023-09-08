package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {

    ItemRequest create(Long userId, ItemRequestDto itemRequestDto);

    List<ItemRequest> getOwnItemRequest(Long userId);

    List<ItemRequest> getAllItemRequest(Long userId, Long from, Long size);

    ItemRequest getItemRequest(Long userId, Long itemRequestId);
}
