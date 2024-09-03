package com.hps.sandbox.service.mappers;


import com.hps.sandbox.entity.Scenario;
import com.hps.sandbox.service.dtos.ScenarioDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScenarioMapper {
    Scenario toEntity(ScenarioDto dto);
    ScenarioDto toDto(Scenario scenario);
}
