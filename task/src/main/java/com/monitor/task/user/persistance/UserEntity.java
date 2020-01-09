package com.monitor.task.user.persistance;

import com.monitor.task.common.CommonEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity extends CommonEntity implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private String mail;

    @Column(nullable = false)
    private String password;

    @ManyToMany(targetEntity = RoleEntity.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<GrantedAuthority> authorities;


    public UserEntity() {
        this.authorities = new ArrayList<>();
    }


    @Builder
    public UserEntity(String username, String mail, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}
