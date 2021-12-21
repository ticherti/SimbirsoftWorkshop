package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.to.ResponseUserDTO;
import com.github.ticherti.simplechat.to.SaveRequestUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User toEntity(ResponseUserDTO responseUserDTO);

    User toEntity(SaveRequestUserDTO requestUserDTO);

    ResponseUserDTO toTO(User user);

    List<User> allToEntities(Collection<ResponseUserDTO> userDTOs);

    List<ResponseUserDTO> allToTOs(Collection<User> users);
}
