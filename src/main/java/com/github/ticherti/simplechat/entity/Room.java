package com.github.ticherti.simplechat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rooms")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class Room implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    @Size(min = 3, max = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    //    @ManyToMany(mappedBy = "rooms", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @ManyToMany(mappedBy = "rooms", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> users;

    @Column(name = "is_private")
    private boolean isPrivate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\nRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}


