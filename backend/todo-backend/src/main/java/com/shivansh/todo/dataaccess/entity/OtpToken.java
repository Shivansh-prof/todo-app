package com.shivansh.todo.dataaccess.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("otp_tokens")
@Data
@Builder
public class OtpToken {
    @Id
    private Long id;
    private Long userId;
    private Long otp;
    private String purpose;
    private Boolean isUsed;
    private LocalDateTime createdAt;
}
