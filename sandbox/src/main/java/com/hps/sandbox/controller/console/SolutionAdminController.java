package com.hps.sandbox.controller.console;

import com.hps.sandbox.service.SolutionService;
import com.hps.sandbox.service.dtos.SolutionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("console/solutions")
@CrossOrigin("*")
public class SolutionAdminController {
    private final SolutionService service;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SolutionDto>> getAllSolution() {
        List<SolutionDto> solutions = service.getAllSolutions();
        return ResponseEntity.ok(solutions);
    }
}
