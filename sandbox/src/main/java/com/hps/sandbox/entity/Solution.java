package com.hps.sandbox.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "sa_solution")
@AllArgsConstructor
@NoArgsConstructor
public class Solution extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer solutionId;
    private String name ;
    private String description ;
    private String imageUrl;
    @Column(nullable = true)
    private Boolean latestVersion ;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "saproductReleaseId", nullable = false)
    private ProductRelease productRelease ;
   @JsonIgnore
    @OneToMany(mappedBy = "solution", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Module> modules;
}

