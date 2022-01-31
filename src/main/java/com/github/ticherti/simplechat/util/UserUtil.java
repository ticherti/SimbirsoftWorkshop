package com.github.ticherti.simplechat.util;

import com.github.ticherti.simplechat.entity.Permission;
import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.exception.NotPermittedException;

public class UserUtil {

    public static void checkCreatorAndPermission(User user, Room room, Permission permission){
        if (room.getCreator().getId() != user.getId() && !user.getRole().getPermissions().contains(permission)){
            throw new NotPermittedException("Not permitted to do so");
        }
    }

    public static void checkEnteredUser(Integer i){
        if (i == null){
            throw new NotPermittedException("Not permitted to do so");
        }
    }
}
