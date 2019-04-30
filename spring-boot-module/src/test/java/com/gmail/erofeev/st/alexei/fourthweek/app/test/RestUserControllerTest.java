package com.gmail.erofeev.st.alexei.fourthweek.app.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.RoleDTO;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestUserControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void getUsersByAdministrator() throws Exception {
        String url = "/api/v1/users";
        MvcResult mvcResult = mvc.perform(get(url))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        List<UserDTO> users = objectMapper.readValue(content, new TypeReference<List<UserDTO>>() {
        });
        Assert.assertEquals(2, users.size());
    }

    @Test
    @WithMockUser(roles = "Customer")
    public void getUsersByCustomer() throws Exception {
        String url = "/api/v1/users";
        mvc.perform(get(url))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "WRONG_user", password = "password")
    public void getUsersByWrongUser() throws Exception {
        String url = "/api/v1/users";
        mvc.perform(get(url))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "Administrator")
    public void addNewUserByAdministrator() throws Exception {
        UserDTO user = new UserDTO("test_user", "test_password", new RoleDTO(1L, "ROLE_Customer"));
        String json = objectMapper.writeValueAsString(user);
        String url = "/api/v1/users";
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated()).andReturn();
    }
}
