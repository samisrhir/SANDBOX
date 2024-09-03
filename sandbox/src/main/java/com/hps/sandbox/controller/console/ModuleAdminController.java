package com.hps.sandbox.controller.console;

import com.hps.sandbox.service.ModuleService;
import com.hps.sandbox.service.dtos.ModuleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("console/modules")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ModuleAdminController {

    private final ModuleService moduleService;

    @GetMapping
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        List<ModuleDto> moduleDtos = moduleService.getAllModules();
        return ResponseEntity.ok(moduleDtos);
    }
}
