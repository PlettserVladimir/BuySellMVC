package org.top.BuySellMVC.rdb;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Product;
import org.top.BuySellMVC.rdb.repository.ProductRepository;
import org.top.BuySellMVC.service.ProductService;

import java.util.Optional;

@Service
public class RdbProduct implements ProductService {
    private final ProductRepository productRepository;

    public RdbProduct(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> add(Product product) {
        return Optional.of(productRepository.save(product));
    }

    @Override
    public Optional<Product> deleteById(Integer id) {
        Optional<Product> deleted = findById(id);
        if (deleted.isPresent()){
            productRepository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<Product> updateProduct(Product product) {
        Optional<Product> updated = findById(product.getId());
        if (updated.isPresent()){
            updated = Optional.of(productRepository.save(product));
            return updated;
        }
        return updated;
    }
}
