package com.hps.sandbox.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDto {
    private Integer solutionId;
    private String name;
    private String imageUrl;
    private String description;
    private boolean enabled;
    private boolean visible;
    private ProductReleaseDTO productRelease;
}
