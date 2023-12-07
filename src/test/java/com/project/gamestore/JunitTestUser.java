package com.project.gamestore;

import com.project.gamestore.domain.User;
import com.project.gamestore.domain.UserRepository;
import com.project.gamestore.dto.UserInfo;
import com.project.gamestore.dto.UserSignUp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.project.gamestore.TestUtils.asJsonString;
import static com.project.gamestore.TestUtils.fromJsonString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JunitTestUser {
    @Autowired
    private MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    User testUser = new User("Pablo", "Marmol", "test1@csumb.edu", "secret", "USER");

    @Test
    public void TestInsertUser() throws Exception {
        MockHttpServletResponse response;

        response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/signup")
                        .content(asJsonString(testUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertTrue(fromJsonString(response.getContentAsString(), Boolean.class));

        User actualUser =  userRepository.findByEmail("test1@csumb.edu");
        assertEquals(actualUser.getFirstName(), testUser.getFirstName());
        assertEquals(actualUser.getLastName(), testUser.getLastName());
        assertEquals(actualUser.getPassword(), testUser.getPassword());
        assertEquals(actualUser.getRole(), testUser.getRole());

        userRepository.deleteById(actualUser.getId());
    }

    @Test
    public void TestInvalidInsertUser() throws Exception {
        MockHttpServletResponse response;
        User invalidUser = testUser;
        invalidUser.setEmail("test@csumb.edu");

        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/signup")
                                .content(asJsonString(invalidUser))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(400, response.getStatus());
        assertFalse(fromJsonString(response.getContentAsString(), Boolean.class));
    }

    @Test
    public void TestGetUserInfo() throws Exception {
        MockHttpServletResponse response;

        response = mvc.perform(
                        MockMvcRequestBuilders
                                .post("/signup")
                                .content(asJsonString(testUser))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());
        assertTrue(fromJsonString(response.getContentAsString(), Boolean.class));

        response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/user/test1@csumb.edu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertEquals(200, response.getStatus());

        UserInfo actualUserInfo = fromJsonString(response.getContentAsString(), UserInfo.class);
        assertEquals(actualUserInfo.email(), testUser.getEmail());
        assertEquals(actualUserInfo.firstName(), testUser.getFirstName());
        assertEquals(actualUserInfo.lastName(), testUser.getLastName());

        userRepository.deleteById(actualUserInfo.id());

    }
}
