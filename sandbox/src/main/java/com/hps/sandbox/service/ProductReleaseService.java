package com.hps.sandbox.service;

import com.hps.sandbox.entity.ProductRelease;
import com.hps.sandbox.service.dtos.ProductReleaseDTO;
import com.hps.sandbox.service.mappers.ProductReleaseMapper;
import com.hps.sandbox.repository.ProductReleaseRepository;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductReleaseService {

    private final ProductReleaseRepository productReleaseRepository;
    private final ProductReleaseMapper productReleaseMapper;

    // Get all product releases
    @Transactional(readOnly = true)
    public List<ProductReleaseDTO> getAllProductReleases() {
        List<ProductRelease> productReleases = productReleaseRepository.findAll();
        return productReleases.stream()
                .map(productReleaseMapper::toModel)
                .collect(Collectors.toList());
    }

    // Get by ID
    @Transactional(readOnly = true)
    public Optional<ProductReleaseDTO> getProductReleaseById(Integer id) throws SandboxEntityNotFound {
        Optional<ProductRelease> productRelease = productReleaseRepository.findById(id);
        return productRelease.map(productReleaseMapper::toModel);
    }

    // Create
    @Transactional
    public ProductReleaseDTO createProductRelease(ProductReleaseDTO productReleaseDto) {
        ProductRelease productRelease = productReleaseMapper.toEntity(productReleaseDto);
        ProductRelease savedProductRelease = productReleaseRepository.save(productRelease);
        return productReleaseMapper.toModel(savedProductRelease);
    }

    // Update
    @Transactional
    public ProductReleaseDTO updateProductReleaseById(Integer id, ProductReleaseDTO dto) {

        ProductRelease productRelease = productReleaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductRelease not found in the database"));

        productReleaseMapper.updateProductRelease(dto, productRelease);

        productRelease = productReleaseRepository.save(productRelease);

        return productReleaseMapper.toModel(productRelease);
    }



    @Transactional
    public void deleteProductReleaseById(Integer id) throws SandboxEntityNotFound {
        if (productReleaseRepository.existsById(id)) {
            productReleaseRepository.deleteById(id);
            log.info("Product Release with Id: {} is Deleted", id);
        } else {
            throw new SandboxEntityNotFound("ProductRelease with ID " + id + " was not found");
        }
    }

    @Transactional(readOnly = true)
    public List<ProductReleaseDTO> getAllProductReleaseByProductId(Integer id) {
        return productReleaseRepository.findByProductId(id).stream()
                .map(productReleaseMapper::toModel)
                .collect(Collectors.toList());
    }
}
