package com.google.OnlineShop.app.service;

import com.google.OnlineShop.app.entity.Users;
import com.google.OnlineShop.app.mapper.UsersMapper;
import com.google.OnlineShop.app.model.UsersModel;
import com.google.OnlineShop.app.repository.UsersRepository;
import com.google.OnlineShop.config.ResourceBundle;
import com.google.OnlineShop.config.ShoppingConstant;
import com.google.OnlineShop.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public Optional<UsersModel> findUsersByUsername(String userName) {
        Optional<Users> users = usersRepository.findUsersByUsername(userName);
        return Optional.ofNullable(UsersMapper.get().entityToModel(users.orElse(null)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersModel> findUser = findUsersByUsername(username);
        if (findUser.isEmpty())
            throw new UsernameNotFoundException(ShoppingConstant.USER_INVALID);
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(findUser.get().getPassword())
                .roles(ShoppingConstant.USER_WRITE_PRIVILEGE)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public UsersModel createUsers(UsersModel usersModel) {
        Users users = UsersMapper.get().modelToEntity(usersModel);
        usersRepository.save(users);
        return UsersMapper.get().entityToModel(users);
    }

    @Transactional(rollbackFor = Exception.class)
    public UsersModel updateUsers(UsersModel usersModel) {
        UsersModel updated;
        if (usersModel.getId() != null) {
            Users users = UsersMapper.get().modelToEntity(usersModel);
            usersRepository.save(users);
            updated = UsersMapper.get().entityToModel(users);
        } else
            updated = new UsersModel();
        return updated;
    }

    @Transactional(readOnly = true)
    public UsersModel getUsers(Long id) {
        return UsersMapper.get().entityToModel(usersRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ResourceBundle.getMessageByKey("USERS"), ResourceBundle.getMessageByKey("ID"), id)));
    }

    @Transactional(rollbackFor = Exception.class)
    public String deleteUsers(Long id) throws Exception {
        if (id.compareTo(ShoppingConstant.ONE_LONG) == 0)
            throw new Exception(ResourceBundle.getMessageByKey("CannotBeDeletedAdminUsers"));
        usersRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ResourceBundle.getMessageByKey("USERS"), ResourceBundle.getMessageByKey("ID"), id));
        usersRepository.deleteById(id);
        return ShoppingConstant.SUCCESS;
    }
}
