package com.hps.sandbox.controller;

import com.hps.sandbox.service.ProductReleaseService;
import com.hps.sandbox.service.dtos.ProductReleaseDTO;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("sandbox-ui/product-releases")
@CrossOrigin("*")
public class ProductReleaseController {

    private final ProductReleaseService productReleaseService;

    @GetMapping
    public ResponseEntity<List<ProductReleaseDTO>> getAllProductsRelease() {
        List<ProductReleaseDTO> productReleaseDTOList = productReleaseService.getAllProductReleases();
        return ResponseEntity.ok(productReleaseDTOList);
    }

    @GetMapping("/{id}")
    public Optional<ProductReleaseDTO> getProductReleaseById(@PathVariable Integer id) throws SandboxEntityNotFound {
        return productReleaseService.getProductReleaseById(id);

    }

    @PostMapping
    public ResponseEntity<ProductReleaseDTO> createProductRelease(@RequestBody ProductReleaseDTO productReleaseDTO) {
        ProductReleaseDTO savedProductReleaseDTO = productReleaseService.createProductRelease(productReleaseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductReleaseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductRelease(@PathVariable Integer id) throws SandboxEntityNotFound {
        productReleaseService.deleteProductReleaseById(id);
        return ResponseEntity.noContent().build();

    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductReleaseDTO> updateProductRelease(
            @PathVariable Integer id, @RequestBody ProductReleaseDTO productReleaseDTO) throws SandboxEntityNotFound {
        ProductReleaseDTO updatedProductReleaseDTO = productReleaseService.updateProductReleaseById(id, productReleaseDTO);
        return ResponseEntity.ok(updatedProductReleaseDTO);
    }
    @GetMapping("/{id}/release")
    public List<ProductReleaseDTO> getAllProductReleaseByProductID(@PathVariable Integer id){
        return productReleaseService.getAllProductReleaseByProductId(id);
    }
}
