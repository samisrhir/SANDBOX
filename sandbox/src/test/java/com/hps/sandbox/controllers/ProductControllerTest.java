package com.hps.sandbox.controllers;

import com.hps.sandbox.service.dtos.ProductDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest extends ApiBaseTest{

    @Test
    @Order(1)
    void createProductOkTest() throws Exception {
        mockMvc.perform(
                        post("/products/")
                                .content(asJsonString(ProductDto.builder().name("product test").description("desc test").build()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    @Order(2)
    void getAllProductsOKTest() throws Exception{
        mockMvc.perform(
                get("/products/")
        ).andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void getProductByIdOKTest() throws Exception {
        mockMvc.perform(
                get("/products/1")
        ).andExpect(status().isOk());
    }
    @Test
    @Order(3)
    void getProductsByNameOkTest() throws Exception {
        mockMvc.perform(
                get("/products/name/prod")
        ).andExpect(status().isOk());
    }
    @Test
    @Order(4)
    void getProductsByNameIs4XXClientErrorTest() throws Exception {
        mockMvc.perform(
                get("/products/name/")
        ).andExpect(status().is4xxClientError());
    }
    @Test
    @Order(5)
    void updateProductByIdOkTest() throws Exception {
        mockMvc.perform(
                put("/products/1")
                        .content(asJsonString(ProductDto.builder().name("product test update").description("desc test update").build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
