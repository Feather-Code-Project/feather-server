package com.feathercode.domain.common.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private String createdAt;

    @LastModifiedDate
    @Column(updatable = true)
    private String modifiedAt;

    @PrePersist
    public void onPrePersist() {
        String parsedCreateDate = getCustomFormatLocalDateTime();
        this.createdAt = parsedCreateDate;
        this.modifiedAt = parsedCreateDate;
    }

    @PreUpdate
    public void onPreUpdate() {
        String parsedCreateDate = getCustomFormatLocalDateTime();
        this.modifiedAt = parsedCreateDate;
    }

    private static String getCustomFormatLocalDateTime() {
        String customLocalDateTimeFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        LocalDateTime parsedCreateDate = LocalDateTime.parse(customLocalDateTimeFormat, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return customLocalDateTimeFormat;
    }
}