package com.github.ticherti.simplechat.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"rooms", "messages", "createdRooms"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "login", unique = true, nullable = false)
    @Size(min = 3, max = 30)
    private String login;

    @Column(name = "password", nullable = false)
    @Size(min = 6, max = 90)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault(value = "USER")
    private Role role;

    //    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//    @JoinTable(name = "room_user",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "room_id")
//    )
    private List<Room> rooms;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE, orphanRemoval = false)
    private List<Room> createdRooms;

    //    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;

    @Column(name = "start_ban_time")
    private Timestamp startBanTime;

    @Column(name = "end_ban_time")
    private Timestamp endBanTime;
}
