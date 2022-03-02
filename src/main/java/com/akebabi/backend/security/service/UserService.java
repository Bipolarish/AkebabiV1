package com.akebabi.backend.security.service;

import com.akebabi.backend.security.entity.User;
import com.akebabi.backend.security.model.UserProfileModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> getUserListPageable(Pageable pageable);

    UserProfileModel updateUserProfile(UserProfileModel userProfileModel);
    User updateUser(User user);


}
