package com.hps.sandbox.service.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters setters
@Builder // for building objects using .build
@AllArgsConstructor
@NoArgsConstructor
public class ProductReleaseDTO {
    private Integer productReleaseId;
    @NotEmpty(message = "Description required")
    private String description ;
    @NotEmpty(message = "name required")
    private String name ;
    private boolean enabled;
    private boolean visible;


}
