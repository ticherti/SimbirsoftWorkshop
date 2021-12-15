package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.to.ResponseMessageTo;
import com.github.ticherti.simplechat.to.SaveRequestMessageTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toEntity(ResponseMessageTo responseMessageTo);

    Message toEntity(SaveRequestMessageTo requestMessageTo);

    ResponseMessageTo toTO(Message message);

    List<Message> allToEntities(Collection<ResponseMessageTo> messageTos);

    List<ResponseMessageTo> allToTOs(Collection<Message> messages);
}
