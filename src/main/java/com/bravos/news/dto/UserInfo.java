package com.bravos.news.dto;

import com.bravos.news.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private UUID id;
    private String username;
    private String fullName;
    private Role role;

}
