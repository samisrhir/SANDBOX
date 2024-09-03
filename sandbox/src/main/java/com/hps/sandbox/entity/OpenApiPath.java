package com.hps.sandbox.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiPath extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPath;
    private String pathUrl;
    @OneToMany(mappedBy = "openApiPath", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<OpenApiMethod> openApiMethodList;
}
