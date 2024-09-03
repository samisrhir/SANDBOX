package com.hps.sandbox.service;

import com.hps.sandbox.entity.Solution;
import com.hps.sandbox.service.dtos.ProductReleaseDTO;
import com.hps.sandbox.service.dtos.SolutionDto;
import com.hps.sandbox.service.mappers.SolutionMapper;
import org.mockito.Mockito;
import com.hps.sandbox.repository.SolutionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SolutionServiceTest {
    @Autowired
    private SolutionService service;
    @MockBean
    private SolutionRepository solutionRepository;


    @Test
    void getAllSolutions() {
        Mockito.when(solutionRepository.findAll()).thenReturn((List<Solution>) List.of(
                Solution.builder().name("solution1").build(),
                Solution.builder().name("solution2").build()
        ));
        assertEquals(2,service.getAllSolutions().size());
    }

    @Test
    void saveSolution() {
        SolutionDto solution = SolutionDto.builder().name("solution").productRelease(ProductReleaseDTO.builder().build()).build();
        Mockito.when(service.saveSolution(solution)).thenReturn(solution);
        assertNotNull(solution);
    }

}