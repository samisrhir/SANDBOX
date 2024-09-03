package com.hps.sandbox.controller;

import com.hps.sandbox.entity.Api;
import com.hps.sandbox.service.ModuleService;
import com.hps.sandbox.service.dtos.ModuleDto;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("sandbox-ui/modules")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModuleDto> getModuleById(@PathVariable Integer id) throws SandboxEntityNotFound {
        ModuleDto moduleDto = moduleService.getModuleById(id)
                .orElseThrow(() -> new SandboxEntityNotFound("Module with ID " + id + " not found"));
        return ResponseEntity.ok(moduleDto);
    }

    @GetMapping("{moduleId}/apis")
    public List<Api> getApisByModule(@PathVariable Integer moduleId) {
        Optional<ModuleDto> module = moduleService.getModuleById(moduleId);
        return module.orElseThrow(() -> new RuntimeException("Module not found with id: " + moduleId))
                .getApis();
    }

    @GetMapping
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        List<ModuleDto> moduleDtos = moduleService.getAllModules();
        return ResponseEntity.ok(moduleDtos);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ModuleDto> createModule(@RequestBody ModuleDto moduleDto) {
        ModuleDto createdModuleDto = moduleService.createModule(moduleDto);
        return new ResponseEntity<>(createdModuleDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ModuleDto> updateModule(@PathVariable Integer id, @RequestBody ModuleDto moduleDto) throws SandboxEntityNotFound {
        ModuleDto updatedModuleDto = moduleService.updateModule(id, moduleDto);
        return ResponseEntity.ok(updatedModuleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Integer id) throws SandboxEntityNotFound {
        moduleService.deleteModuleById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/modules")
    public List<ModuleDto> getAllModuleBySolutionID(@PathVariable Integer id) {
        return moduleService.getAllModuleBySolutionId(id);
    }
}
