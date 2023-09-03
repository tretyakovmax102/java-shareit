package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DirtiesContext
    void findAllByItemIdTest() {
        User user1 = new User(1L,"user1","user1@mail.ru");
        userRepository.save(user1);

        User user2 = new User(2L,"user2","user2@mail.ru");
        userRepository.save(user2);

        User user3 = new User(3L,"user3","user3@mail.ru");
        userRepository.save(user3);

        Item item1 = new Item(1L,"item1","itemDecription1",true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item1);

        Item item2 = new Item(2L,"item2","itemDecription2",true);
        item2.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item2);

        LocalDateTime date = LocalDateTime.of(2023,10,20, 1, 1);
        Comment comment1 = commentRepository.save(new Comment("comment1", item1,  user2, date));
        Comment comment2 = commentRepository.save(new Comment("comment2", item2,  user3, date));
        Comment comment3 = commentRepository.save(new Comment("comment3", item1,  user3, date));
        Comment comment4 = commentRepository.save(new Comment("comment4", item2,  user2, date));

        List<Comment> comments = commentRepository.findAllByItemIdIn(List.of(item1.getId(),item2.getId()));
        assertEquals(comments.size(),4);
        assertEquals(comments.get(0).getText(),comment1.getText());
        assertEquals(comments.get(1).getText(),comment2.getText());
        assertEquals(comments.get(2).getText(),comment3.getText());
        assertEquals(comments.get(3).getText(),comment4.getText());
    }

    @Test
    @DirtiesContext
    public void findAllByItemIdInTest() {
        User user1 = new User(1L,"user1","user1@mail.ru");
        userRepository.save(user1);
        User user2 = new User(2L,"user2","user2@mail.ru");
        userRepository.save(user2);
        User user3 = new User(3L,"user3","user3@mail.ru");
        userRepository.save(user3);
        User user4 = new User(4L,"user4","user4@mail.ru");
        userRepository.save(user4);
        Item item1 = new Item(1L,"item1","itemDecription1",true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item1);

        LocalDateTime date = LocalDateTime.of(2023,10,20, 1, 1);
        Comment comment1 = commentRepository.save(new Comment("comment1", item1,  user2, date));
        Comment comment2 = commentRepository.save(new Comment("comment2", item1,  user3, date));
        Comment comment3 = commentRepository.save(new Comment("comment3", item1,  user4, date));

        List<Comment> comments = commentRepository.findAllByItemId(1L);
        assertEquals(comments.size(),3);
        assertEquals(comments.get(0).getText(),comment1.getText());
        assertEquals(comments.get(1).getText(),comment2.getText());
        assertEquals(comments.get(2).getText(),comment3.getText());
    }
}