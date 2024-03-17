package com.egepancaroglu.userreviewservice.controller;

import com.egepancaroglu.userreviewservice.UserReviewServiceApplication;
import com.egepancaroglu.userreviewservice.request.user.UserSaveRequest;
import com.egepancaroglu.userreviewservice.request.user.UserUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author egepancaroglu
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserReviewServiceApplication.class})
class UserControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetUserById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/2")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateUser() throws Exception {

        String requestAsString = """
                {
                  "userName": "testUser",
                  "firstName": "TestName",
                  "lastName": "TestSurname",
                  "email": "test@mail.com",
                  "birthDate": "2000-01-01"
                }""";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateUsers2() throws Exception {

        UserSaveRequest request = new UserSaveRequest("eegeeeee", "Ege", "Pancaroğlu", "egepancaroglu@mail.com", LocalDate.of(1999, 4, 23));

        String requestAsString = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }


    @Test
    void shouldUpdateUser() throws Exception {

        UserUpdateRequest request = new UserUpdateRequest(2L, "updatedUserName", "UpdatedFirstName", "UpdatedLastName", "updatedmail@gmail.com", LocalDate.of(2000, 1, 1));

        String requestAsString = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);

        assertTrue(success);
    }

    @Test
    void shouldActivateUser() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/activate/2")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);

        assertTrue(success);
    }

    @Test
    void shouldDeleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", 5))
                .andExpect(status().isNoContent());
    }


}
