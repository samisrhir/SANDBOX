package com.hps.sandbox.controller.console;

import com.hps.sandbox.service.ProductReleaseService;
import com.hps.sandbox.service.dtos.ProductReleaseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("console/productRelease")
@CrossOrigin("*")
public class ProductReleaseAdminController {

    private final ProductReleaseService productReleaseService;

    @GetMapping
    public ResponseEntity<List<ProductReleaseDTO>> getAllProductsRelease() {
        List<ProductReleaseDTO> productReleaseDTOList = productReleaseService.getAllProductReleases();
        return ResponseEntity.ok(productReleaseDTOList);
    }
}
