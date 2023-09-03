package ru.practicum.shareit.request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.service.ItemRequestServiceInDb;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {
    @MockBean
    ItemRequestServiceInDb itemRequestService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void newItemRequestTest() throws  Exception {

        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .id(1L)
                .description("description")
                .created(LocalDateTime.now())
                .build();

        when(itemRequestService.create(anyLong(), Mockito.any(ItemRequestDto.class)))
                .thenReturn(new ItemRequest(1L,"description",new User(),
                        LocalDateTime.now(),new ArrayList<>()));

        mvc.perform(post("/requests")
                        .content(mapper.writeValueAsString(itemRequestDto))
                        .header("X-Sharer-User-Id",1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1L), Long.class));
    }

    @Test
    public void getOwnItemRequestTest() throws Exception {
        when(itemRequestService.getOwnItemRequest(anyLong()))
                .thenAnswer(u -> {
                    ItemRequest request1 = new ItemRequest(1L,"description1",new User(),
                            LocalDateTime.now(),new ArrayList<>());
                    ItemRequest request2 = new ItemRequest(2L,"description2",new User(),
                            LocalDateTime.now(),new ArrayList<>());
                    return List.of(request1,request2);
                });

        mvc.perform(get("/requests")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id",1)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id",is(1L), Long.class))
                .andExpect(jsonPath("$.[1].id",is(2L), Long.class));
    }

    @Test
    public void getAllItemRequestTest() throws Exception {
        when(itemRequestService.getAllItemRequest(anyLong(),anyLong(),anyLong()))
                .thenAnswer(u -> {
                    ItemRequest request1 = new ItemRequest(1L,"description1",new User(),
                            LocalDateTime.now(),new ArrayList<>());
                    ItemRequest request2 = new ItemRequest(2L,"description2",new User(),
                            LocalDateTime.now(),new ArrayList<>());
                    return List.of(request1,request2);
                });

        mvc.perform(get("/requests/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id",1)
                        .param("from", String.valueOf(0))
                        .param("size",String.valueOf(0))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id",is(1L), Long.class))
                .andExpect(jsonPath("$.[1].id",is(2L), Long.class));
    }

    @Test
    public void getItemRequestTest() throws Exception {
        when(itemRequestService.getItemRequest(anyLong(), anyLong()))
                .thenReturn(new ItemRequest(1L,"description",new User(),
                        LocalDateTime.now(),new ArrayList<>()));

        mvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id",1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1L), Long.class));
    }
}