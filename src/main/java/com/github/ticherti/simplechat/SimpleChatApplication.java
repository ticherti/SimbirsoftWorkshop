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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setLogin("login test");
        user1.setPassword("password");
        user1.setRole(Role.moderator);
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        user2.setRole(Role.moderator);

        Room room = new Room();
        room.setCreator(user1);
        room.setName("The Room2 and test");
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
//        user1.setRooms(Collections.singletonList(room));
//        user2.setRooms(rooms);

        Message message1 = new Message();
        message1.setContent("content all content");
        message1.setUser(user1);
        message1.setRoom(room);
        Message message2 = new Message();
        message2.setContent("content all content and more");
        message2.setUser(user1);
        message2.setRoom(room3);
        Message message3 = new Message();
        message3.setContent("content all content and more for message3");
        message3.setUser(user2);
        message3.setRoom(room);
        userRepository.save(user1);
        userRepository.save(user2);
        roomRepository.save(room3);
        roomRepository.save(room);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);


        userRepository.findAll().forEach(System.out::println);
        System.out.println("__________");
        roomRepository.findAll().forEach(System.out::println);
        System.out.println("_______deleting________");
        System.out.println(userRepository.findById(1L));
        userRepository.deleteById(1L);
        System.out.println(userRepository.findById(1L));
    }
}
