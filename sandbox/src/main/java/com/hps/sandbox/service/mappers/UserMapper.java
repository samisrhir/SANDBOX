package com.hps.sandbox.service.mappers;


import com.hps.sandbox.entity.User;
import com.hps.sandbox.service.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto dto);
    @Mapping(target = "isActive", expression = "java(user.isEnabled() ? \"Active account\" : \"Inactive account\")")
    @Mapping(expression = "java(formatDate(user.getCreatedDate()))",target="createdDate")
    UserDto toModel(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdDate", ignore = true )
    @Mapping(target = "products", ignore = true)
    void updateUser(UserDto userDto, @MappingTarget User user);

     default String formatDate(Date date){
       return new SimpleDateFormat("yyyy-MM-dd").format(date) ;
    }

}
