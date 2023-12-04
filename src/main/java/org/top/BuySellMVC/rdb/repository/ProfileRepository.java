package org.top.BuySellMVC.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.BuySellMVC.entity.Profile;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<Profile,Integer> {
    Optional<Profile> findByName(String name);
}
