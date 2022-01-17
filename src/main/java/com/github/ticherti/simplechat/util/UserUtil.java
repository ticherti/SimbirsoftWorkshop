package com.github.ticherti.simplechat.util;

import com.github.ticherti.simplechat.entity.Permission;
import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.NotPermittedException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserUtil {

    public static void ckeckBan(User user){
        if (user.getEndBanTime().before(Timestamp.valueOf(LocalDateTime.now()))){
            user.setActive(true);
        }
        if (!user.isActive()) {
            throw new NotPermittedException("You are banned");
        }
    }

    public static void checkCreatorAndPermission(User user, Room room){
        ckeckBan(user);
        if (room.getCreator().getId() != user.getId() && !user.getRole().getPermissions().contains(Permission.RENAME_ROOM)){
            throw new NotPermittedException("Not permitted to do so");
        }
    }

    public static void checkEnteredUser(Integer i){
        if (i == null){
            throw new NotPermittedException("Not permitted to do so");
        }
    }
}
