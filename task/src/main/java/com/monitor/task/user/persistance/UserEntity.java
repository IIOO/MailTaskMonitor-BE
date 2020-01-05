package com.monitor.task.user.persistance;

import com.monitor.task.common.CommonEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends CommonEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String surname;

    private String mail;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private RoleEntity role;

    @Builder
    public UserEntity(String name, String surname, String mail, String password, RoleEntity role) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password = password;
        this.role = role;
    }
}
