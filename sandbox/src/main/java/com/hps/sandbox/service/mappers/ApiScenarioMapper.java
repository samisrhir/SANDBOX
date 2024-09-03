package com.hps.sandbox.service.mappers;

import com.hps.sandbox.entity.ApiScenario;
import com.hps.sandbox.service.dtos.ApiScenarioDto;
import org.mapstruct.MappingTarget;

public interface ApiScenarioMapper {
    ApiScenario toEntity(ApiScenarioDto dto);
    ApiScenarioDto toDto(ApiScenario apiScenario);
    void updateApiScenario(ApiScenarioDto apiScenarioDto,@MappingTarget ApiScenario apiScenario);
}
