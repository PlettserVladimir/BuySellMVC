package org.top.BuySellMVC.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.BuySellMVC.entity.UserProfile;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile,Integer> {
    Optional<UserProfile> findByName(String name);
}
