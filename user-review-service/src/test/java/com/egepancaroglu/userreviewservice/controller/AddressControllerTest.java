package com.egepancaroglu.userreviewservice.controller;

import com.egepancaroglu.userreviewservice.UserReviewServiceApplication;
import com.egepancaroglu.userreviewservice.request.address.AddressSaveRequest;
import com.egepancaroglu.userreviewservice.request.address.AddressUpdateRequest;
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
@SpringBootTest(classes = {UserReviewServiceApplication.class})
class AddressControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldGetAllAddresses() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/addresses")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetAddressesById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/addresses/8")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldGetAddressesByUserId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/addresses/with-userId/7")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateAddress() throws Exception {

        String requestAsString = """
                {
                  "city": "testCity",
                  "state": "TestState",
                  "district": "TestDistrict",
                  "street": "testStreet",
                  "location": "45.6542, 45.3223",
                  "userId": 8
                }""";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/addresses").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }

    @Test
    void shouldCreateAddress2() throws Exception {

        AddressSaveRequest request = new AddressSaveRequest("testCity2", "TestState2", "TestDistrict2", "testStreet2", "90.6542, 90.3223", 8L);

        String requestAsString = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/addresses").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        boolean success = isSuccess(mvcResult);
        assertTrue(success);
    }


    @Test
    void shouldUpdateAddress() throws Exception {

        AddressUpdateRequest request = new AddressUpdateRequest(6L, "updatedCity2", "UpdatedState2", "UpdatedDistrict2", "UpdatedStreet2", "45.4334, 24.7643");

        String requestAsString = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/addresses").content(requestAsString).contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        boolean success = isSuccess(mvcResult);

        assertTrue(success);
    }

    @Test
    void shouldDeleteAddressById() throws Exception {
        mockMvc.perform(delete("/api/v1/addresses/{id}", 10))
                .andExpect(status().isNoContent());
    }


}
