package com.google.OnlineShop.controller;

import com.google.OnlineShop.app.model.UsersModel;
import com.google.OnlineShop.app.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody UsersModel usersModel) {
        return new ResponseEntity<>(usersService.createUsers(usersModel), HttpStatus.CREATED);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody UsersModel usersModel) {
        return new ResponseEntity<>(usersService.updateUsers(usersModel), HttpStatus.OK);
    }

    @PostMapping(value = "/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return new ResponseEntity<>(usersService.getUsers(id), HttpStatus.OK);
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(usersService.deleteUsers(id), HttpStatus.OK);
    }

}
