package shareit.item.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.item.model.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemServiceDb;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestServiceInDb;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceDbTest {
    @InjectMocks
    private ItemServiceDb itemService;

    @Mock
    ItemRepository itemRepository;

    @Mock
    UserService userService;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    ItemRequestServiceInDb itemRequestService;

    @Test
    void testCreateAndGetItem() {
        assertThrows(NotFoundException.class, () -> itemService.getItemDtoById(10L,1L));
        assertThrows(NotFoundException.class, () -> itemService.getItemDtoById(0L,1L));
        assertThrows(NotFoundException.class, () -> itemService.getItemById(1L));
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("item")
                .description("descriptionItem")
                .available(true)
                .build();

        User user = new User(1L,"user1","user1@mail.ru");

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user);

        when(userService.getUser(user.getId()))
                .thenReturn(user);
        when(itemRepository.findById(1L))
                .thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class)))
                .thenReturn(item);
        when(commentRepository.findAllByItemId(itemDto.getId()))
                .thenReturn(new ArrayList<>());


        itemService.add(user.getId(), new ItemDtoRequest());
        ItemDto itemRes = itemService.getItemDtoById(1L,1L);
        assertEquals(1L,itemRes.getId());
        assertTrue(itemRes.getComments().isEmpty());
    }

    @Test
    void testUpdateAndSearchAndDeleteItem() {
        User user1 = new User(1L,"user1","user1@mail.ru");


        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("item")
                .description("descriptionItem")
                .available(true)
                .build();

        ItemDtoRequest itemDtoRequest = ItemDtoRequest.builder()
                .id(1L)
                .name("itemUpd")
                .description("descriptionItemUpd")
                .available(false)
                .build();

        when(itemRepository.save(any(Item.class)))
                .thenReturn(ItemMapper.toItem(itemDtoRequest));

        itemService.add(user1.getId(),itemDtoRequest);

        assertThrows(NotFoundException.class, () -> itemService.update(1L,null, null));
        assertThrows(NotFoundException.class, () -> itemService.update(1L,0L,null));
        assertThrows(NotFoundException.class, () -> itemService.update(2L,itemDto.getId(),null));
        when(itemRepository.findById(1L))
                .thenReturn(Optional.of(ItemMapper.toItem(itemDtoRequest)));

        assertThrows(NotFoundException.class, () -> itemService.update(1L,itemDto.getId(), any(Map.class)));

        Page<Item> itemSearch = new PageImpl<>(List.of(ItemMapper.toItem(itemDtoRequest)), Pageable.unpaged(),1L);

        List<ItemDto> searchResEmpty = itemService.searchItems(1L, "",1L,1L);
        assertEquals(0,searchResEmpty.size());

        Item item = itemService.getItemById(1L);
        item.setOwner(new User(1L, "1111", "1111"));
        when(itemRepository.findById(1L))
                .thenReturn(Optional.of(item));
        itemService.delete(1L, 1L);

        Map<String, String>  updates = new HashMap<>();
        updates.put("name", "updateName");
        updates.put("available", "false");
        updates.put("description", "updateDesk");

        ItemDto item1 = itemService.update(1L, 1L, updates);
        assertNotNull(item1);
    }

    @Test
    void testComment() {
        CommentDto comment = new CommentDto();
        comment.setText("text");
        Long userId = 1L;
        Long itemId = 1L;
        assertThrows(NotFoundException.class, () -> itemService.addComment(2L,2L,comment));

        when(userService.getUser(userId))
                .thenReturn(new User());
        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Item()));
        when(bookingRepository.findFirstByItemIdAndBookerIdAndStatusAndEndBefore(anyLong(), anyLong(), any(BookingStatus.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(new Booking()));

        assertThrows(NullPointerException.class, () -> itemService.addComment(userId,itemId,comment));

        assertThrows(NullPointerException.class, () -> itemService.addComment(2L, 3L, new CommentDto()));
        verify(userService,times(3)).getUser(anyLong());
    }

    @Test
    void testGetAllItemUsers() {
        User user1 = new User(1L,"user1","user1@mail.ru");
        User user2 = new User(2L,"user2","user2@mail.ru");

        ItemRequest request = new ItemRequest("need item",user1,LocalDateTime.now());

        ItemDtoRequest itemDto = ItemDtoRequest.builder()
                .id(1L)
                .name("item")
                .description("descriptionItem")
                .available(true)
                .requestId(1L)
                .build();

        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(user1);

        Page<Item> itemSearch = new PageImpl<>(List.of(item),Pageable.unpaged(),1L);
        when(bookingRepository.findAllByOwnerIdAndStatus(anyLong(), any(BookingStatus.class), any(Pageable.class)))
                .thenReturn(List.of(
                        new Booking(item,user2,LocalDateTime.now().minusDays(2),
                                LocalDateTime.now().minusDays(1), BookingStatus.APPROVED),
                        new Booking(item,user2,LocalDateTime.now().plusDays(1),
                                LocalDateTime.now().plusDays(2), BookingStatus.WAITING)
                ));
        List<ItemDto> result = itemService.getAllUserItems(user1.getId(),null,null);
        assertEquals(0,result.size());
    }
}