package com.akebabi.backend.security.repo;

import com.akebabi.backend.security.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPageRepo extends PagingAndSortingRepository<User , Long> {
}
