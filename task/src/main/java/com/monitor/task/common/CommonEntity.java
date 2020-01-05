package com.monitor.task.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class CommonEntity {
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;

    @PrePersist
    void onCreate() {
        creationDate = LocalDateTime.now();
    }
    @PreUpdate
    void onUpdate() {
        modificationDate = LocalDateTime.now();
    }
}
