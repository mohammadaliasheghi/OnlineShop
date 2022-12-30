package com.google.OnlineShop.controller;

import com.google.OnlineShop.app.model.UsersModel;
import com.google.OnlineShop.app.service.UsersService;
import com.google.OnlineShop.config.ShoppingConstant;
import com.google.OnlineShop.util.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UsersModel usersModel) {
        if (usersService.findUsersByUsername(usersModel.getUsername()).isEmpty())
            return new ResponseEntity<>(
                    new ResponseDto<>(ShoppingConstant.USER_CREATED,
                            usersService.createUsers(usersModel)),
                    HttpStatus.CREATED);
        else
            return new ResponseEntity<>(
                    new ResponseDto<>(ShoppingConstant.USERNAME_CONFLICT),
                    HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UsersModel usersModel) {
        if (usersService.findUsersByUsername(usersModel.getUsername()).isEmpty())
            return new ResponseEntity<>(
                    new ResponseDto<>(ShoppingConstant.USER_CREATED,
                            usersService.updateUsers(usersModel)),
                    HttpStatus.OK);
        else
            return new ResponseEntity<>(
                    new ResponseDto<>(ShoppingConstant.USERNAME_CONFLICT),
                    HttpStatus.CONFLICT);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return new ResponseEntity<>(new ResponseDto<>(ShoppingConstant.SUCCESS, usersService.getUsers(id)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(new ResponseDto<>(usersService.deleteUsers(id)), HttpStatus.OK);
    }

}
