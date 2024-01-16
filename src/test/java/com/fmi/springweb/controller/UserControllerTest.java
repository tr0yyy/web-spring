package com.fmi.springweb.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.springweb.dto.ResponseDto;
import com.fmi.springweb.dto.UpdateAccountDto;
import com.fmi.springweb.dto.UserCarsDetailsDto;
import com.fmi.springweb.dto.UserDto;
import com.fmi.springweb.exceptions.AuthenticationFailedException;
import com.fmi.springweb.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import static com.fmi.springweb.tools.Utils.convertStringToResponseDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("UnitTests")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterAccount() throws Exception {
        UserDto userDto = new UserDto("test", "test", "test");
        doNothing().when(userService).register(userDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        ResponseDto<String> response = convertStringToResponseDto(String.class, responseContent);
        assertTrue(response.success);
        assertNull(response.result);
    }

    @Test
    public void testLoginAccount() throws Exception {
        UserDto userDto = new UserDto("test", "test", "test");
        String bearerToken = "BearerToken";
        when(userService.login(any(UserDto.class))).thenReturn(bearerToken);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        ResponseDto<String> response = convertStringToResponseDto(String.class, responseContent);
        assertTrue(response.success);
        assertEquals(response.result, bearerToken);
    }

    @Test
    public void testFailedLoginAccount() throws Exception {
        UserDto userDto = new UserDto("test", "test", "test");
        when(userService.login(any(UserDto.class))).thenThrow(new AuthenticationFailedException("Test"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        ResponseDto<String> response = convertStringToResponseDto(String.class, responseContent);
        assertFalse(response.success);
        assertNull(response.result);
        assertEquals(response.error, "Test");
    }

    @Test
    public void testUpdateAccount() throws Exception {
        UpdateAccountDto updateAccountDto = new UpdateAccountDto("test","testnou","testnou");
        doNothing().when(userService).updateAccountDetails(updateAccountDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/core/account/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateAccountDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        ResponseDto<String> response = convertStringToResponseDto(String.class, responseContent);
        assertTrue(response.success);
        assertEquals(response.result, "Change successful");
    }

    @Test
    public void testGetUsersCars() throws Exception {
        String username = "testUser";
        UserCarsDetailsDto userCarsDetailsDto = new UserCarsDetailsDto();
        userCarsDetailsDto.username = "test";
        when(userService.getUserProfile(username)).thenReturn(userCarsDetailsDto);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/core/account/profile/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
        ResponseDto<UserCarsDetailsDto> response = convertStringToResponseDto(UserCarsDetailsDto.class, responseContent);
        assertTrue(response.success);
    }

    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}