package com.hps.sandbox.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer productId;
    private String name;
    private String description;
    private boolean visible;
    private boolean enabled;
}
