package com.github.ticherti.simplechat;

import com.github.ticherti.simplechat.entity.Message;
import com.github.ticherti.simplechat.entity.Role;
import com.github.ticherti.simplechat.entity.Room;
import com.github.ticherti.simplechat.entity.User;
import com.github.ticherti.simplechat.repository.MessageRepository;
import com.github.ticherti.simplechat.repository.RoomRepository;
import com.github.ticherti.simplechat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class SimpleChatApplication implements CommandLineRunner {
    UserRepository userRepository;
    MessageRepository messageRepository;
    RoomRepository roomRepository;

    @Autowired
    public SimpleChatApplication(UserRepository userRepository, MessageRepository messageRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleChatApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setLogin("login");
        user1.setPassword("password");
        user1.setRole(Role.moderator);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        user2.setRole(Role.moderator);

        Room room = new Room();
        room.setCreator(user1);
        room.setName("The Room2");
        room.setPrivate(false);
        Room room3 = new Room();
        room3.setCreator(user2);
        room3.setName("The Room3");
        room3.setPrivate(false);


        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        rooms.add(room3);

        room.setUsers(Collections.singletonList(user1));
        room3.setUsers(users);
        user1.setRooms(Collections.singletonList(room));
        user2.setRooms(rooms);

        Message message = new Message();
        message.setContent("content all content");
        message.setUser(user1);
        message.setRoom(room);

        userRepository.save(user1);
//        userRepository.save(user2);
        roomRepository.save(room);
//        roomRepository.save(room3);
        messageRepository.save(message);
        userRepository.findAll().forEach(System.out::println);
        System.out.println("__________");
        roomRepository.findAll().forEach(System.out::println);
        System.out.println("_______deleting________");

//userRepository.deleteById(1L);
    }
}
