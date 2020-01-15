package com.monitor.task.business.persistance;

import com.monitor.task.user.persistance.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
public class CompanyEntity {
    @Id
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserEntity user;

    @Builder
    public CompanyEntity(Long id, String name, UserEntity user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }
}
