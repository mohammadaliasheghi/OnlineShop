package com.google.OnlineShop.config;

public class ShoppingConstant {

    //TEST
    public static final String USERNAME_TEST = "mohammad";
    public static final String PASS_TEST = "123456";
    public static final String PHONE_TEST = "09901234565";
    public static final String EMAIL_TEST = "example@gmail.com";
    public static final Boolean ENABLE = Boolean.TRUE;
    public static final Long ID_TEST = 1000L;
    //END_TEST

    //API_ADDRESS
    public static final String USER_CONTEXT = "/api/users";
    //END_API_ADDRESS

    //MESSAGE
    public static String SUCCESS = "SUCCESS";
    public static final String USER_CREATED = "New User  successfully created";
    public static final String USER_UPDATED = "Exist User  successfully updated";
    public static final String USERNAME_CONFLICT = "The username has already been registered ";
    public static final String TOKEN_URL = "http://localhost:9898/authentication/oauth/token";
    public static final String USER_INVALID = "Incorrect username/password supplied";
    //TODO this role must be created
    public static final String USER_WRITE_PRIVILEGE = "WRITE_PRIVILEGE";
    //END_MESSAGE

    //VALUE
    public static Long ONE_LONG = 1L;
    //END_VALUE
}
