package ru.practicum.shareit.item.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private long id;
    private String text;
    private String authorName;
    private String itemName;
    private LocalDateTime created;
}
