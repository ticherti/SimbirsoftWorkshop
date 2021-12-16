package com.github.ticherti.simplechat.mapper;

import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.to.ResponseRoomTo;
import com.github.ticherti.simplechat.to.SaveRequestRoomTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "users", source = "users"),
            @Mapping(target = "private", source = "private")
    })
    Room toEntity(ResponseRoomTo responseRoomTo);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "private", source = "private")
    })
    Room toEntity(SaveRequestRoomTo requestRoomTo);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "userId", source = "creator.id"),
            @Mapping(target = "users", source = "users"),
            @Mapping(target = "private", source = "private")
    })
    ResponseRoomTo toTO(Room room);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "users", source = "users"),
            @Mapping(target = "isPrivate", source = "isPrivate")
    })
    List<ResponseRoomTo> allToTOs(Collection<Room> rooms);
}
