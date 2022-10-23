package com.micro.show.dto;

import lombok.Data;

@Data
public class LoginFormDTO {
    private String phone;
    private String code;
    private String password;
}
