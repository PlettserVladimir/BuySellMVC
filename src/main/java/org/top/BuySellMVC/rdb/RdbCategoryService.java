package org.top.BuySellMVC.rdb;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Category;
import org.top.BuySellMVC.rdb.repository.CategoryRepository;
import org.top.BuySellMVC.service.CategoryService;

import java.util.Optional;

@Service
public class RdbCategoryService  implements CategoryService {
    private final CategoryRepository categoryRepository;

    public RdbCategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> addCategory(Category category) {
        Optional<Category> added = findById(category.getId());
        if (added.isEmpty()){
            return Optional.of(categoryRepository.save(category));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> deleteById(Integer id) {
        Optional<Category> deleted = findById(id);
        if (deleted.isPresent()){
            categoryRepository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<Category> updateCategory(Category category) {
        Optional<Category> updated = findById(category.getId());
        Optional<Category> isCategory = categoryRepository.findByCategory(category.getCategory());
        if (updated.isPresent() && isCategory.isEmpty()){
            updated = Optional.of(categoryRepository.save(category));
        }
        return updated;
    }
}
