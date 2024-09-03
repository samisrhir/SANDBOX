package com.hps.sandbox.service.dtos;

import com.hps.sandbox.entity.Product;
import com.hps.sandbox.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String email;
    private String password;
    private String username;
    private String phone;
    private boolean enabled;
    private String isActive;
    private String fullName;
    @Column(nullable=true)
    private Set<Role> roles;
    private Set<Product> products;
    private String createdDate;
    private String validityRangeDate;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    public Set<Product> getProducts() {
        return this.products;
    }
}
