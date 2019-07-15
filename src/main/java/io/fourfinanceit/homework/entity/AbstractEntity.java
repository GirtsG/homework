package io.fourfinanceit.homework.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @ReadOnlyProperty
    private Long id;

    @CreatedDate
    @ReadOnlyProperty
    private LocalDateTime createdTime;

    @LastModifiedDate
    @ReadOnlyProperty
    private LocalDateTime lastModifiedTime;
}
