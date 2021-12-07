package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.to.ResponseMessageTo;
import com.github.ticherti.simplechat.to.SaveRequestMessageTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface MessageMapper {
    MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    Message toEntity(ResponseMessageTo responseMessageTo);

    Message toEntity(SaveRequestMessageTo requestMessageTo);

    ResponseMessageTo toTO(Message message);

    List<Message> allToEntities(Collection<ResponseMessageTo> messageTos);

    List<ResponseMessageTo> allToTOs(Collection<Message> messages);
}
