package org.top.BuySellMVC.service;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Category;

import java.util.Optional;

@Service
public interface CategoryService {

    Iterable<Category> findAll();
    Optional<Category> findById(Integer id);
    Optional<Category> addCategory(Category category);
    Optional<Category> deleteById(Integer id);
    Optional<Category> updateCategory(Category category);
}
