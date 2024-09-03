package com.hps.sandbox.service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDTO {
    private String startDate;
    private boolean enabled;
    private String endDate;
    private String phone;
    private  String isActive;
    private String email;
    private String fullName;

}
