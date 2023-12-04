package org.top.BuySellMVC.service;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Product;

import java.util.Optional;

@Service
public interface ProductService {
    Iterable<Product> findAll();
    Optional<Product> findById(Integer id);
    Optional<Product> add(Product product);
    Optional<Product> deleteById(Integer id);
    Optional<Product> updateProduct(Product product);
}
