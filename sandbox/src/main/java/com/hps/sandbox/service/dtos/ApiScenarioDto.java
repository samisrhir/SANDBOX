package com.hps.sandbox.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ApiScenarioDto {
    private Integer scenarioId;
    private String name;
    private String body;
}
