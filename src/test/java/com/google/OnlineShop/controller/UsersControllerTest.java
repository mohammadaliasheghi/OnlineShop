package com.google.OnlineShop.controller;

import com.google.OnlineShop.app.model.UsersModel;
import com.google.OnlineShop.app.service.UsersService;
import com.google.OnlineShop.config.ShoppingConstant;
import com.google.OnlineShop.util.JSON;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class UsersControllerTest {

    @MockBean
    UsersService usersService;
    @Autowired
    MockMvc mockMvc;

    protected UsersModel usersModel;

    @BeforeEach
    void setUp() {
        usersModel = UsersModel.builder()
                .id(ShoppingConstant.ID_TEST)
                .username(ShoppingConstant.USERNAME_TEST)
                .password(ShoppingConstant.PASS_TEST)
                .phone(ShoppingConstant.PHONE_TEST)
                .email(ShoppingConstant.EMAIL_TEST)
                .enabled(ShoppingConstant.ENABLE)
                .build();
    }

    @Test
    void itShouldSaveNewUser() throws Exception {
        //Given
        String input = JSON.convertToJSON(usersModel);
        BDDMockito.given(usersService.findUsersByUsername(usersModel.getUsername())).willReturn(Optional.empty());
        //When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(ShoppingConstant.USER_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON).content(input))
                .andDo(MockMvcResultHandlers.print());
        //Then
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ShoppingConstant.USER_CREATED)));
    }

    @Test
    void itShouldNotSaveNewUser() throws Exception {
        //Given
        String input = JSON.convertToJSON(usersModel);
        BDDMockito.given(usersService.findUsersByUsername(ArgumentMatchers.any())).willReturn(Optional.of(usersModel));
        //When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(ShoppingConstant.USER_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON).content(input))
                .andDo(MockMvcResultHandlers.print());
        //Then
        resultActions.andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ShoppingConstant.USERNAME_CONFLICT)));
    }

    @Test
    void itShouldUpdateExistUser() throws Exception {
        //Given
        String input = JSON.convertToJSON(usersModel);
        BDDMockito.given(usersService.findUsersByUsername(usersModel.getUsername())).willReturn(Optional.empty());
        //When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(ShoppingConstant.USER_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON).content(input))
                .andDo(MockMvcResultHandlers.print());
        //Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ShoppingConstant.USER_UPDATED)));
    }

    @Test
    void itShouldNotUpdateExistUser() throws Exception {
        //Given
        String input = JSON.convertToJSON(usersModel);
        BDDMockito.given(usersService.findUsersByUsername(ArgumentMatchers.any())).willReturn(Optional.of(usersModel));
        //When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put(ShoppingConstant.USER_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON).content(input))
                .andDo(MockMvcResultHandlers.print());
        //Then
        resultActions.andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ShoppingConstant.USERNAME_CONFLICT)));
    }

    @Test
    void itShouldFindExistUser() throws Exception {
        //Given
        BDDMockito.given(usersService.getUsers(usersModel.getId())).willReturn(usersModel);
        //When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.
                        get(ShoppingConstant.USER_CONTEXT + "/" + usersModel.getId()))
                .andDo(MockMvcResultHandlers.print());
        //Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ShoppingConstant.SUCCESS)));
    }

    @Test
    void itShouldDeleteExistUser() throws Exception {
        //Given
        BDDMockito.given(usersService.deleteUsers(usersModel.getId())).willReturn(ShoppingConstant.SUCCESS);
        //When
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.
                        delete(ShoppingConstant.USER_CONTEXT + "/" + usersModel.getId()))
                .andDo(MockMvcResultHandlers.print());
        //Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
