package ru.practicum.shareit.item.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Qualifier("itemStorage")
public class ItemStorageInMemory implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();
    private long idItem = 1L;

    @Override
    public Item get(long id) {
        log.trace("get item..");
        Item item = items.get(id);
        if (item != null) {
            log.info("get item complete");
            return item;
        }
        log.info("404: item with {} id not found", id);
        throw new NotFoundException(String.format("404: item with {} id not found", id));
    }

    @Override
    public List<Item> getAllItemsUser(Long userId) {
        log.trace("getAllItemUsers");
        return items.values().stream()
                .filter(u -> u.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Item create(Item item) {
        log.trace("create item");
        item.setId(idItem++);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(long userId, long itemId, Map<String, String> updates) {
        log.trace("update item..");
        Item item = getAllItemsUser(userId).stream()
                .filter(i -> i.getId() == itemId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("item not found"));
        if (updates.containsKey("name") && !updates.get("name").isBlank()) {
            item.setName(updates.get("name"));
            log.info("name updated");
        }
        if (updates.containsKey("description") && !updates.get("description").isBlank()) {
            item.setDescription(updates.get("description"));
            log.info("description updated");
        }
        if (updates.containsKey("available") && !updates.get("available").isBlank()) {
            item.setAvailable(Boolean.valueOf(updates.get("available")));
            log.info("available updated");
        }
        log.info("update successful {}", itemId);
        return item;
    }

    @Override
    public List<Item> search(String string) {
        log.trace("search item");
        return string.isBlank() ? new ArrayList<>() :
                items.values().stream()
                        .filter(u -> u.getAvailable() &&
                                (u.getName().toLowerCase().contains(string.toLowerCase()) ||
                                        u.getDescription().toLowerCase().contains(string.toLowerCase())))
                        .collect(Collectors.toList());
    }

    @Override
    public void delete(long itemId) {
        log.trace("delete item");
        get(itemId);
        items.remove(itemId);
    }

    @Override
    public void deleteAll() {
        log.trace("deleteAll");
        idItem = 0;
        items.clear();
    }
}
