package com.lazyorchest.e_commerce.repositories;

import com.lazyorchest.e_commerce.models.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepo extends JpaRepository<CartDetail, Long> {
}
