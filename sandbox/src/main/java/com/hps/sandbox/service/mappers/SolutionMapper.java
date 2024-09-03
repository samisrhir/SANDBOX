package com.hps.sandbox.service.mappers;

import com.hps.sandbox.entity.Solution;
import com.hps.sandbox.service.dtos.SolutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SolutionMapper {
    Solution toEntity(SolutionDto dto);

    SolutionDto toModel(Solution solution);

    @Mapping(target = "solutionId", ignore = true)
    void updateSolution(SolutionDto solutionDto, @MappingTarget Solution solution);
}
