package com.bravos.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequest {

    private String type;
    private String id;
    private String username;
    private String fullName;
    private String email;
    private String mobile;
    private Date dob;
    private String sex;
    private String role;

}
