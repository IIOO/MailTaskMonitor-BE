package com.monitor.task.user.persistance;

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
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "role")
    private Set<UserEntity> user;
}
