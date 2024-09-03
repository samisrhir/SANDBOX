package com.hps.sandbox.service;

import com.hps.sandbox.entity.Product;
import com.hps.sandbox.entity.ProductRelease;
import com.hps.sandbox.repository.ProductRepository;
import com.hps.sandbox.service.dtos.ProductDto;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import com.hps.sandbox.service.mappers.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class providing CRUD operations for Product entities.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    /**
     * Retrieves a ProductDto by its ID.
     *
     * @param idProduct The ID of the Product to retrieve.
     * @return ProductDto if found, otherwise throw an exception.
     */
    @Transactional(readOnly = true) // avoid hibernate automatic dirty check and snapshot copy
    public ProductDto getProductById(Integer idProduct) throws EntityNotFoundException {
        if(idProduct == null){
            log.error("idProduct is null");
            return null;
        }
        return productRepository.findById(idProduct)
                .map(productMapper::toModel)
                .orElseThrow(
                        ()-> new EntityNotFoundException("product not found with specific id : " + idProduct)
                );
    }
    /**
     * Retrieves a list of all ProductDto entities.
     *
     * @return List of ProductDto entities.
     */
    @Transactional(readOnly = true) // avoid hibernate automatic dirty check and snapshot copy
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toModel)
                .toList();
    }
    /**
     * Saves a new ProductDto entity.
     *
     * @param dto The ProductDto to be saved.
     * @return ProductDto of the saved entity.
     */
    @Transactional
    public ProductDto saveProduct(ProductDto dto) {
        return productMapper.toModel(
                productRepository.save(
                        productMapper.toEntity(dto)
                )
        );
    }
    /**
     * Updates an existing Product entity by its ID.
     *
     * @param id            The ID of the Product to be updated.
     * @param dto  The updated ProductDto.
     * @return ProductDto of the updated entity, or null if ID is null or products are not equal.
     */
    @Transactional
    public ProductDto updateProductById(Integer id,ProductDto dto) throws EntityNotFoundException {
        if(id == null){
            log.error("id is null");
            return null;
        }

        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product Not found in the data base")
        );
        /* update product using mapper */
        productMapper.updateProduct(dto,product);

        return productMapper.toModel(
                productRepository.save(
                        product
                )
        );
    }
    /**
     * Deletes a Product entity by its ID.
     *
     * @param id The ID of the Product to be deleted.
     * @return True if deletion is successful, otherwise false.
     */
    public void deleteProductById(Integer id){
        productRepository.deleteById(id);
            log.info("Product Release with Id: {} is Deleted", id);
        }

    /**
     * Retrieves a list of ProductDto entities by their name, ignoring case.
     *
     * @param name The name of the Product entities to retrieve.
     * @return List of ProductDto entities matching the provided name, or an empty list if none are found.
     */
    @Transactional(readOnly = true) // avoid hibernate automatic dirty check and snapshot copy
    public List<ProductDto> getProductsByName(String name){
        return productRepository.findProductByNameIgnoreCase(name)
                .stream().map(productMapper::toModel)
                .toList();
    }
}
