package com.gmail.erofeev.st.alexei.fourthweek.app.test;

import com.gmail.erofeev.st.alexei.fourthweek.controller.UserController;
import com.gmail.erofeev.st.alexei.fourthweek.repository.model.Role;
import com.gmail.erofeev.st.alexei.fourthweek.service.UserService;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Mock
    private UserService userService;
    private UserController controller;
    private MockMvc mvc;

    @Before
    public void setup() {
//        controller = new UserController(userService);
//        Role userRole = new Role(1L, "USER");
//        Role adminRole = new Role(2L, "ADMIN");
//        UserDTO user = new UserDTO(1L, "user1", "$2a$10$uKP6jODKQWNbYmXCRACFt./YOXznRtgHak1Jz4hPjCyNHxnfFddCu", userRole, false);
//        UserDTO admin = new UserDTO(2L, "admin1", "$2a$10$oIlbs.ocfS6ttFMxkMir4uQPLukjDGUpfaR7ytuuz5LjvE5zfAmPa", userRole, false);
//        when(userService.findUserByName("USER")).thenReturn(user);
//        when(userService.findUserByName("ADMIN")).thenReturn(admin);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void shouldSucceedWith200ForLoginPage() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSucceedWith200ForHomePage() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldSucceedWith200ForItemsPage() throws Exception {
        mvc.perform(get("/items"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldSucceedWith200ForUsersPage() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test(expected = RuntimeException.class)
    @WithMockUser(roles = "UNKNOWN")
    public void shouldThrowExceptionForUnknownUser() throws Exception {
        mvc.perform(get("/users"));
    }

}
