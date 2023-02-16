package com.lazyorchest.e_commerce.repositories;

import com.lazyorchest.e_commerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
