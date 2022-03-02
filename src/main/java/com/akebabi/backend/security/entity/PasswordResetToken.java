package com.akebabi.backend.security.entity;



import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="PASSWORD_RESET_TOKEN")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="TOKEN_ID")
    private Integer id;
    @Column(name="TOKEN")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "USER_ID")
    private User user;
    @Column(name="CREATED_DATE")
    private LocalDateTime createdDate;

    public PasswordResetToken() {
    }
    public PasswordResetToken(User user) {
        this.user = user;
        createdDate = LocalDateTime.now();
        token = UUID.randomUUID().toString();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
