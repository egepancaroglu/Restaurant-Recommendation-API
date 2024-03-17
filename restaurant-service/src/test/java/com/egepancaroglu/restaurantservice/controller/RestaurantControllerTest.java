package com.egepancaroglu.restaurantservice.controller;

import com.egepancaroglu.restaurantservice.RestaurantServiceApplication;
import com.egepancaroglu.restaurantservice.request.RestaurantSaveRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateAverageScoreRequest;
import com.egepancaroglu.restaurantservice.request.RestaurantUpdateRequest;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author egepancaroglu
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {RestaurantServiceApplication.class})
class RestaurantControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldGetAllRestaurants() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurants")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetRestaurantsById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurants/61cc05cb-a5e9-46b4-9942-71f2079e47d1/")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateReview() throws Exception {

        String requestAsString = """
                {
                  "name": "testRestaurant",
                  "location": "45.5433, 42.3456"
                }""";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurants").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateReview2() throws Exception {

        RestaurantSaveRequest request = new RestaurantSaveRequest("deletedtestRestaurant2", "54.4324, 43.5436");

        String requestAsString = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurants").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldUpdateRestaurant() throws Exception {

        RestaurantUpdateRequest request = new RestaurantUpdateRequest("7c318680-2a3f-4a3e-be44-207a36471d5c", "updatedComment", "34.4564, 45.2345");

        String requestAsString = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/restaurants").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);

        assertTrue(success);
    }

    @Test
    void shouldActivateRestaurant() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/restaurants/activate/7c318680-2a3f-4a3e-be44-207a36471d5c")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);

        assertTrue(success);
    }

    @Test
    void updateRestaurantAverageScore() throws Exception {
        RestaurantUpdateAverageScoreRequest updateRequest = new RestaurantUpdateAverageScoreRequest("1d7794a9-f7f9-4ef0-b3d8-256baf3645ea", 5.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/restaurants/averageScore/" + "1d7794a9-f7f9-4ef0-b3d8-256baf3645ea")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.averageScore").value(5.0));
    }


    @Test
    void shouldDeleteRestaurantId() throws Exception {
        mockMvc.perform(delete("/api/v1/restaurants/{id}", "7c318680-2a3f-4a3e-be44-207a36471d5c"))
                .andExpect(status().isNoContent());
    }


}
