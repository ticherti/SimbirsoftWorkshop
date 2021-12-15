package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.to.ResponseUserTo;
import com.github.ticherti.simplechat.to.SaveRequestUserTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    User toEntity(ResponseUserTo responseUserTo);

    User toEntity(SaveRequestUserTo requestUserTo);

    ResponseUserTo toTO(User user);

    List<User> allToEntities(Collection<ResponseUserTo> userTos);

    List<ResponseUserTo> allToTOs(Collection<User> users);
}
