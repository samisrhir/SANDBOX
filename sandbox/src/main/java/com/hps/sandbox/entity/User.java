package com.hps.sandbox.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "sa_user")
@AllArgsConstructor
@NoArgsConstructor
public class    User  extends  BaseEntity implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer userId ;
    @Column(unique = true)
    private  String email;
    private  String password;
    @Column(unique = true,nullable = false)
    private  String username;
    private  String  isActive ;
    private  String phone ;
    @Column(nullable = false)
    private  String fullName;
    @Column(nullable = true)
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_product",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "productId")})
    private Set<Product> products = new HashSet<>();
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    private  boolean enabled ;
    @Builder.Default
    private  boolean accountNonExpired = true;
    @Builder.Default
    private  boolean accountNonLocked = true;
    @Builder.Default
    private  boolean credentialsNonExpired = true;
    @Column(nullable = true)
    private  String validityRangeDate ;
    @Singular
    @Column(nullable = true)
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "userId")},inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")
    })
    private Set<Role> roles = new HashSet<>();


    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

}
