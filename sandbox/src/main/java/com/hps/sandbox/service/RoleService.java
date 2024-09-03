package com.hps.sandbox.service;

import com.hps.sandbox.repository.RoleRepository;
import com.hps.sandbox.service.dtos.RoleDto;
import com.hps.sandbox.service.mappers.RoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;
    public List<RoleDto> getAllRole(){
        return roleRepository.findAll().stream().map(roleMapper::toModel).toList();
    }
    public RoleDto saveRole(RoleDto roleDto){
        return roleMapper.toModel(roleRepository.save(roleMapper.toEntity(roleDto)));
    }
}
