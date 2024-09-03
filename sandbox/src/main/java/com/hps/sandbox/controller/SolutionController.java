package com.hps.sandbox.controller;

import com.hps.sandbox.service.SolutionService;
import com.hps.sandbox.service.dtos.SolutionDto;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sandbox-ui/solutions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SolutionController {
    private final SolutionService service;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SolutionDto>> getAllSolution() {
        List<SolutionDto> solutions = service.getAllSolutions();
        return ResponseEntity.ok(solutions);
    }
    @GetMapping(value = "/{id}/solution", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SolutionDto>> getAllSolutionByProductReleaseID(@PathVariable Integer id) throws SandboxEntityNotFound {
        List<SolutionDto> solutions = service.getByProductReleaseId(id);
        return ResponseEntity.ok(solutions);
    }
    @PostMapping("/")
    public SolutionDto save(@RequestBody SolutionDto solutionDto){
        return service.saveSolution(solutionDto);
    }
}
