package com.github.ticherti.simplechat.entity;

import lombok.*;

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
@ToString(exclude = {"rooms", "messages"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "login", unique = true, nullable = false)
    @Size(min = 3, max = 30)
    private String login;

    @Column(name = "password", nullable = false)
    @Size(min = 6, max = 20)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "room_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Message> messages;

    @Column(name = "is_banned")
    private boolean isBanned;

    @Column(name = "start_ban_time")
    private Timestamp startBanTime;

    @Column(name = "end_ban_time")
    private Timestamp endBanTime;
}
