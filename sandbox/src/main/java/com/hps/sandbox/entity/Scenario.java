package com.hps.sandbox.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hps.sandbox.utils.JsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Base64;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "sa_scenario")
@AllArgsConstructor
@NoArgsConstructor
public class Scenario extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scenarioId;
    @Column(nullable = false)
    private String name;
    @Convert(converter = JsonConverter.class)
    @Column(name = "scenario_json", nullable = false,columnDefinition = "TEXT")
    private Object scenarioJson;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "api_id")
    private Api api;
}
