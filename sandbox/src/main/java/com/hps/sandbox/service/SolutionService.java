package com.hps.sandbox.service;

import com.hps.sandbox.entity.Solution;
import com.hps.sandbox.repository.SolutionRepository;
import com.hps.sandbox.service.dtos.ProductReleaseDTO;
import com.hps.sandbox.service.dtos.SolutionDto;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import com.hps.sandbox.service.mappers.ProductReleaseMapper;
import com.hps.sandbox.service.mappers.SolutionMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class providing CRUD operations for Solution entities.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SolutionService {
    private final SolutionMapper solutionMapper;
    private final SolutionRepository solutionRepository;
    private final ProductReleaseService productReleaseService;
    private final ProductReleaseMapper productReleaseMapper;
    /**
     * Retrieves a SolutionDto by its ID.
     *
     * @param idSolution The ID of the Solution to retrieve.
     * @return SolutionDto if found, otherwise throw an exception.
     */
    @Transactional(readOnly = true) // avoid hibernate automatic dirty check and snapshot copy
    public SolutionDto getSolutionById(Integer idSolution) throws EntityNotFoundException {
        if(idSolution == null){
            log.error("idSolution is null");
            return null;
        }
        return solutionRepository.findById(idSolution)
                .map(solutionMapper::toModel)
                .orElseThrow(
                        ()-> new EntityNotFoundException("Solution not found with specific id : " + idSolution)
                );
    }

    /**
     * Retrieves a list of all SolutionDto entities.
     *
     * @return List of SolutionDto entities.
     */
    @Transactional(readOnly = true) // avoid hibernate automatic dirty check and snapshot copy
    public List<SolutionDto> getAllSolutions() {
        return solutionRepository.findAll().stream()
                .map(solutionMapper::toModel)
                .toList();
    }

    /**
     * Saves a new SolutionDto entity.
     *
     * @param dto The SolutionDto to be saved.
     * @return SolutionDto of the saved entity.
     */
    @Transactional
    public SolutionDto saveSolution(SolutionDto dto) {
        return solutionMapper.toModel(
                solutionRepository.save(
                        solutionMapper.toEntity(dto)
                )
        );
    }

    /**
     * Updates an existing Solution entity by its ID.
     *
     * @param id            The ID of the Solution to be updated.
     * @param dto  The updated SolutionDto.
     * @return SolutionDto of the updated entity, or null if ID is null or solutions are not equal.
     */
    @Transactional
    public SolutionDto updateSolutionById(Integer id, SolutionDto dto) throws EntityNotFoundException {
        idIsNull(id);
        Solution solution = solutionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Solution Not found in the data base")
        );
        SolutionDto solutionEquality = solutionMapper.toModel(solution);

        if(!solutionEquality.equals(dto)){
            log.error("Solutions are not equal");
            return null;
        }

        solutionMapper.updateSolution(dto, solution);

        return solutionMapper.toModel(
                solutionRepository.save(solution)
        );
    }

    /**
     * Deletes a Solution entity by its ID.
     *
     * @param id The ID of the Solution to be deleted.
     * @return True if deletion is successful, otherwise false.
     */
    @Transactional
    public boolean deleteSolutionById(Integer id) {
        if(id == null){
            log.error("id is null");
            return false;
        }
        solutionRepository.deleteById(id);
        return true;
    }
    @Transactional(readOnly = true) // avoid hibernate automatic dirty check and snapshot copy
    public List<SolutionDto> getByProductReleaseId(Integer id) throws SandboxEntityNotFound {
        idIsNull(id);
        Optional<ProductReleaseDTO> productReleaseDto =  productReleaseService.getProductReleaseById(id);
        return solutionRepository.findByProductRelease_ProductReleaseId(productReleaseDto.get().getProductReleaseId()).
                stream().map(solutionMapper::toModel).toList();
    }
    private void idIsNull(Integer id){
        if(id == null) {
            log.error("id is null");
        }
    }
}

