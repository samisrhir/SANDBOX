package com.hps.sandbox.service.mappers;

import com.hps.sandbox.service.dtos.ProductDto;
import com.hps.sandbox.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

     Product toEntity(ProductDto dto);

     ProductDto toModel(Product product);
    @Mapping(target = "productId", ignore = true)
    void updateProduct(ProductDto productDto, @MappingTarget Product product);
}
