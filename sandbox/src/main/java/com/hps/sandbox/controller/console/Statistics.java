package com.hps.sandbox.controller.console;

import com.hps.sandbox.service.ModuleService;
import com.hps.sandbox.service.ProductService;
import com.hps.sandbox.service.UserService;
import com.hps.sandbox.service.dtos.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("console/statistics")
@RequiredArgsConstructor
@CrossOrigin("*")
public class Statistics {
    private final UserService userService;
    private final ProductService productService;
    private final ModuleService moduleService;
    @GetMapping("/")
    public int countProducts(){
        return productService.getAllProducts().size();
    }
    @GetMapping("/activeProduct")
    public long countActiveProducts(){
        return productService.getAllProducts().stream().filter(ProductDto::isEnabled).count();
    }
    @GetMapping("/users")
    public long countUsers(){
        return userService.getAllUsers().size();
    }

    @GetMapping("/module")
    public int countModule(){
        return moduleService.getAllModules().size();
    }
}
