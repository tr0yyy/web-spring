package com.fmi.springweb.controller;

import com.fmi.springweb.dto.PlaceBidDto;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.service.BidService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("UnitTests")
public class BidControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private BidController bidController;

    @MockBean
    private BidService bidService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPlaceBid() throws Exception {
        PlaceBidDto placeBidDto = new PlaceBidDto();
        doNothing().when(bidService).placeBid(any(String.class), any(Long.class), any(Float.class));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/core/bid/place")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(placeBidDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        ResponseDto<String> response = convertStringToResponseDto(String.class, responseContent);
        assertTrue(response.success);
    }
}
