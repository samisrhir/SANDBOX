package com.hps.sandbox.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Table(name = "sa_product_release")
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRelease extends BaseEntity{  ///EXTENDS BASE_ENTITY

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer productReleaseId;
    private String description;
    private String name;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product ;
}
