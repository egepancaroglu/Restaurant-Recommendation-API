package com.egepancaroglu.userreviewservice.controller;

import com.egepancaroglu.userreviewservice.UserReviewServiceApplication;
import com.egepancaroglu.userreviewservice.request.review.ReviewSaveRequest;
import com.egepancaroglu.userreviewservice.request.review.ReviewUpdateRequest;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author egepancaroglu
 */

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserReviewServiceApplication.class})
class ReviewControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldGetAllReviews() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews")).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetReviewById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews/12")).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateReview() throws Exception {

        String requestAsString = """
                {
                  "comment": "testComment",
                  "rate": 5,
                  "userId": 2,
                  "restaurantId": "1d7794a9-f7f9-4ef0-b3d8-256baf3645ea"
                }""";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateReview2() throws Exception {

        ReviewSaveRequest request = new ReviewSaveRequest("testComment22", (byte) 1, "1d7794a9-f7f9-4ef0-b3d8-256baf3645ea", 2L);

        String requestAsString = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/reviews").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }


    @Test
    void shouldUpdateReview() throws Exception {

        ReviewUpdateRequest request = new ReviewUpdateRequest(3L, "Harika", (byte) 5);

        String requestAsString = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/reviews").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);

        assertTrue(success);
    }

    @Test
    void shouldGetReviewsByRestaurantId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/reviews/with-restaurantId/1d7794a9-f7f9-4ef0-b3d8-256baf3645ea")).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }


}
