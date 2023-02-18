package com.lazyorchest.e_commerce.services;

import com.lazyorchest.e_commerce.dto.ProductRequest;
import com.lazyorchest.e_commerce.models.Product;
import com.lazyorchest.e_commerce.repositories.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    public Product createProduct(ProductRequest request) {
        var product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory().toLowerCase())
                .build();
        productRepo.save(product);
        return product;
    }

    public List<Product> readAllProduct(String category) {
        if (category.equals("ALL")) {
            return productRepo.findAll();
        }
        Product product = Product.builder()
                .category(category.toLowerCase())
                .build();
        return productRepo.findAll(Example.of(product));
    }

    public Product readProductById(Long id) {
        return productRepo.findById(id).orElseThrow();
    }

    public Product updateProduct(ProductRequest request, Long id) {
        Product product = productRepo.findById(id).orElseThrow();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory().toLowerCase());
        productRepo.save(product);
        return product;
    }

    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow();
        productRepo.delete(product);
    }
}
