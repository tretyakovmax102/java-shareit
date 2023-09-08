package shareit.item.model;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.CommentMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(commentDtoList.get(0).getId(), comment1.getId());
        assertEquals(commentDtoList.get(1).getId(), comment2.getId());
    }
}