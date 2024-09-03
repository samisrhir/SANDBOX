package com.hps.sandbox.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiMethod extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMethod;
    private String method; // post - get - delete ...
    @Column(columnDefinition = "TEXT")
    private String description;
    private String summary;
    private String tags;
    @ManyToOne
    @JsonIgnore
    private OpenApiPath openApiPath;
}
