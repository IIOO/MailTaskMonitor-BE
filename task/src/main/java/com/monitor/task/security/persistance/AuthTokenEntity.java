package com.monitor.task.security.persistance;

import com.monitor.task.user.persistance.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "auth_tokens")
@NoArgsConstructor
@Getter
@Setter
public class AuthTokenEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "last_access_time", nullable = false)
    private LocalDateTime lastAccessTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @PrePersist
    public void onPrePresist() {
        this.lastAccessTime = LocalDateTime.now();
    }

    @Builder
    public AuthTokenEntity(UserEntity user) {
        this.user = user;
    }
}
