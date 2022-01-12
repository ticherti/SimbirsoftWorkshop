package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.to.ResponseUserDTO;
import com.github.ticherti.simplechat.to.SaveRequestUserDTO;
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
    User toEntity(ResponseUserDTO responseUserTo);

    @Mappings({
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password")
    })
    User toEntity(SaveRequestUserDTO requestUserTo);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "active", source = "active"),
            @Mapping(target = "startBanTime", source = "startBanTime"),
            @Mapping(target = "endBanTime", source = "endBanTime")
    })
    ResponseUserDTO toTO(User user);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "login", source = "login"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "isBanned", source = "isBanned"),
            @Mapping(target = "startBanTime", source = "startBanTime"),
            @Mapping(target = "endBanTime", source = "endBanTime")
    })
    List<ResponseUserDTO> allToTOs(Collection<User> users);
}
