package com.akebabi.backend.security.repo;

import com.akebabi.backend.security.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoleRepo extends CrudRepository<Role, Integer> {
    Set<Role> findAllByname(String user);
}
