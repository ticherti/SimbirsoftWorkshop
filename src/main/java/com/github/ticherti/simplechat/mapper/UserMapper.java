package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.to.ResponseUserTo;
import com.github.ticherti.simplechat.to.SaveRequestUserTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "active", source = "active"),
            @Mapping(target = "startBanTime", source = "startBanTime"),
            @Mapping(target = "endBanTime", source = "endBanTime")
    })
    User toEntity(ResponseUserTo responseUserTo);

    @Mappings({
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", source = "role")
    })
    User toEntity(SaveRequestUserTo requestUserTo);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "active", source = "active"),
            @Mapping(target = "startBanTime", source = "startBanTime"),
            @Mapping(target = "endBanTime", source = "endBanTime")
    })
    ResponseUserTo toTO(User user);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "isBanned", source = "isBanned"),
            @Mapping(target = "startBanTime", source = "startBanTime"),
            @Mapping(target = "endBanTime", source = "endBanTime")
    })
    List<ResponseUserTo> allToTOs(Collection<User> users);
}
