package com.lazyorchest.e_commerce.services;

import com.lazyorchest.e_commerce.dto.ProductRequest;
import com.lazyorchest.e_commerce.models.Product;
import com.lazyorchest.e_commerce.repositories.ProductRepo;
import lombok.RequiredArgsConstructor;
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
                .category(request.getCategory())
                .build();
        productRepo.save(product);
        return product;
    }

    public List<Product> readAllProduct() {
        //TODO Filter produk berdasarkan category
        return productRepo.findAll();
    }

    public Product readProductById(Long id) {
        return productRepo.findById(id).orElseThrow();
    }

    public Product updateProduct(ProductRequest request, Long id) {
        Product product = productRepo.findById(id).orElseThrow();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(request.getCategory());
        productRepo.save(product);
        return product;
    }

    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow();
        productRepo.delete(product);
    }
}
