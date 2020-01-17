package com.monitor.task.user.persistance;

import com.monitor.task.user.Role;
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
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    @Transient
    public String getAuthority() {
        return this.role.toString();
    }

    @Builder
    public RoleEntity(Role role) {
        this.role = role;
    }
}
