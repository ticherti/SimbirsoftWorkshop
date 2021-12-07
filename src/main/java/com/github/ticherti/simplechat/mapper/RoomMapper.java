package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.to.ResponseRoomTo;
import com.github.ticherti.simplechat.to.SaveRequestRoomTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface RoomMapper {
    RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    Room toEntity(ResponseRoomTo responseRoomTo);

    Room toEntity(SaveRequestRoomTo requestRoomTo);

    ResponseRoomTo toTO(Room room);

    List<Room> allToEntities(Collection<ResponseRoomTo> roomTos);

    List<ResponseRoomTo> allToTOs(Collection<Room> rooms);
}
