package org.example.parampammusic.controllerTest;

import org.example.parampammusic.controller.ReviewController;
import org.example.parampammusic.service.AudioTrackService;
import org.example.parampammusic.service.ReviewService;
import org.example.parampammusic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private UserService userService;

    @MockBean
    private AudioTrackService audioTrackService;

//    @Test
//    @WithMockUser(username = "user", roles = "USER")
//    public void testGetReviews() throws Exception {
//        mockMvc.perform(get("/reviewList"))
//                .andExpect(status().isOk());
//    }
}
