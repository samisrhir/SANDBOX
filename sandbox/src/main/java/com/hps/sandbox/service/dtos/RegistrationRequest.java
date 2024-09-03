package com.hps.sandbox.service.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {
    private  String email;
    private  String password;
    private  String username;
    private String  fullName;
    private  String phone ;
}
