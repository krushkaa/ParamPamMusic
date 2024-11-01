package org.example.parampammusic.securityTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    void contextLoads() {
        assertThat(context).isNotNull();
    }

    @Test
    @WithMockUser(roles = "USER")
    void testUserAccessToUserEndpoint() throws Exception {
        mockMvc.perform(get("/user/someEndpoint"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminAccessToAdminEndpoint() throws Exception {
        mockMvc.perform(get("/admin/someEndpoint"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testPublicAccessToHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void testUnauthorizedAccessToUserEndpoint() throws Exception {
        mockMvc.perform(get("/user/someEndpoint"))
                .andExpect(status().is3xxRedirection()); // Проверяем редирект на страницу логина
    }
}
