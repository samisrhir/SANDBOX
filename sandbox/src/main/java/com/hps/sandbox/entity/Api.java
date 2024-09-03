package com.hps.sandbox.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "sa_api")
@AllArgsConstructor
@NoArgsConstructor
public class Api extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apiId;
    private String name ;
    private String description;
    private String link;
    @OneToMany(mappedBy = "api", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Scenario> scenarios;
    @OneToOne
    private OpenApiHeader openApiHeader;
    @OneToOne
    private OpenApiPath openApiPath;
}
