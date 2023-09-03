package ru.practicum.shareit.request.service;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestMapper;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceInDbTest {

    @InjectMocks
    private ItemRequestServiceInDb itemRequestService;

    @Mock
    private UserService userService;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private UserRepository userRepository;

    private final EasyRandom generator = new EasyRandom();


    @Test
    void newItemRequestAndGetTest() {
        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .id(1L)
                .description("description1")
                .created(LocalDateTime.now())
                .items(List.of(generator.nextObject(ItemDtoRequest.class)))
                .build();

        User user = new User(1L,"name","email@mail.com");

        when(userService.getUser(anyLong()))
                .thenReturn(user);

        itemRequestService.create(1L,itemRequestDto);
        verify(itemRequestRepository,times(1)).save(any(ItemRequest.class));
        when(itemRequestRepository.findByRequester_IdOrderByCreatedAsc(1L))
                .thenReturn(List.of(ItemRequestMapper.fromDto(itemRequestDto,user)));

        List<ItemRequest> requests = itemRequestService.getOwnItemRequest(1L);
        assertEquals(requests.size(),1);
        assertEquals(requests.get(0).getDescription(),"description1");
        verify(itemRequestRepository,times(1)).findByRequester_IdOrderByCreatedAsc(anyLong());

        assertThrows(NullPointerException.class,
                () -> itemRequestService.getAllItemRequest(null,null,null));
        when(userRepository.findByIdNot(anyLong()))
                .thenReturn(List.of(user));

        when(itemRequestRepository.findALlByRequesterInOrderByCreatedAsc(anyList(),any(Pageable.class)))
                .thenReturn((List.of()));

        List<ItemRequest> requestsAllIR = itemRequestService.getAllItemRequest(1L,null,null);
        assertTrue(requestsAllIR.isEmpty());
        verify(userRepository,times(1)).findByIdNot(anyLong());
        verify(itemRequestRepository,
                times(1)).findALlByRequesterInOrderByCreatedAsc(anyList(),any(Pageable.class));

        assertThrows(NotFoundException.class,
                () -> itemRequestService.getItemRequest(1L,null));
        assertThrows(NotFoundException.class,
                () -> itemRequestService.getItemRequest(1L,0L));

        when(itemRequestRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> itemRequestService.getItemRequest(1L,1L));

        when(itemRequestRepository.findById(anyLong()))
                .thenReturn(Optional.of(ItemRequestMapper.fromDto(itemRequestDto,user)));
        ItemRequest itemRequest = itemRequestService.getItemRequest(1L,1L);
        assertNotNull(itemRequest);
        verify(itemRequestRepository,times(3)).findById(anyLong());
    }
}