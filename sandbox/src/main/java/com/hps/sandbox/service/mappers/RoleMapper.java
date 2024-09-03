package com.hps.sandbox.service.mappers;

import com.hps.sandbox.entity.Role;
import com.hps.sandbox.service.dtos.RoleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDto dto);

    RoleDto toModel(Role role);
}
