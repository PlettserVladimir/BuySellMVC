package org.top.BuySellMVC.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.BuySellMVC.entity.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {
    Optional<Category> findByCategory(String category);
}
