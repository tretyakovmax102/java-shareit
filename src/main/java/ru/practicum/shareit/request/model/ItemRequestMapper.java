package ru.practicum.shareit.request.model;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ItemRequestMapper {
    public static ItemRequest fromDto(ItemRequestDto itemRequestDto, User user) {
        ItemRequest itemRequest = new ItemRequest(itemRequestDto.getDescription(),user, LocalDateTime.now());
        itemRequest.setId(itemRequestDto.getId());
        return itemRequest;
    }

    public static ItemRequestDto toDto(ItemRequest itemRequest) {
        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(itemRequest.getItems() != null ? ItemMapper.toItemDtoRequestList(itemRequest.getItems()) : null)
                .build();
    }

    public static List<ItemRequestDto> toDtoList(List<ItemRequest> requests) {
        List<ItemRequestDto> itemRequest = new ArrayList<>();
        for (ItemRequest i : requests) {
            itemRequest.add(ItemRequestMapper.toDto(i));
        }
        return itemRequest;
    }
}