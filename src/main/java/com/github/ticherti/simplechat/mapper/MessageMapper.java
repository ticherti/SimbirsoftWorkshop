package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.to.ResponseMessageTo;
import com.github.ticherti.simplechat.to.SaveRequestMessageTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "room", source = "room"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "dateTime", source = "dateTime")
    })
    Message toEntity(ResponseMessageTo responseMessageTo);

    @Mappings({
            @Mapping(target = "room", source = "room"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "content", source = "content")
    })
    Message toEntity(SaveRequestMessageTo requestMessageTo);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "room", source = "room"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "dateTime", source = "dateTime")
    })
    ResponseMessageTo toTO(Message message);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "room", source = "room"),
            @Mapping(target = "user", source = "user"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "dateTime", source = "dateTime")
    })
    List<ResponseMessageTo> allToTOs(Collection<Message> messages);
}
