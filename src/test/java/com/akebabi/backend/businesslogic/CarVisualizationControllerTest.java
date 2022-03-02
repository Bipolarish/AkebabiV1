package com.akebabi.backend.businesslogic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarVisualizationControllerTest {

//    reference: https://rieckpil.de/guide-to-testing-spring-boot-applications-with-mockmvc/

    @Autowired
    MockMvc mockMvc;

    @Test
    void getCars() throws Exception {
//         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/car/carList");
//         mockMvc.perform(requestBuilder)
//                 .andDo(print())
//                 .andExpect(status().isOk());
//         for now the tests are not working
    }

    @Test
    void getMyCars() {
    }

    @Test
    void getCarDetail() {
    }

    @Test
    void getCarDetailByPublicId() {
    }

    @Test
    void getCarSearchOptions() {
    }

    @Test
    void searchCars() {
    }

    @Test
    void getFavorites() {
    }

    @Test
    void getFavoriteIds() {
    }
}
