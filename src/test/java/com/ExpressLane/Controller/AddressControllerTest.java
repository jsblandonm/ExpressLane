package com.ExpressLane.Controller;

import com.ExpressLane.DTO.AddressDTO;
import com.ExpressLane.Service.AddressService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;


import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;


    @Test
    void createAddress() throws Exception {
        AddressDTO addressDTO = new AddressDTO();

        when(addressService.createAddress(addressDTO)).thenReturn(addressDTO);

        mockMvc.perform(post("/api/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(addressDTO)))
                .andExpect(status().isCreated());
        verify(addressService, times(1)).createAddress(addressDTO);
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllAddresses() throws Exception {
        List<AddressDTO> addresses = new ArrayList<>();

        addresses.add(new AddressDTO());

        when(addressService.getAllAdresses()).thenReturn(addresses);

        mockMvc.perform((RequestBuilder) get("/api/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lenght()").value(addresses.size()));

        verify(addressService, times(1)).getAllAdresses();
    }



    @Test
    void getAddressById() {
    }

    @Test
    void updateAddress() {
    }

    @Test
    void deleteAddress() {
    }
}