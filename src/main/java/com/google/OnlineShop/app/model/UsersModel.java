package com.google.OnlineShop.app.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UsersModel")
public class UsersModel {

    private Long id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Boolean enabled;
}
