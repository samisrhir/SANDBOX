package com.hps.sandbox.service.dtos;

import com.hps.sandbox.entity.OpenApiHeader;
import com.hps.sandbox.entity.OpenApiPath;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiDto {
    private Integer apiId;
    private String name ;
    private String description;
    private String link;
    private List<ScenarioDto> scenarioList;
    private OpenApiHeader openApiHeader;
    private OpenApiPath openApiPath;
}
