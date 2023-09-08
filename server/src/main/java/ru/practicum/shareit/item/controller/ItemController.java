package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private static final String LINE = "X-Sharer-User-Id";
    private final ItemService itemService;

    @PostMapping
    public ItemDtoRequest create(@RequestHeader(LINE) long ownerId, @RequestBody ItemDtoRequest itemDto) {
        return itemService.add(ownerId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(LINE) long ownerId, @PathVariable long itemId,
                          @RequestBody Map<String, String> updates) {
        return itemService.update(ownerId, itemId, updates);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader(LINE) long userId, @PathVariable long itemId) {
        return itemService.getItemDtoById(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(LINE) long ownerId,
                                @RequestParam(value = "from", required = false) Long from,
                                @RequestParam(value = "size", required = false) Long size) {
        return itemService.getAllUserItems(ownerId, from, size);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(LINE) long userId, @RequestParam(name = "text") String text,
                                     @RequestParam(value = "from", required = false) Long from,
                                     @RequestParam(value = "size", required = false) Long size) {
        return itemService.searchItems(userId, text, from, size);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@RequestHeader(LINE) long ownerId, @PathVariable long itemId) {
        itemService.delete(ownerId, itemId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(LINE) long userId, @PathVariable long itemId,
                                 @RequestBody CommentDto commentDto) {
        return itemService.addComment(userId, itemId, commentDto);
    }
}
