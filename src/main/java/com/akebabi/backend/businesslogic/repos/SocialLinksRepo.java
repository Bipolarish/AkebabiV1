package com.akebabi.backend.businesslogic.repos;

import com.akebabi.backend.businesslogic.entities.SocialLink;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialLinksRepo extends CrudRepository<SocialLink, Integer> {
}
