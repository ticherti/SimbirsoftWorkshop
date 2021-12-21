package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.to.ResponseMessageDTO;
import com.github.ticherti.simplechat.to.SaveRequestMessageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface MessageMapper {
    MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    Message toEntity(ResponseMessageDTO responseMessageTo);

    Message toEntity(SaveRequestMessageDTO requestMessageTo);

    ResponseMessageDTO toTO(Message message);

    List<Message> allToEntities(Collection<ResponseMessageDTO> messageTos);

    List<ResponseMessageDTO> allToTOs(Collection<Message> messages);
}
