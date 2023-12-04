package org.top.BuySellMVC.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.BuySellMVC.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product,Integer> {
}
