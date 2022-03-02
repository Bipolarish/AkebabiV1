package com.akebabi.backend.security.repo;

import com.akebabi.backend.security.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Integer> {

    User findByUserName(String userName);

    Optional<User> findByUserPublicId(String publicId);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

}
