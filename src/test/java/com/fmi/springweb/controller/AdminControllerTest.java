package com.fmi.springweb.controller;

import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.dto.StartAuctionDto;
import com.fmi.springweb.service.AuctionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.fmi.springweb.tools.Utils.asJsonString;
import static com.fmi.springweb.tools.Utils.convertStringToResponseDto;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("UnitTests")
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AuctionService auctionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStartAuction() throws Exception {
        StartAuctionDto startAuctionDto = new StartAuctionDto();
        doNothing().when(auctionService).startAuctionForCar(startAuctionDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/auction/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(startAuctionDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        ResponseDto<String> response = convertStringToResponseDto(String.class, responseContent);
        assertTrue(response.success);
    }
}