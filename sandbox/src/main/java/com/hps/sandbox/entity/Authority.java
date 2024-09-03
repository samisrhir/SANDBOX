package com.hps.sandbox.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "sa_authority")
@AllArgsConstructor
@NoArgsConstructor
public class Authority extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String permission ;
    @Singular
    @JsonIgnore
    @ManyToMany(mappedBy = "authorities",fetch = FetchType.LAZY)
    private Set<Role> roles;

}
