package com.hps.sandbox.service.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCsvRepresentation {

    @CsvBindByName(column = "fullName")
    private String fullName;
    @CsvBindByName(column = "email")
    private String email;
    @CsvBindByName(column = "userName")
    private String userName;
    @CsvBindByName(column = "password")
    private String password;
    @CsvBindByName(column = "phone")
    private String phone;
    @CsvBindByName(column = "enabled")
    private boolean enabled;

}
