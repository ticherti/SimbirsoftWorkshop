package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.to.ResponseRoomDTO;
import com.github.ticherti.simplechat.to.SaveRequestRoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface RoomMapper {
    RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    Room toEntity(ResponseRoomDTO responseRoomDTO);

    Room toEntity(SaveRequestRoomDTO requestRoomDTO);

    ResponseRoomDTO toTO(Room room);

    List<Room> allToEntities(Collection<ResponseRoomDTO> roomDTOs);

    List<ResponseRoomDTO> allToTOs(Collection<Room> rooms);
}
