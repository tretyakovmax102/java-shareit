package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private static final String LINE = "X-Sharer-User-Id";

    private final ItemRequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createItemRequest(@RequestBody @Valid ItemRequestDto itemRequestDto,
                                                    @RequestHeader(LINE) @Positive Long userId) {
        return requestClient.createItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemRequestByOwner(@RequestHeader(LINE) @Positive Long userId) {
        return requestClient.getItemRequestByOwner(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequestByPage(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                          @RequestParam(defaultValue = "10") @Positive int size,
                                                          @RequestHeader(LINE) @Positive Long userId) {
        return requestClient.getAllItemRequestByPage(from, size, userId);
    }

    @GetMapping("{requestId}")
    public ResponseEntity<Object> getItemRequest(@RequestHeader(LINE) @Positive Long userId,
                                                 @PathVariable @Positive Long requestId) {
        return requestClient.getItemRequest(userId, requestId);
    }
}