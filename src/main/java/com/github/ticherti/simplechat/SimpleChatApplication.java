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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

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


        Room room = new Room();
        room.setCreator(user1);
        room.setName("The Room2");
        room.setPrivate(false);

        room.setUsers(Collections.singletonList(user1));
        user1.setRooms(Collections.singletonList(room));

        Message message = new Message();
        message.setContent("content all content");
        message.setUser(user1);
        message.setRoom(room);

        userRepository.save(user1);
        roomRepository.save(room);
        messageRepository.save(message);
        System.out.println(userRepository.findAll().get(0));
    }
}
