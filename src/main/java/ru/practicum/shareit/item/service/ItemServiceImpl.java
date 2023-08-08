package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public ItemDto create(long userId, Item item) {
        User owner = userStorage.getUser(userId);
        item.setOwner(owner);
        return ItemMapper.toItemDto(itemStorage.create(item));
    }

    @Override
    public ItemDto update(long userId, long itemId, Map<String, String> updates) {
        return ItemMapper.toItemDto(itemStorage.update(userId, itemId, updates));

    }

    @Override
    public ItemDto get(long itemId) {
        return ItemMapper.toItemDto(itemStorage.get(itemId));
    }

    @Override
    public List<ItemDto> getAllItemsUser(long userId) {
        return ItemMapper.toItemDtoList(itemStorage.getAllItemsUser(userId));
    }

    @Override
    public void delete(long itemId) {
        itemStorage.delete(itemId);
    }

    @Override
    public List<ItemDto> search(String string) {
        return itemStorage.search(string).stream().map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
