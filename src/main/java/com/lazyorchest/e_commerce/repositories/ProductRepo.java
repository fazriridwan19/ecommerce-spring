package com.lazyorchest.e_commerce.repositories;

import com.lazyorchest.e_commerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
