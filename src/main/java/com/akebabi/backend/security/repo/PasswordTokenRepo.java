package com.akebabi.backend.security.repo;

import com.akebabi.backend.security.entity.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordTokenRepo extends CrudRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String passwordToken);
    PasswordResetToken findByUserId(Integer id);
}
