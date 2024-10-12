package com.bravos.news.entity;

import com.bravos.news.entity.enums.Role;
import com.bravos.news.entity.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String username;
    private String password;
    private String fullName;
    private Date birthDay;
    private Sex sex;
    private String mobile;
    private String email;
    private Role role;

}
