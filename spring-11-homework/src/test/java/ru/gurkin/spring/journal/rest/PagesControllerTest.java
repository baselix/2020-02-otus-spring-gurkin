package ru.gurkin.spring.journal.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.model.UserRole;
import ru.gurkin.spring.journal.security.JournalUserDetailService;
import ru.gurkin.spring.journal.service.JournalUserService;
import ru.gurkin.spring.journal.service.PostService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(PostController.class)
class PagesControllerTest {

//    @MockBean
//    private PostService postService;
//
//    @MockBean
//    private JournalUserService userService;
//
//    @MockBean
//    private JournalUserDetailService userDetailService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @WithMockUser(
//            username = "user1",
//            authorities = {"USER"}
//    )
//
//    @Test
//    void testAuthenticatedOnAdmin() throws Exception {
//        mockMvc.perform(get("/posts"))
//                .andExpect(status().isOk());
//    }
}
