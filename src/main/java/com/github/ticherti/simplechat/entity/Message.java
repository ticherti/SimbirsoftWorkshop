package com.github.ticherti.simplechat.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(name = "content", nullable = false)
    @NotBlank
    @Size(max = 1000)
    private String content;

    @Column(name = "date_time", updatable = false)
    private Timestamp dateTime = Timestamp.from(Instant.now());


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\nMessage{" +
                "id=" + id +
                ", roomId=" + room.getName() +
                ", userId=" + user.getLogin() +
                ", content='" + content + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
