package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "room.id", source = "roomId"),
            @Mapping(target = "user.id", source = "userId"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "creationDateTime", source = "creationDateTime")
    })
    Message toEntity(ResponseMessageDTO responseMessageTo);

    @Mappings({
            @Mapping(target = "content", source = "content")
    })
    Message toEntity(SaveRequestMessageDTO requestMessageTo);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "roomId", source = "room.id"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "creationDateTime", source = "creationDateTime")
    })
    ResponseMessageDTO toTO(Message message);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "roomId", source = "room.id"),
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "content", source = "content"),
            @Mapping(target = "creationDateTime", source = "creationDateTime")
    })
    List<ResponseMessageDTO> allToTOs(Collection<Message> messages);

    ResponseMessageDTO requestToResponce(SaveRequestMessageDTO saveRequestMessageDTO);
}
