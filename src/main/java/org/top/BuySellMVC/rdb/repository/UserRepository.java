package org.top.BuySellMVC.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.BuySellMVC.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByLogin(String login);
    Iterable<User> findAllByRole(String role);
}
