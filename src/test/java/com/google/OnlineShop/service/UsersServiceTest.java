package com.google.OnlineShop.service;

import com.google.OnlineShop.app.entity.Users;
import com.google.OnlineShop.app.mapper.UsersMapper;
import com.google.OnlineShop.app.model.UsersModel;
import com.google.OnlineShop.app.repository.UsersRepository;
import com.google.OnlineShop.app.service.UsersService;
import com.google.OnlineShop.config.ShoppingConstant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@SpringBootTest
public class UsersServiceTest {

    @Autowired
    protected UsersService usersService;
    @MockBean
    protected UsersRepository usersRepository;

    protected UsersModel usersModel;

    @BeforeEach
    protected void setUp() {
        usersModel = UsersModel.builder()
                .id(ShoppingConstant.ID_TEST)
                .username(ShoppingConstant.USER_TEST)
                .password(ShoppingConstant.PASS_TEST)
                .phone(ShoppingConstant.PHONE_TEST)
                .email(ShoppingConstant.EMAIL_TEST)
                .enabled(ShoppingConstant.ENABLE)
                .build();
    }

    @Test
    protected void itShouldSaveNewUser() {
        //Given
        Users entity = UsersMapper.get().modelToEntity(usersModel);
        Mockito.when(usersRepository.save(entity)).thenReturn(entity);
        //When
        UsersModel createdModel = usersService.createUsers(usersModel);
        //Then
        Assertions.assertThat(usersModel).isEqualTo(createdModel);
    }

    @Test
    protected void itShouldLoadUserByUserName() {
        //Given
        Users findUser = UsersMapper.get().modelToEntity(usersModel);
        BDDMockito.given(usersRepository.getByUsername(usersModel.getUsername())).willReturn(findUser);
        //When
        UserDetails returnUser = usersService.loadUserByUsername(usersModel.getUsername());
        //Then
        Assertions.assertThat(returnUser).hasNoNullFieldsOrProperties();
    }

    @Test
    protected void itShouldEmptyWhenUserNotFoundByUsername() {
        //Given
        BDDMockito.given(usersRepository.getByUsername(usersModel.getUsername())).willReturn(null);
        //When
        UserDetails returnUser = usersService.loadUserByUsername(usersModel.getUsername());
        //Then
        Assertions.assertThat(returnUser).isEqualTo(null);
    }

    @Test
    protected void itShouldFindUserById() {
        //Given
        Users entity = UsersMapper.get().modelToEntity(usersModel);
        BDDMockito.given(usersRepository.findById(usersModel.getId())).willReturn(Optional.of(entity));
        //When
        Optional<Users> findUser = usersRepository.findById(usersModel.getId());
        //Then
        Assertions.assertThat(findUser).isNotEqualTo(Optional.empty());
    }

    @Test
    protected void itShouldDeleteUserById() {
        //Given ,When
        usersRepository.deleteById(usersModel.getId());
        //Then
        BDDMockito.then(usersRepository).should().deleteById(usersModel.getId());
    }

}
