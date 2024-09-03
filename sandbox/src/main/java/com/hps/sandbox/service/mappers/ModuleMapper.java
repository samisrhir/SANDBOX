package com.hps.sandbox.service.mappers;

import com.hps.sandbox.entity.Module;
import com.hps.sandbox.entity.ProductRelease;
import com.hps.sandbox.service.dtos.ModuleDto;
import com.hps.sandbox.service.dtos.ProductReleaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ModuleMapper {

    Module toEntity(ModuleDto dto);

    ModuleDto toDto(Module module);
    @Mapping(target = "moduleId", ignore = true)
    void updateModule(ModuleDto moduleDto, @MappingTarget Module module);

}

