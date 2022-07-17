package com.root.meter.Requests;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private Long id;
    private String name;
    private String password;
    private String confirmPassword;
}

