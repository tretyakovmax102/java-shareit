package ru.practicum.shareit.item.model;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    private final EasyRandom generator = new EasyRandom();

    @Test
    void testToDto() {
        Comment comment = generator.nextObject(Comment.class);
        CommentDto commentDto = CommentMapper.toDto(comment);
        assertEquals(comment.getId(), commentDto.getId());

    }

    @Test
    void testToDtoList() {
        Comment comment1 = generator.nextObject(Comment.class);
        Comment comment2 = generator.nextObject(Comment.class);
        List<CommentDto> commentDtoList = CommentMapper.toDtoList(List.of(comment1, comment2));
        assertEquals(comment1.getId(), commentDtoList.get(0).getId());
        assertEquals(comment2.getId(), commentDtoList.get(1).getId());
    }
}