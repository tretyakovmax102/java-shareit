package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private static final String LINE = "X-Sharer-User-Id";
    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader(LINE) long userId, @PathVariable long itemId) {
        return itemService.getItemDtoById(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(LINE) long ownerId,
                                @RequestParam(value = "from", required = false) @PositiveOrZero Long from,
                                @RequestParam(value = "size", required = false) @PositiveOrZero Long size) {
        return itemService.getAllUserItems(ownerId, from, size);
    }

    @PostMapping
    public ItemDtoRequest create(@RequestHeader(LINE) long ownerId, @Valid @RequestBody ItemDtoRequest itemDto) {
        return itemService.add(ownerId, itemDto);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(LINE) long userId, @RequestParam(name = "text") String text,
                                     @RequestParam(value = "from", required = false) @PositiveOrZero Long from,
                                     @RequestParam(value = "size", required = false) @PositiveOrZero Long size) {
        return itemService.searchItems(userId, text, from, size);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@RequestHeader(LINE) long ownerId, @PathVariable long itemId) {
        itemService.delete(ownerId, itemId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(LINE) long ownerId, @PathVariable long itemId,
                          @RequestBody @NotNull Map<String, String> updates) {
        return itemService.update(ownerId, itemId, updates);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(LINE) long userId, @PathVariable long itemId,
                                 @Valid @RequestBody CommentDto commentDto) {
        return itemService.addComment(userId, itemId, commentDto);
    }

}
