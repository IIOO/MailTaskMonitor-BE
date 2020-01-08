package com.monitor.task.user.persistance;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "role", unique = true, nullable = false)
    private String role;

    @Override
    @Transient
    public String getAuthority() {
        return this.role;
    }

    @Builder
    public RoleEntity(String role) {
        this.role = role;
    }
}
