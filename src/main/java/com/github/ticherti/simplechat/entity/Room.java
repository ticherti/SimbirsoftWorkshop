package com.github.ticherti.simplechat.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "rooms")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"creator", "users", "messages"})
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 3, max = 20)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    //    todo Find out the best way to deal with cascade type Remove
    @ManyToMany(mappedBy = "rooms", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> users;

    // todo Find out if I should only leave Remove here
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room", cascade = CascadeType.ALL)
    private List<Message> messages;
}


