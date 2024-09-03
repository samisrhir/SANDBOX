package com.hps.sandbox.service.mappers;


import com.hps.sandbox.entity.Api;
import com.hps.sandbox.service.dtos.ApiDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ScenarioMapper.class)
public interface ApiMapper {

    Api toEntity(ApiDto dto);

    @Mapping(target = "scenarioList", source = "scenarios")
    ApiDto toDto(Api api);
    @Mapping(target = "apiId", ignore = true)//generated ID should not be updated manually.
    void updateApi(ApiDto apiDto,@MappingTarget Api api);
}
