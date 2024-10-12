package com.bravos.news.dto;

import lombok.Data;

@Data
public class NewUserCreated {

    private UsersRequest usersRequest;
    private String password;

}
