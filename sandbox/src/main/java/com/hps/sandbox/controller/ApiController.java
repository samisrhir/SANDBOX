package com.hps.sandbox.controller;

import com.hps.sandbox.entity.Api;
import com.hps.sandbox.service.ApiService;
import com.hps.sandbox.service.dtos.ApiDto;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sandbox-ui/apis")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping
    public ResponseEntity<List<ApiDto>> getAllApis() {
        List<ApiDto> apis = apiService.getAllApis();
        return ResponseEntity.ok(apis);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiDto> getApiById(@PathVariable Integer id) {
        try {
            ApiDto api = apiService.getApiById(id)
                    .orElseThrow(() -> new SandboxEntityNotFound("API with ID " + id + " not found"));
            return ResponseEntity.ok(api);
        } catch (SandboxEntityNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ApiDto> createApi(@RequestBody ApiDto apiDto) {
        ApiDto createdApi = apiService.createApi(apiDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApi);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDto> updateApi(@PathVariable Integer id, @RequestBody ApiDto apiDto) {
        try {
            ApiDto updatedApi = apiService.updateApi(id, apiDto);
            return ResponseEntity.ok(updatedApi);
        } catch (SandboxEntityNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiById(@PathVariable Integer id) {
        try {
            apiService.deleteApiById(id);
            return ResponseEntity.noContent().build();
        } catch (SandboxEntityNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

}
