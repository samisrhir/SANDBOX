package com.hps.sandbox.service;

import com.hps.sandbox.entity.Module;
import com.hps.sandbox.service.dtos.ModuleDto;
import com.hps.sandbox.service.dtos.SolutionDto;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import com.hps.sandbox.service.mappers.ModuleMapper;
import com.hps.sandbox.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final SolutionService solutionService;

    @Transactional(readOnly = true)
    public List<ModuleDto> getAllModules() {
        List<Module> modules = moduleRepository.findAll();
        return modules.stream()
                .map(moduleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ModuleDto> getModuleById(Integer id) {
        Optional<Module> module = moduleRepository.findById(id);
        return module.map(moduleMapper::toDto);
    }

    @Transactional
    public ModuleDto createModule(ModuleDto moduleDto) {
        Module module = moduleMapper.toEntity(moduleDto);
        Module savedModule = moduleRepository.save(module);
        log.info("Module created with ID: " + savedModule.getModuleId());
        return moduleMapper.toDto(savedModule);
    }

    @Transactional
    public ModuleDto updateModule(Integer id, ModuleDto moduleDto) throws SandboxEntityNotFound {
        Module existingModule = moduleRepository.findById(id)
                .orElseThrow(() -> new SandboxEntityNotFound("Module with ID " + id + " not found"));

        moduleMapper.updateModule(moduleDto, existingModule);

        Module updatedModule = moduleRepository.save(existingModule);
        log.info("Module updated with ID: " + updatedModule.getModuleId());
        return moduleMapper.toDto(updatedModule);
    }

    @Transactional
    public void deleteModuleById(Integer id) throws SandboxEntityNotFound {
        if (moduleRepository.existsById(id)) {
            moduleRepository.deleteById(id);
            log.info("Module with ID: " + id + " is deleted");
        } else {
            throw new SandboxEntityNotFound("Module with ID " + id + " was not found");
        }
    }

    @Transactional(readOnly = true)
    public List<ModuleDto> getAllModuleBySolutionId(Integer id) {
        if (id == null) {
            return null;
        }
        Optional<SolutionDto> solution = Optional.ofNullable(solutionService.getSolutionById(id));
        return moduleRepository.findBySolution_SolutionId(solution.get().getSolutionId()).stream()
                .map(moduleMapper::toDto).toList();
    }
}
