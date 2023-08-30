package ru.practicum.shareit.item.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    private long id;
    @NotBlank(message = "text is blank")
    @Size(max = 400)
    private String text;
    private String authorName;
    private String itemName;
    private LocalDateTime created;
}