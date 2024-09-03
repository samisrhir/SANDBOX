package com.hps.sandbox.entity;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "sa_module")
@AllArgsConstructor
@NoArgsConstructor
public class Module extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer moduleId;
    private String name ;
    private String description ;
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution ;

    @OneToMany
    private List<Api> apis ; }

