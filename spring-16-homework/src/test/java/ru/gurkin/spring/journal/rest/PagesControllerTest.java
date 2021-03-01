package ru.gurkin.spring.journal.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.gurkin.spring.journal.model.JournalUser;
import ru.gurkin.spring.journal.model.UserRole;
import ru.gurkin.spring.journal.security.JournalUserDetailService;
import ru.gurkin.spring.journal.service.JournalUserService;
import ru.gurkin.spring.journal.service.PostService;
import ru.gurkin.spring.journal.service.impl.SequenceGeneratorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@DirtiesContext
@WebMvcTest(PagesController.class)
class PagesControllerTest {

    @MockBean
    private PostService postService;

    @MockBean
    private JournalUserService userService;

    @MockBean
    private JournalUserDetailService userDetailService;

    @MockBean
	private MutableAclService mutableAclService;
	@MockBean
	private SequenceGeneratorService sequenceGenerator;

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(
            username = "user1",
            authorities = {"USER"}
    )

    @Test
    void testAuthenticatedOnAdmin() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }
}
