package com.akebabi.backend.security.model;

import com.akebabi.backend.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetTokenModel {

    private String token;
    private User user;
    private LocalDateTime createdDate;
}
