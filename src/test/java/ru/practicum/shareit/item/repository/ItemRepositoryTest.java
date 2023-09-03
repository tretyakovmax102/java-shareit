package ru.practicum.shareit.item.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;


    @Test
    @DirtiesContext
    void findAllByOwnerIdTest() {
        userRepository.save(new User(1L,"user1","user1@mail.ru"));
        userRepository.save(new User(2L,"user2","user2@mail.ru"));

        Item item1 = new Item(1L,"item1","itemDecription1",true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        item1 = itemRepository.save(item1);

        Item item2 = new Item(2L,"item2","itemDecription2",true);
        item2.setOwner(userRepository.findById(2L).orElseThrow(() -> new NotFoundException("no found")));
        item2 = itemRepository.save(item2);

        Item item3 = new Item(3L,"item3","itemDecription3",true);
        item3.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        item3 = itemRepository.save(item3);

        List<Item> itemList = itemRepository.findAllByOwnerId(1L);
        assertEquals(itemList.size(),2);

        assertEquals(itemList.get(0).getId(),item1.getId());
        assertEquals(itemList.get(0).getName(),item1.getName());
        assertEquals(itemList.get(0).getDescription(),item1.getDescription());
        assertEquals(itemList.get(0).getOwner().getId(),item1.getOwner().getId());

        assertEquals(itemList.get(1).getId(),item3.getId());
        assertEquals(itemList.get(1).getName(),item3.getName());
        assertEquals(itemList.get(1).getDescription(),item3.getDescription());
        assertEquals(itemList.get(1).getOwner().getId(),item3.getOwner().getId());
    }

    @Test
    @DirtiesContext
    void searchAvailableItemsTest() {
        userRepository.save(new User(1L,"user1","user1@mail.ru"));

        Item item1 = new Item(1L,"item1","description",true);
        item1.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        itemRepository.save(item1);

        Item item2 = new Item(2L,"item2","DESCRIPTION",true);
        item2.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        item2 = itemRepository.save(item2);

        Item item3 = new Item(3L,"item3","DESCRIPTION",false);
        item3.setOwner(userRepository.findById(1L).orElseThrow(() -> new NotFoundException("no found")));
        item3 = itemRepository.save(item3);

        List<Item> itemList1 = itemRepository.searchAvailableItems("item2", Pageable.unpaged());
        assertEquals(itemList1.size(),1);
        assertEquals(itemList1.get(0).getId(),item2.getId());
        assertEquals(itemList1.get(0).getName(),item2.getName());
        assertEquals(itemList1.get(0).getDescription(),item2.getDescription());
        assertEquals(itemList1.get(0).getOwner().getId(),item2.getOwner().getId());

        List<Item> itemList2 = itemRepository.searchAvailableItems("description",Pageable.unpaged());
        assertEquals(itemList2.size(),2);
        assertEquals(itemList2.get(0).getId(),item1.getId());
        assertEquals(itemList2.get(0).getName(),item1.getName());
        assertEquals(itemList2.get(0).getDescription(),item1.getDescription());
        assertEquals(itemList2.get(0).getOwner().getId(),item1.getOwner().getId());

        assertEquals(itemList2.get(1).getId(),item2.getId());
        assertEquals(itemList2.get(1).getName(),item2.getName());
        assertEquals(itemList2.get(1).getDescription(),item2.getDescription());
        assertEquals(itemList2.get(1).getOwner().getId(),item2.getOwner().getId());
    }
}