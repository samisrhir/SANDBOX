package com.hps.sandbox.service.dtos;


import com.hps.sandbox.entity.Api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto {

    private Integer moduleId;
    private String name;
    private String description;
    private boolean enabled;
    private String imagePath;
    private boolean visible;
    private SolutionDto solution;
   private List<Api> apis;

}
