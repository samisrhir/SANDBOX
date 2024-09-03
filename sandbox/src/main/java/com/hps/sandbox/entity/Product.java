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
@Table(name = "sa_product")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String name ;
    private String description ;
    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private List<ProductRelease> productReleases;
}
