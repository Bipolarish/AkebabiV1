package com.akebabi.backend.security.service;

import com.akebabi.backend.security.entity.Role;
import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.model.NewUserDetail;

import java.util.Set;

public interface UserRegistrationService {

    NewUserDetail saveUser(NewUserDetail user) throws Exception;

    NewUserDetail saveUser(NewUserDetail user, Set<Role> roles);

    User saveUser(User user);

    User updateStatus(User user);

    boolean isUserNameExists(String email);

    User getUser(String userPublicId);

}
